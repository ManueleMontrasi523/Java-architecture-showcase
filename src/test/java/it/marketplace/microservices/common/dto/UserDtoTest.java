package it.marketplace.microservices.common.dto;

import it.marketplace.microservices.common.enums.RoleEnum;
import it.marketplace.microservices.common.enums.StatusUserEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoTest {

    @Test
    void shouldCreateUserDtoWithAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        // Act
        UserDto dto = new UserDto(1L, "Mario", "Rossi", "mario.rossi@email.com", "Via Roma", "Rome", StatusUserEnum.ACTIVE, RoleEnum.CLIENT, now);
        // Assert
        assertEquals("1", dto.getId().toString());
        assertEquals("Mario", dto.getName());
        assertEquals("Rossi", dto.getLastname());
        assertEquals("mario.rossi@email.com", dto.getEmail());
        assertEquals("Via Roma", dto.getResidenceAddress());
        assertEquals("Rome", dto.getResidenceCity());
        assertEquals(StatusUserEnum.ACTIVE, dto.getStatus());
        assertEquals(RoleEnum.CLIENT, dto.getRole());
        assertEquals(now, dto.getTmsSubscriptionDate());
    }

    @Test
    void shouldSetAndGetEmail() {
        // Arrange
        UserDto dto = new UserDto();
        // Act
        dto.setEmail("test@email.com");
        // Assert
        assertEquals("test@email.com", dto.getEmail());
    }
}

