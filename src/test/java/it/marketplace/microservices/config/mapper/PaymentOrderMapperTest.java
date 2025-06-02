package it.marketplace.microservices.config.mapper;

import it.marketplace.microservices.common.dto.PaymentOrderDto;
import it.marketplace.microservices.common.resource.PaymentOrderResource;
import it.marketplace.microservices.database.entity.PaymentOrderEntity;
import it.marketplace.microservices.common.enums.StatusOrderEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentOrderMapperTest {
    @Test
    void shouldMapPaymentOrderDtoToResourceAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        PaymentOrderDto dto = new PaymentOrderDto(1L, "ORD123", StatusOrderEnum.PAID, 100.0, 100.0, now, now);
        // Act
        PaymentOrderResource resource = PaymentOrderMapper.toResource(dto);
        PaymentOrderDto mappedDto = PaymentOrderMapper.toDto(resource);
        // Assert
        assertEquals(dto.getId(), resource.getId());
        assertEquals(dto.getOrderCode(), resource.getOrderCode());
        assertEquals(dto.getOrderDate(), resource.getOrderDate());
        assertEquals(dto.getId(), mappedDto.getId());
        assertEquals(dto.getOrderCode(), mappedDto.getOrderCode());
        assertEquals(dto.getOrderDate(), mappedDto.getOrderDate());
    }

    @Test
    void shouldMapPaymentOrderDtoToEntityAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        PaymentOrderDto dto = new PaymentOrderDto(2L, "ORD456", StatusOrderEnum.CREATED, 200.0, 50.0, now, now);
        // Act
        PaymentOrderEntity entity = PaymentOrderMapper.toEntity(dto);
        PaymentOrderDto mappedDto = PaymentOrderMapper.toDto(entity);
        // Assert
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getOrderCode(), entity.getOrderCode());
        assertEquals(dto.getOrderDate(), entity.getOrderDate());
        assertEquals(dto.getId(), mappedDto.getId());
        assertEquals(dto.getOrderCode(), mappedDto.getOrderCode());
        assertEquals(dto.getOrderDate(), mappedDto.getOrderDate());
    }
}

