package it.marketplace.common.mapper;

import it.marketplace.common.dto.UserDto;
import it.marketplace.common.resource.UserResource;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    @Test
    void shouldMapUserDtoToResourceAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        UserDto dto = new UserDto(1L, "John", "Doe", "john.doe@email.com", "Main Street", "London", null, null, now, now);
        // Act
        UserResource resource = UserMapper.toResource(dto);
        UserDto mappedDto = UserMapper.toDto(resource);
        // Assert
        assertEquals(dto.getId(), resource.getId());
        assertEquals(dto.getEmail(), resource.getEmail());
        assertEquals(dto.getTmsSubscriptionDate(), resource.getTmsSubscriptionDate());
        assertEquals(dto.getId(), mappedDto.getId());
        assertEquals(dto.getEmail(), mappedDto.getEmail());
        assertEquals(dto.getTmsSubscriptionDate(), mappedDto.getTmsSubscriptionDate());
    }
}

