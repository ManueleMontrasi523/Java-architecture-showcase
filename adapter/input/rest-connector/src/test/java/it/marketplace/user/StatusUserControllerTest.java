package it.marketplace.user;

import it.marketplace.common.enums.StatusUserEnum;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.service.UserService;
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

class StatusUserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private StatusUserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateUserStatus_WhenValidEmailAndStatus_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        String email = "test@email.com";
        StatusUserEnum status = StatusUserEnum.ACTIVE;
        doNothing().when(service).statusByEmail(email, status);

        // Act
        ResponseEntity<Map<String, String>> response = controller.status(email, status);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User updated!", response.getBody().get("message"));
        verify(service).statusByEmail(email, status);
    }
}

