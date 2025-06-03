package it.marketplace.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.common.dto.ProductDto;
import it.marketplace.entity.ProductEntity;

/**
 * Mapper class for converting between Product DTOs, resources, and entities in the marketplace system.
 * Uses Jackson ObjectMapper for object conversion.
 */
public class ProductMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Converts a ProductEntity to a ProductDto.
     *
     * @param entity the ProductEntity to convert
     *
     * @return the corresponding ProductDto
     */
    public static ProductDto toDto(ProductEntity entity) {
        if (entity == null) return null;
        return mapper.convertValue(entity, ProductDto.class);
    }

    /**
     * Converts a ProductDto to a ProductEntity.
     *
     * @param dto the ProductDto to convert
     *
     * @return the corresponding ProductEntity
     */
    public static ProductEntity toEntity(ProductDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, ProductEntity.class);
    }

}
