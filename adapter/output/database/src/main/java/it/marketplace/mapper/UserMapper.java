package it.marketplace.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.common.dto.UserDto;
import it.marketplace.entity.UserEntity;

/**
 * Mapper class for converting between User DTOs, resources, and entities in the marketplace system.
 * Uses Jackson ObjectMapper for object conversion and ensures email is stored in lowercase.
 */
public class UserMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Converts a UserEntity to a UserDto.
     *
     * @param entity the UserEntity to convert
     *
     * @return the corresponding UserDto
     */
    public static UserDto toDto(UserEntity entity) {
        if (entity == null) return null;
        return mapper.convertValue(entity, UserDto.class);
    }

    /**
     * Converts a UserDto to a UserEntity and ensures the email is lowercase.
     *
     * @param dto the UserDto to convert
     *
     * @return the corresponding UserEntity
     */
    public static UserEntity toEntity(UserDto dto) {
        if (dto == null) return null;
        UserEntity entity = mapper.convertValue(dto, UserEntity.class);
        entity.setEmail(entity.getEmail().toLowerCase());
        return entity;
    }

}
