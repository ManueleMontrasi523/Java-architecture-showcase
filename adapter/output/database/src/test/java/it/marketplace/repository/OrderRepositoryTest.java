package it.marketplace.repository;

import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.dto.UserDto;
import it.marketplace.common.enums.RoleEnum;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.common.enums.StatusUserEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindByOrderCodeIgnoreCase_ArrangeActAssert() {
        // Arrange
        UserDto user = new UserDto();
        user.setEmail("test@email.com");
        user.setName("user");
        user.setLastname("3");
        user.setRole(RoleEnum.CLIENT);
        user.setStatus(StatusUserEnum.ACTIVE);
        user.setTmsUpdate(LocalDateTime.now());
        user.setTmsSubscriptionDate(LocalDateTime.now());
        userRepository.save(user);
        OrderDto order = new OrderDto();
        order.setOrderCode("ORD123");
        order.setUser(user);
        order.setProductOrder(new ArrayList<>());
        order.setStatus(StatusOrderEnum.CREATED);
        order.setTmsUpdate(LocalDateTime.now());
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);
        // Act
        OrderDto found = orderRepository.findByOrderCodeIgnoreCase("ord123");
        // Assert
        assertNotNull(found);
        assertEquals("ORD123", found.getOrderCode());
    }

    @Test
    void shouldFindOrderByUserMailAndStatus_ArrangeActAssert() {
        // Arrange
        UserDto user = new UserDto();
        user.setEmail("user2@email.com");
        user.setName("user");
        user.setLastname("3");
        user.setRole(RoleEnum.CLIENT);
        user.setStatus(StatusUserEnum.ACTIVE);
        user.setTmsSubscriptionDate(LocalDateTime.now());
        user.setTmsUpdate(LocalDateTime.now());
        userRepository.save(user);
        OrderDto order = new OrderDto();
        order.setOrderCode("ORD456");
        order.setUser(user);
        order.setProductOrder(new ArrayList<>());
        order.setStatus(StatusOrderEnum.CREATED);
        order.setOrderDate(LocalDateTime.now());
        order.setTmsUpdate(LocalDateTime.now());
        orderRepository.save(order);
        // Act
        OrderDto found = orderRepository.findOrderByUserMailAndStatus("user2@email.com", StatusOrderEnum.CREATED);
        // Assert
        assertNotNull(found);
        assertEquals("ORD456", found.getOrderCode());
    }

    @Test
    void shouldFindOrderByUserMail_ArrangeActAssert() {
        // Arrange
        UserDto user = new UserDto();
        user.setName("user");
        user.setLastname("3");
        user.setEmail("user3@email.com");
        user.setRole(RoleEnum.CLIENT);
        user.setStatus(StatusUserEnum.ACTIVE);
        user.setTmsSubscriptionDate(LocalDateTime.now());
        user.setTmsUpdate(LocalDateTime.now());
        userRepository.save(user);
        OrderDto order = new OrderDto();
        order.setOrderCode("ORD789");
        order.setUser(user);
        order.setProductOrder(new ArrayList<>());
        order.setStatus(StatusOrderEnum.CREATED);
        order.setOrderDate(LocalDateTime.now());
        order.setTmsUpdate(LocalDateTime.now());
        orderRepository.save(order);
        // Act
        List<OrderDto> found = orderRepository.findOrderByUserMail("user3@email.com");
        // Assert
        assertFalse(found.isEmpty());
        assertEquals("ORD789", found.get(0).getOrderCode());
    }

    @Test
    void shouldFindByStatus_ArrangeActAssert() {
        // Arrange
        UserDto user = new UserDto();
        user.setName("user");
        user.setLastname("3");
        user.setEmail("user4@email.com");
        user.setRole(RoleEnum.CLIENT);
        user.setStatus(StatusUserEnum.ACTIVE);
        user.setTmsSubscriptionDate(LocalDateTime.now());
        user.setTmsUpdate(LocalDateTime.now());
        userRepository.save(user);
        OrderDto order = new OrderDto();
        order.setOrderCode("ORD000");
        order.setUser(user);
        order.setProductOrder(new ArrayList<>());
        order.setStatus(StatusOrderEnum.CREATED);
        order.setOrderDate(LocalDateTime.now());
        order.setTmsUpdate(LocalDateTime.now());
        orderRepository.save(order);
        // Act
        List<OrderDto> found = orderRepository.findByStatus(StatusOrderEnum.CREATED);
        // Assert
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(o -> o.getOrderCode().equals("ORD000")));
    }
}

