package it.marketplace.common.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.common.dto.UserDto;
import it.marketplace.common.resource.UserResource;

/**
 * Mapper class for converting between User DTOs, resources, and entities in the marketplace system.
 * Uses Jackson ObjectMapper for object conversion and ensures email is stored in lowercase.
 */
public class UserMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    /**
     * Converts a UserDto to a UserResource.
     *
     * @param dto the UserDto to convert
     *
     * @return the corresponding UserResource
     */
    public static UserResource toResource(UserDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, UserResource.class);
    }

    /**
     * Converts a UserResource to a UserDto.
     *
     * @param resource the UserResource to convert
     *
     * @return the corresponding UserDto
     */
    public static UserDto toDto(UserResource resource) {
        if (resource == null) return null;
        return mapper.convertValue(resource, UserDto.class);
    }

}
