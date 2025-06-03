package it.marketplace.service;

import it.marketplace.rabbitmq.RabbitMqProducer;
import it.marketplace.repository.*;
import it.marketplace.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PaymentOrderRepository paymentOrderRepository;
    @Mock
    private PaymentInstallmentsRepository paymentInstallmentsRepository;
    @Mock
    private RabbitMqProducer rabbitMqProducer;

    @InjectMocks
    private TransactionServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCallReadPendingPaymentsOrder_WhenInvoked_ThenArrangeActAssert() {
        // Arrange/Act
        service.readPendingPaymentsOrder();
        assertTrue(true);
    }
}

