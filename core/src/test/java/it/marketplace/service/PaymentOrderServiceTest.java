package it.marketplace.service;

import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.dto.PaymentOrderDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.repository.PaymentInstallmentsRepository;
import it.marketplace.repository.PaymentOrderRepository;
import it.marketplace.service.impl.PaymentOrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PaymentOrderServiceTest {

    @Mock
    private PaymentOrderRepository repository;

    @Mock
    private OrderService orderService;

    @Mock
    private PaymentInstallmentsRepository paymentInstallmentsRepository;

    @InjectMocks
    private PaymentOrderServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindOrderByEmail_WhenOrdersExist_ThenArrangeActAssert() {
        // Arrange
        String email = "test@email.com";
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderCode("ORD123");
        when(orderService.findByUserMail(email)).thenReturn(List.of(orderDto));
        PaymentOrderDto dto = new PaymentOrderDto();
        when(repository.findByOrderCodeIn(List.of("ORD123"))).thenReturn(List.of(dto));
        // Act
        List<PaymentOrderDto> result = service.findOrderByEmail(email);
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderService).findByUserMail(email);
        verify(repository).findByOrderCodeIn(List.of("ORD123"));
    }

    @Test
    void shouldFindAll_WhenPaymentOrdersExist_ThenArrangeActAssert() {
        // Arrange
        PaymentOrderDto dto = new PaymentOrderDto();
        when(repository.findAll()).thenReturn(List.of(dto));
        // Act
        List<PaymentOrderDto> result = service.findAll();
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void shouldPayOrder_WhenNotInstallments_ThenArrangeActAssert() {
        // Arrange
        String orderCode = "ORD123";
        PaymentOrderDto entity = new PaymentOrderDto();
        entity.setStatus(StatusOrderEnum.PENDING_PAYMENT);
        entity.setDebit(120.0);
        when(repository.findByOrderCodeIgnoreCase(orderCode)).thenReturn(entity);
        // Act
        service.payOrder(orderCode, false);
        // Assert
        assertEquals(StatusOrderEnum.PAID, entity.getStatus());
        verify(orderService).payOrder(orderCode);
        verify(repository).save(entity);
    }

    @Test
    void shouldPayOrder_WhenInstallments_ThenArrangeActAssert() {
        // Arrange
        String orderCode = "ORD124";
        PaymentOrderDto entity = new PaymentOrderDto();
        entity.setStatus(StatusOrderEnum.PENDING_PAYMENT);
        entity.setDebit(120.0);
        when(repository.findByOrderCodeIgnoreCase(orderCode)).thenReturn(entity);
        // Act
        service.payOrder(orderCode, true);
        // Assert
        assertEquals(StatusOrderEnum.RATEIZED, entity.getStatus());
        verify(paymentInstallmentsRepository).saveAll(anyList());
        verify(repository).save(entity);
    }
}
