package it.marketplace.microservices.common.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.microservices.common.dto.OrderDto;
import it.marketplace.microservices.common.resource.OrderResource;
import it.marketplace.microservices.database.entity.OrderEntity;

public class OrderMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static OrderResource toResource(OrderDto dto) {
        return mapper.convertValue(dto, OrderResource.class);
    }

    public static OrderDto toDto(OrderResource resource) {
        return mapper.convertValue(resource, OrderDto.class);
    }

    public static OrderDto toDto(OrderEntity entity) {
        return mapper.convertValue(entity, OrderDto.class);
    }

    public static OrderEntity toEntity(OrderDto dto) {
        return mapper.convertValue(dto, OrderEntity.class);
    }

}
