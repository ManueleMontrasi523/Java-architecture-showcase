package it.marketplace.microservices.scheduler;

import it.marketplace.microservices.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class OrderSchedulerCreatedTest {

    @Mock
    private TransactionService service;

    @InjectMocks
    private OrderSchedulerCreated scheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCallTransactionServiceMethods_WhenStartProcessOrder_ThenArrangeActAssert() {
        // Arrange/Act
        scheduler.startProcessOrder();
        // Assert
        verify(service).readPendingPaymentsOrder();
        verify(service).startAlignmentStatesOrder();
    }
}

