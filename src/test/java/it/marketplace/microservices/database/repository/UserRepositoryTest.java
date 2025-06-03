package it.marketplace.microservices.database.repository;

import it.marketplace.microservices.common.enums.RoleEnum;
import it.marketplace.microservices.common.enums.StatusUserEnum;
import it.marketplace.microservices.database.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldFindByEmailIgnoreCase_ArrangeActAssert() {
        // Arrange
        UserEntity entity = new UserEntity();
        entity.setName("user");
        entity.setLastname("3");
        entity.setEmail("test@email.com");
        entity.setRole(RoleEnum.CLIENT);
        entity.setTmsSubscriptionDate(LocalDateTime.now());
        entity.setTmsUpdate(LocalDateTime.now());
        entity.setStatus(StatusUserEnum.ACTIVE);
        repository.save(entity);
        // Act
        UserEntity found = repository.findByEmailIgnoreCase("TEST@email.com");
        // Assert
        assertNotNull(found);
        assertEquals("test@email.com", found.getEmail());
    }

    @Test
    void shouldFindAllByEmailIgnoreCaseIn_ArrangeActAssert() {
        // Arrange
        UserEntity entity = new UserEntity();
        entity.setName("user");
        entity.setLastname("3");
        entity.setEmail("user2@email.com");
        entity.setRole(RoleEnum.CLIENT);
        entity.setTmsSubscriptionDate(LocalDateTime.now());
        entity.setTmsUpdate(LocalDateTime.now());
        entity.setStatus(StatusUserEnum.ACTIVE);
        repository.save(entity);
        // Act
        List<UserEntity> found = repository.findAllByEmailIgnoreCaseIn(List.of("USER2@email.com"));
        // Assert
        assertFalse(found.isEmpty());
        assertEquals("user2@email.com", found.get(0).getEmail());
    }

    @Test
    void shouldFindAllByStatus_ArrangeActAssert() {
        // Arrange
        UserEntity entity = new UserEntity();
        entity.setName("user");
        entity.setLastname("3");
        entity.setEmail("user3@email.com");
        entity.setStatus(StatusUserEnum.ACTIVE);
        entity.setRole(RoleEnum.CLIENT);
        entity.setTmsSubscriptionDate(LocalDateTime.now());
        entity.setTmsUpdate(LocalDateTime.now());
        entity.setStatus(StatusUserEnum.ACTIVE);
        repository.save(entity);
        // Act
        List<UserEntity> found = repository.findAllByStatus(StatusUserEnum.ACTIVE);
        // Assert
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(u -> u.getEmail().equals("user3@email.com")));
    }

    @Test
    void shouldUpdateStatusRelationshipsByEmail_ArrangeActAssert() {
        // Arrange
        UserEntity entity = new UserEntity();
        entity.setName("user");
        entity.setLastname("3");
        entity.setEmail("user4@email.com");
        entity.setStatus(StatusUserEnum.ACTIVE);
        entity.setRole(RoleEnum.CLIENT);
        entity.setTmsSubscriptionDate(LocalDateTime.now());
        entity.setTmsUpdate(LocalDateTime.now());
        entity.setStatus(StatusUserEnum.ACTIVE);
        repository.save(entity);
        // Act
        repository.statusRelationshipsByEmail("user4@email.com", StatusUserEnum.DISABLED);
        entityManager.flush();
        entityManager.clear();
        UserEntity updated = repository.findByEmailIgnoreCase("user4@email.com");
        // Assert
        assertNotNull(updated);
        assertEquals(StatusUserEnum.DISABLED, updated.getStatus());
    }
}

