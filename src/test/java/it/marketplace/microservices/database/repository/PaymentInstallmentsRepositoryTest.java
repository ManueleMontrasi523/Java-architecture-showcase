package it.marketplace.microservices.database.repository;

import it.marketplace.microservices.common.enums.StatusOrderEnum;
import it.marketplace.microservices.database.entity.PaymentInstallmentsEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PaymentInstallmentsRepositoryTest {

    @Autowired
    private PaymentInstallmentsRepository repository;

    @Test
    void shouldFindByOrderCode_ArrangeActAssert() {
        // Arrange
        PaymentInstallmentsEntity entity = new PaymentInstallmentsEntity();
        entity.setOrderCode("ORD1");
        entity.setStatus(StatusOrderEnum.CREATED);
        entity.setDebit(0.59);
        entity.setReference("ORDER");
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
        // Act
        List<PaymentInstallmentsEntity> found = repository.findByOrderCode("ORD1");
        // Assert
        assertFalse(found.isEmpty());
        assertEquals("ORD1", found.get(0).getOrderCode());
    }

    @Test
    void shouldFindByOrderCodeAndStatus_ArrangeActAssert() {
        // Arrange
        PaymentInstallmentsEntity entity = new PaymentInstallmentsEntity();
        entity.setOrderCode("ORD2");
        entity.setStatus(StatusOrderEnum.PAID);
        entity.setDebit(0.59);
        entity.setReference("ORDER");
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
        // Act
        List<PaymentInstallmentsEntity> found = repository.findByOrderCodeAndStatus("ORD2", StatusOrderEnum.PAID);
        // Assert
        assertFalse(found.isEmpty());
        assertEquals(StatusOrderEnum.PAID, found.get(0).getStatus());
    }
}

