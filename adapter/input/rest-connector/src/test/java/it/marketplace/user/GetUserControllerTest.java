package it.marketplace.user;

import it.marketplace.common.dto.UserDto;
import it.marketplace.common.enums.StatusUserEnum;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.common.mapper.UserMapper;
import it.marketplace.common.resource.UserResource;
import it.marketplace.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetUserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private GetUserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnUser_WhenFindByEmail_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        String email = "test@email.com";
        UserDto dto = new UserDto();
        UserResource expected = UserMapper.toResource(dto);
        when(service.findByEmail(email)).thenReturn(dto);

        // Act
        ResponseEntity<UserResource> response = controller.find(email);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(service).findByEmail(email);
    }

    @Test
    void shouldReturnAllUsers_WhenFindAllByStatus_ThenArrangeActAssert() throws ServiceException {
        // Arrange
        StatusUserEnum status = StatusUserEnum.ACTIVE;
        UserDto dto = new UserDto();
        List<UserDto> dtos = List.of(dto);
        when(service.findAll(status)).thenReturn(dtos);
        List<UserResource> expected = dtos.stream().map(UserMapper::toResource).toList();

        // Act
        ResponseEntity<List<UserResource>> response = controller.findAll(status);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(service).findAll(status);
    }
}

