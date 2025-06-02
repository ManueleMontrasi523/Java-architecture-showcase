package it.marketplace.microservices.config.validation;

import it.marketplace.microservices.common.resource.UserResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    private UserValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserValidator();
    }

    @Test
    void shouldSupportUserResourceClass() {
        // Act & Assert
        assertTrue(validator.supports(UserResource.class));
        assertFalse(validator.supports(String.class));
    }

    @Test
    void shouldPassValidationForValidUser() {
        // Arrange
        UserResource user = new UserResource();
        user.setName("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@email.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        // Act
        validator.validate(user, errors);
        // Assert
        assertFalse(errors.hasErrors());
    }

    @Test
    void shouldRejectIfNameIsMissing() {
        // Arrange
        UserResource user = new UserResource();
        user.setLastname("Doe");
        user.setEmail("john.doe@email.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        // Act
        validator.validate(user, errors);
        // Assert
        assertTrue(errors.hasErrors());
        assertEquals("Name is mandatory!", errors.getAllErrors().get(0).getDefaultMessage());
    }

    @Test
    void shouldRejectIfLastnameIsMissing() {
        // Arrange
        UserResource user = new UserResource();
        user.setName("John");
        user.setEmail("john.doe@email.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        // Act
        validator.validate(user, errors);
        // Assert
        assertTrue(errors.hasErrors());
        assertEquals("lastname is mandatory!", errors.getAllErrors().get(0).getDefaultMessage());
    }

    @Test
    void shouldRejectIfEmailIsMissing() {
        // Arrange
        UserResource user = new UserResource();
        user.setName("John");
        user.setLastname("Doe");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        // Act
        validator.validate(user, errors);
        // Assert
        assertTrue(errors.hasErrors());
        assertEquals("Email missing or with format invalid!", errors.getAllErrors().get(0).getDefaultMessage());
    }

    @Test
    void shouldRejectIfEmailIsInvalid() {
        // Arrange
        UserResource user = new UserResource();
        user.setName("John");
        user.setLastname("Doe");
        user.setEmail("invalid-email");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        // Act
        validator.validate(user, errors);
        // Assert
        assertTrue(errors.hasErrors());
        assertEquals("Email missing or with format invalid!", errors.getAllErrors().get(0).getDefaultMessage());
    }
}

