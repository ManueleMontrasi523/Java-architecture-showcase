package it.marketplace.microservices.service;

import it.marketplace.microservices.common.dto.PaymentOrderDto;
import it.marketplace.microservices.database.entity.PaymentOrderEntity;
import it.marketplace.microservices.database.repository.PaymentOrderRepository;
import it.marketplace.microservices.service.impl.PaymentOrderServiceImpl;
import it.marketplace.microservices.common.dto.OrderDto;
import it.marketplace.microservices.service.OrderService;
import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.database.repository.PaymentInstallmentsRepository;
import it.marketplace.microservices.database.entity.PaymentInstallmentsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        PaymentOrderEntity entity = new PaymentOrderEntity();
        when(repository.findByOrderCodeIn(List.of("ORD123"))).thenReturn(List.of(entity));
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
        PaymentOrderEntity entity = new PaymentOrderEntity();
        when(repository.findAll()).thenReturn(List.of(entity));
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
        PaymentOrderEntity entity = new PaymentOrderEntity();
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
        PaymentOrderEntity entity = new PaymentOrderEntity();
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
