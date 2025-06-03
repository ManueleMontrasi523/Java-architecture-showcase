package it.marketplace.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.common.dto.ProductOrderDto;
import it.marketplace.entity.ProductOrderEntity;

/**
 * Mapper class for converting between ProductOrder DTOs, resources, and entities in the marketplace system.
 * Uses Jackson ObjectMapper for object conversion.
 */
public class ProductOrderMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Converts a ProductOrderEntity to a ProductOrderDto.
     *
     * @param entity the ProductOrderEntity to convert
     *
     * @return the corresponding ProductOrderDto
     */
    public static ProductOrderDto toDto(ProductOrderEntity entity) {
        if (entity == null) return null;
        return mapper.convertValue(entity, ProductOrderDto.class);
    }

    /**
     * Converts a ProductOrderDto to a ProductOrderEntity.
     *
     * @param dto the ProductOrderDto to convert
     *
     * @return the corresponding ProductOrderEntity
     */
    public static ProductOrderEntity toEntity(ProductOrderDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, ProductOrderEntity.class);
    }

}
