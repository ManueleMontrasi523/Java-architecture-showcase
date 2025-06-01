package it.marketplace.microservices.config.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.microservices.common.dto.ProductOrderDto;
import it.marketplace.microservices.common.resource.ProductOrderResource;
import it.marketplace.microservices.database.entity.ProductOrderEntity;

public class ProductOrderMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static ProductOrderResource toResource(ProductOrderDto dto) {
        return mapper.convertValue(dto, ProductOrderResource.class);
    }

    public static ProductOrderDto toDto(ProductOrderResource resource) {
        return mapper.convertValue(resource, ProductOrderDto.class);
    }

    public static ProductOrderDto toDto(ProductOrderEntity entity) {
        if (entity == null) return null;
        return mapper.convertValue(entity, ProductOrderDto.class);
    }

    public static ProductOrderEntity toEntity(ProductOrderDto dto) {
        return mapper.convertValue(dto, ProductOrderEntity.class);
    }

}
