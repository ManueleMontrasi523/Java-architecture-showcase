package it.marketplace.microservices.controller.user;

import it.marketplace.microservices.common.resource.UserResource;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.UserMapper;
import it.marketplace.microservices.config.validation.UserValidator;
import it.marketplace.microservices.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class PutUserControllerTest {

    @Mock
    private UserService service;
    @Mock
    private UserValidator validator;
    @Mock
    private WebDataBinder binder;

    @InjectMocks
    private PutUserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller.initBinder(binder);
    }

    @Test
    void shouldUpdateUser_WhenValidResource_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        UserResource resource = new UserResource();
        doNothing().when(service).update(UserMapper.toDto(resource));

        // Act
        ResponseEntity<Map<String, String>> response = controller.update(resource);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User updated!", response.getBody().get("message"));
        verify(service).update(UserMapper.toDto(resource));
    }
}

