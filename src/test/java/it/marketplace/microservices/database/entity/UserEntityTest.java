package it.marketplace.microservices.database.entity;

import it.marketplace.microservices.common.enums.RoleEnum;
import it.marketplace.microservices.common.enums.StatusUserEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void shouldCreateEntityWithAllArgsConstructor_ArrangeActAssert() {
        // Arrange/Act
        LocalDateTime now = LocalDateTime.now();
        UserEntity entity = new UserEntity(1L, "Mario", "Rossi", "mario@email.com", "Via Roma", "Roma", RoleEnum.CLIENT, StatusUserEnum.ACTIVE, now, now);
        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("Mario", entity.getName());
        assertEquals("Rossi", entity.getLastname());
        assertEquals("mario@email.com", entity.getEmail());
        assertEquals("Via Roma", entity.getResidenceAddress());
        assertEquals("Roma", entity.getResidenceCity());
        assertEquals(RoleEnum.CLIENT, entity.getRole());
        assertEquals(StatusUserEnum.ACTIVE, entity.getStatus());
        assertEquals(now, entity.getTmsSubscriptionDate());
        assertEquals(now, entity.getTmsUpdate());
    }

    @Test
    void shouldSetAndGetFields_ArrangeActAssert() {
        // Arrange
        UserEntity entity = new UserEntity();
        LocalDateTime now = LocalDateTime.now();
        // Act
        entity.setId(2L);
        entity.setName("Luigi");
        entity.setLastname("Verdi");
        entity.setEmail("luigi@email.com");
        entity.setResidenceAddress("Via Milano");
        entity.setResidenceCity("Milano");
        entity.setRole(RoleEnum.ADMIN);
        entity.setStatus(StatusUserEnum.DISABLED);
        entity.setTmsSubscriptionDate(now);
        entity.setTmsUpdate(now);
        // Assert
        assertEquals(2L, entity.getId());
        assertEquals("Luigi", entity.getName());
        assertEquals("Verdi", entity.getLastname());
        assertEquals("luigi@email.com", entity.getEmail());
        assertEquals("Via Milano", entity.getResidenceAddress());
        assertEquals("Milano", entity.getResidenceCity());
        assertEquals(RoleEnum.ADMIN, entity.getRole());
        assertEquals(StatusUserEnum.DISABLED, entity.getStatus());
        assertEquals(now, entity.getTmsSubscriptionDate());
        assertEquals(now, entity.getTmsUpdate());
    }

    @Test
    void shouldTestEqualsAndHashCode_ArrangeActAssert() {
        // Arrange
        UserEntity e1 = new UserEntity();
        UserEntity e2 = new UserEntity();
        // Act
        e1.setId(1L); e2.setId(1L);
        e1.setEmail("a@b"); e2.setEmail("a@b");
        // Assert
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void shouldTestToString_ArrangeActAssert() {
        // Arrange
        UserEntity entity = new UserEntity();
        entity.setEmail("a@b");
        // Act
        String str = entity.toString();
        // Assert
        assertTrue(str.contains("a@b"));
    }
}

