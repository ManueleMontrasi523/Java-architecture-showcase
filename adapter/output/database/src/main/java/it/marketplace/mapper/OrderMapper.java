package it.marketplace.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.dto.ProductOrderDto;
import it.marketplace.common.dto.UserDto;
import it.marketplace.entity.OrderEntity;
import it.marketplace.entity.ProductOrderEntity;
import it.marketplace.entity.UserEntity;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * Converts an OrderEntity to an OrderDto, including nested user and product orders.
     *
     * @param entity the OrderEntity to convert
     *
     * @return the corresponding OrderDto
     */
    public static OrderDto toDto(OrderEntity entity) {
        if (entity == null) return null;
        OrderDto dto = mapper.convertValue(entity, OrderDto.class);

        if (nonNull(entity.getUser())) {
            UserDto userDto = mapper.convertValue(entity.getUser(), UserDto.class);
            dto.setUser(userDto);
        }

        if (!CollectionUtils.isEmpty(entity.getProductOrder())) {
            List<ProductOrderDto> productsDto = entity.getProductOrder().stream()
                    .map(ProductOrderMapper::toDto)
                    .collect(Collectors.toList());
            dto.setProductOrder(productsDto);
        }
        return dto;
    }

    /**
     * Converts an OrderDto to an OrderEntity, including nested user and product orders.
     *
     * @param dto the OrderDto to convert
     *
     * @return the corresponding OrderEntity
     */
    public static OrderEntity toEntity(OrderDto dto) {
        if (dto == null) return null;
        OrderEntity entity = mapper.convertValue(dto, OrderEntity.class);

        if (nonNull(dto.getUser()))
            entity.setUser(mapper.convertValue(dto.getUser(), UserEntity.class));

        if (nonNull(dto.getProductOrder())) {
            List<ProductOrderEntity> products = new ArrayList<>();
            dto.getProductOrder().forEach(product -> {
                products.add(mapper.convertValue(product, ProductOrderEntity.class));
            });
            entity.setProductOrder(products);
        }
        return entity;
    }

}
