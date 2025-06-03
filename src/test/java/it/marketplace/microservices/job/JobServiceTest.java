package it.marketplace.microservices.job;

import it.marketplace.microservices.common.dto.OrderDto;
import it.marketplace.microservices.common.dto.ProductDto;
import it.marketplace.microservices.common.dto.ProductOrderDto;
import it.marketplace.microservices.rabbitmq.RabbitMqProducer;
import it.marketplace.microservices.service.OrderService;
import it.marketplace.microservices.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

class JobServiceTest {

    @Mock
    private RabbitMqProducer producer;
    @Mock
    private OrderService orderService;
    @Mock
    private ProductService productService;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldProcessOrderAndSendPendingPayment_WhenSupplyIsSufficient_ThenArrangeActAssert() {
        // Arrange
        String orderCode = "ORD123";
        ProductOrderDto productOrderDto = new ProductOrderDto();
        productOrderDto.setProductCode("PROD1");
        productOrderDto.setQuantity(BigDecimal.ONE);
        productOrderDto.setTotal(10.0);
        OrderDto orderDto = new OrderDto();
        orderDto.setProductOrder(List.of(productOrderDto));
        when(orderService.findByCode(orderCode)).thenReturn(orderDto);
        ProductDto productDto = new ProductDto();
        productDto.setSupply(BigDecimal.TEN);
        when(productService.findByCode("PROD1")).thenReturn(productDto);
        // Act
        jobService.startProcessing(orderCode);
        // Assert
        verify(productService).saveAllDirectly(anyList());
        verify(orderService).saveDirectly(orderDto);
        verify(producer).sendMessagePendingPayment(anyMap());
    }

    @Test
    void shouldRejectOrder_WhenSupplyIsInsufficient_ThenArrangeActAssert() {
        // Arrange
        String orderCode = "ORD124";
        ProductOrderDto productOrderDto = new ProductOrderDto();
        productOrderDto.setProductCode("PROD2");
        productOrderDto.setQuantity(BigDecimal.TEN);
        productOrderDto.setTotal(100.0);
        OrderDto orderDto = new OrderDto();
        orderDto.setProductOrder(List.of(productOrderDto));
        when(orderService.findByCode(orderCode)).thenReturn(orderDto);
        ProductDto productDto = new ProductDto();
        productDto.setSupply(BigDecimal.ONE);
        when(productService.findByCode("PROD2")).thenReturn(productDto);
        // Act
        jobService.startProcessing(orderCode);
        // Assert
        verify(productService).saveAllDirectly(anyList());
        verify(orderService).saveDirectly(orderDto);
        verify(producer, never()).sendMessagePendingPayment(anyMap());
    }
}

