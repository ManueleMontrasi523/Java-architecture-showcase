package it.marketplace.common.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.common.dto.ProductDto;
import it.marketplace.common.resource.ProductResource;

/**
 * Mapper class for converting between Product DTOs, resources, and entities in the marketplace system.
 * Uses Jackson ObjectMapper for object conversion.
 */
public class ProductMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Converts a ProductDto to a ProductResource.
     *
     * @param dto the ProductDto to convert
     *
     * @return the corresponding ProductResource
     */
    public static ProductResource toResource(ProductDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, ProductResource.class);
    }

    /**
     * Converts a ProductResource to a ProductDto.
     *
     * @param resource the ProductResource to convert
     *
     * @return the corresponding ProductDto
     */
    public static ProductDto toDto(ProductResource resource) {
        if (resource == null) return null;
        return mapper.convertValue(resource, ProductDto.class);
    }

}
