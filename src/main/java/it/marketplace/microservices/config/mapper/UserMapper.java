package it.marketplace.microservices.config.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.microservices.common.dto.UserDto;
import it.marketplace.microservices.common.resource.UserResource;
import it.marketplace.microservices.database.entity.UserEntity;

public class UserMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static UserResource toResource(UserDto dto) {
        return mapper.convertValue(dto, UserResource.class);
    }

    public static UserDto toDto(UserResource resource) {
        return mapper.convertValue(resource, UserDto.class);
    }

    public static UserDto toDto(UserEntity entity) {
        return mapper.convertValue(entity, UserDto.class);
    }

    public static UserEntity toEntity(UserDto dto) {
        UserEntity entity = mapper.convertValue(dto, UserEntity.class);
        entity.setEmail(entity.getEmail().toLowerCase());
        return entity;
    }

}
