package it.marketplace.service;

import it.marketplace.common.dto.UserDto;
import it.marketplace.repository.UserRepository;
import it.marketplace.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindByEmail_WhenUserExists_ThenArrangeActAssert() {
        // Arrange
        String email = "test@email.com";

        UserDto dto = new UserDto();
        when(repository.findByEmailIgnoreCase(email)).thenReturn(dto);
        // Act
        UserDto result = service.findByEmail(email);
        // Assert
        assertNotNull(result);
        verify(repository).findByEmailIgnoreCase(email);
    }
}

