package it.marketplace.mapper;

import it.marketplace.common.dto.UserDto;
import it.marketplace.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {
    @Test
    void shouldMapUserDtoToEntityAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        UserDto dto = new UserDto(2L, "Jane", "Smith", "JANE.SMITH@EMAIL.COM", "Second Street", "Paris", null, null, now, now);
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

