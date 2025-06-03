package it.marketplace.microservices.controller.user;

import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class DeleteUserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private DeleteUserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteUser_WhenValidEmail_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        String email = "test@email.com";
        doNothing().when(service).deleteByEmail(email);

        // Act
        ResponseEntity<Map<String, String>> response = controller.delete(email);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User deleted!", response.getBody().get("message"));
        verify(service).deleteByEmail(email);
    }
}

