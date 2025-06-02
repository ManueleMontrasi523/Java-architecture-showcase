package it.marketplace.microservices.config.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.microservices.common.dto.ProductDto;
import it.marketplace.microservices.common.resource.ProductResource;
import it.marketplace.microservices.database.entity.ProductEntity;

public class ProductMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static ProductResource toResource(ProductDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, ProductResource.class);
    }

    public static ProductDto toDto(ProductResource resource) {
        if (resource == null) return null;
        return mapper.convertValue(resource, ProductDto.class);
    }

    public static ProductDto toDto(ProductEntity entity) {
        if (entity == null) return null;
        return mapper.convertValue(entity, ProductDto.class);
    }

    public static ProductEntity toEntity(ProductDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, ProductEntity.class);
    }

}
