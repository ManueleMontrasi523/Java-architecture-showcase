package it.marketplace.microservices.database.repository;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.database.entity.PaymentOrderEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PaymentOrderRepositoryTest {

    @Autowired
    private PaymentOrderRepository repository;

    @Test
    void shouldFindByOrderCodeIgnoreCase_ArrangeActAssert() {
        // Arrange
        PaymentOrderEntity entity = new PaymentOrderEntity();
        entity.setOrderCode("ORD1");
        entity.setStatus(StatusOrderEnum.CREATED);
        entity.setDebit(0.59);
        entity.setOrderDate(LocalDateTime.now());
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
        // Act
        PaymentOrderEntity found = repository.findByOrderCodeIgnoreCase("ord1");
        // Assert
        assertNotNull(found);
        assertEquals("ORD1", found.getOrderCode());
    }

    @Test
    void shouldFindByOrderCodeIn_ArrangeActAssert() {
        // Arrange
        PaymentOrderEntity entity = new PaymentOrderEntity();
        entity.setOrderCode("ORD2");
        entity.setStatus(StatusOrderEnum.PAID);
        entity.setDebit(0.59);
        entity.setOrderDate(LocalDateTime.now());
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
        // Act
        List<PaymentOrderEntity> found = repository.findByOrderCodeIn(List.of("ORD2"));
        // Assert
        assertFalse(found.isEmpty());
        assertEquals("ORD2", found.get(0).getOrderCode());
    }

    @Test
    void shouldFindByStatus_ArrangeActAssert() {
        // Arrange
        PaymentOrderEntity entity = new PaymentOrderEntity();
        entity.setOrderCode("ORD3");
        entity.setStatus(StatusOrderEnum.CREATED);
        entity.setDebit(0.59);
        entity.setOrderDate(LocalDateTime.now());
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
        // Act
        List<PaymentOrderEntity> found = repository.findByStatus(StatusOrderEnum.CREATED);
        // Assert
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(e -> e.getOrderCode().equals("ORD3")));
    }

    @Test
    void shouldFindByOrderCodeAndStatus_ArrangeActAssert() {
        // Arrange
        PaymentOrderEntity entity = new PaymentOrderEntity();
        entity.setOrderCode("ORD4");
        entity.setStatus(StatusOrderEnum.PAID);
        entity.setDebit(0.59);
        entity.setOrderDate(LocalDateTime.now());
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
        // Act
        PaymentOrderEntity found = repository.findByOrderCodeAndStatus("ORD4", StatusOrderEnum.CREATED);
        // Assert
        assertNotNull(found);
        assertEquals("ORD4", found.getOrderCode());
        assertEquals(StatusOrderEnum.PAID, found.getStatus());
    }
}

