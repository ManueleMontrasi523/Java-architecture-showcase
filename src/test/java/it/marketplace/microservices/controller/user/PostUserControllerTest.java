package it.marketplace.microservices.controller.user;

import it.marketplace.microservices.common.dto.UserDto;
import it.marketplace.microservices.common.resource.UserResource;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.UserMapper;
import it.marketplace.microservices.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostUserControllerTest {

    @Mock
    private UserService service;
    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private PostUserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddUser_WhenValidResource_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        UserResource resource = new UserResource();
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(service).save(UserMapper.toDto(resource));

        // Act
        ResponseEntity<?> response = controller.save(resource, bindingResult);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User added!", ((Map<?, ?>) response.getBody()).get("message"));
        verify(service).save(UserMapper.toDto(resource));
    }

    @Test
    void shouldReturnBadRequest_WhenBindingResultHasErrors_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        UserResource resource = new UserResource();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of());

        // Act
        ResponseEntity<?> response = controller.save(resource, bindingResult);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        verify(bindingResult).getAllErrors();
    }

    @Test
    void shouldAddAllUsers_WhenValidResources_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        UserResource resource = new UserResource();
        List<UserResource> resources = List.of(resource);
        List<UserDto> dtos = resources.stream().map(UserMapper::toDto).toList();
        doNothing().when(service).saveAll(dtos);

        // Act
        ResponseEntity<?> response = controller.saveAll(resources);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Users added!", ((Map<?, ?>) response.getBody()).get("message"));
        verify(service).saveAll(dtos);
    }
}

