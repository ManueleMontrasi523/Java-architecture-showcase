package it.marketplace.common.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.common.dto.ProductOrderDto;
import it.marketplace.common.resource.ProductOrderResource;

/**
 * Mapper class for converting between ProductOrder DTOs, resources, and entities in the marketplace system.
 * Uses Jackson ObjectMapper for object conversion.
 */
public class ProductOrderMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Converts a ProductOrderDto to a ProductOrderResource.
     *
     * @param dto the ProductOrderDto to convert
     *
     * @return the corresponding ProductOrderResource
     */
    public static ProductOrderResource toResource(ProductOrderDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, ProductOrderResource.class);
    }

    /**
     * Converts a ProductOrderResource to a ProductOrderDto.
     *
     * @param resource the ProductOrderResource to convert
     *
     * @return the corresponding ProductOrderDto
     */
    public static ProductOrderDto toDto(ProductOrderResource resource) {
        if (resource == null) return null;
        return mapper.convertValue(resource, ProductOrderDto.class);
    }


}
