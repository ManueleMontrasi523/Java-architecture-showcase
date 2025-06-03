package it.marketplace.service;

import it.marketplace.common.dto.OrderDto;
import it.marketplace.repository.OrderRepository;
import it.marketplace.repository.UserRepository;
import it.marketplace.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindByCode_WhenOrderExists_ThenArrangeActAssert() {
        // Arrange
        String code = "ORD123";
        OrderDto entity = new OrderDto();
        when(orderRepository.findByOrderCodeIgnoreCase(code)).thenReturn(entity);
        // Act
        OrderDto result = orderService.findByCode(code);
        // Assert
        assertNotNull(result);
        verify(orderRepository).findByOrderCodeIgnoreCase(code);
    }
}

