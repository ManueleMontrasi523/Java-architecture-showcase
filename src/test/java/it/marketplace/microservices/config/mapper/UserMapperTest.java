package it.marketplace.microservices.config.mapper;

import it.marketplace.microservices.common.dto.UserDto;
import it.marketplace.microservices.common.resource.UserResource;
import it.marketplace.microservices.database.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    @Test
    void shouldMapUserDtoToResourceAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        UserDto dto = new UserDto(1L, "John", "Doe", "john.doe@email.com", "Main Street", "London", null, null, now);
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

    @Test
    void shouldMapUserDtoToEntityAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        UserDto dto = new UserDto(2L, "Jane", "Smith", "JANE.SMITH@EMAIL.COM", "Second Street", "Paris", null, null, now);
        // Act
        UserEntity entity = UserMapper.toEntity(dto);
        UserDto mappedDto = UserMapper.toDto(entity);
        // Assert
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getEmail().toLowerCase(), entity.getEmail());
        assertEquals(dto.getTmsSubscriptionDate(), entity.getTmsSubscriptionDate());
        assertEquals(dto.getId(), mappedDto.getId());
        assertEquals(dto.getEmail().toLowerCase(), mappedDto.getEmail());
        assertEquals(dto.getTmsSubscriptionDate(), mappedDto.getTmsSubscriptionDate());
    }
}

