package it.marketplace.common.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.dto.ProductOrderDto;
import it.marketplace.common.dto.UserDto;
import it.marketplace.common.resource.OrderResource;
import it.marketplace.common.resource.ProductOrderResource;
import it.marketplace.common.resource.UserResource;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Mapper class for converting between Order DTOs, resources, and entities in the marketplace system.
 * Uses Jackson ObjectMapper for object conversion and handles nested user and product order mappings.
 */
public class OrderMapper {

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule())
            .registerModule(new Hibernate6Module())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Converts an OrderDto to an OrderResource, including nested user and product orders.
     *
     * @param dto the OrderDto to convert
     *
     * @return the corresponding OrderResource
     */
    public static OrderResource toResource(OrderDto dto) {
        if (dto == null) return null;
        OrderResource resource = mapper.convertValue(dto, OrderResource.class);

        if (nonNull(dto.getUser()))
            resource.setUserResource(mapper.convertValue(dto.getUser(), UserResource.class));

        if (nonNull(dto.getProductOrder())) {
            List<ProductOrderResource> products = new ArrayList<>();
            dto.getProductOrder().forEach(product -> {
                products.add(mapper.convertValue(product, ProductOrderResource.class));
            });
            resource.setProductResource(products);
        }
        return resource;
    }

    /**
     * Converts an OrderResource to an OrderDto, including nested user and product orders.
     *
     * @param resource the OrderResource to convert
     *
     * @return the corresponding OrderDto
     */
    public static OrderDto toDto(OrderResource resource) {
        if (resource == null) return null;
        OrderDto dto = mapper.convertValue(resource, OrderDto.class);

        if (nonNull(resource.getUserResource()))
            dto.setUser(mapper.convertValue(resource.getUserResource(), UserDto.class));

        if (nonNull(resource.getProductResource())) {
            List<ProductOrderDto> products = new ArrayList<>();
            resource.getProductResource().forEach(product -> {
                products.add(mapper.convertValue(product, ProductOrderDto.class));
            });
            dto.setProductOrder(products);
        }
        return dto;
    }


}
