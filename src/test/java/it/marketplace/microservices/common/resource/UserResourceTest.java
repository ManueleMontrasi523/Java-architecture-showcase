package it.marketplace.microservices.common.resource;

import it.marketplace.microservices.common.enums.RoleEnum;
import it.marketplace.microservices.common.enums.StatusUserEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserResourceTest {
    @Test
    void shouldCreateUserResourceWithAllFields() {
        // Arrange
        LocalDateTime tmsSubscriptionDate = LocalDateTime.now();
        // Act
        UserResource resource = new UserResource(1L, "John", "Doe", "john.doe@email.com", "Main Street", "London", StatusUserEnum.ACTIVE, RoleEnum.CLIENT, tmsSubscriptionDate);
        // Assert
        assertEquals(1L, resource.getId());
        assertEquals("John", resource.getName());
        assertEquals("Doe", resource.getLastname());
        assertEquals("john.doe@email.com", resource.getEmail());
        assertEquals("Main Street", resource.getResidenceAddress());
        assertEquals("London", resource.getResidenceCity());
        assertEquals(StatusUserEnum.ACTIVE, resource.getStatus());
        assertEquals(RoleEnum.CLIENT, resource.getRole());
        assertEquals(tmsSubscriptionDate, resource.getTmsSubscriptionDate());
    }

    @Test
    void shouldSetAndGetIdAndTmsSubscriptionDate() {
        // Arrange
        UserResource resource = new UserResource();
        LocalDateTime tmsSubscriptionDate = LocalDateTime.now();
        // Act
        resource.setId(88L);
        resource.setTmsSubscriptionDate(tmsSubscriptionDate);
        // Assert
        assertEquals(88L, resource.getId());
        assertEquals(tmsSubscriptionDate, resource.getTmsSubscriptionDate());
    }
}

