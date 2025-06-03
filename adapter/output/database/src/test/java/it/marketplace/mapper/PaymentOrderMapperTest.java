package it.marketplace.mapper;

import it.marketplace.common.dto.PaymentOrderDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.entity.PaymentOrderEntity;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentOrderMapperTest {
    @Test
    public void shouldMapPaymentOrderDtoToEntityAndBack() {
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
        Assertions.assertEquals(dto.getId(), mappedDto.getId());
        Assertions.assertEquals(dto.getOrderCode(), mappedDto.getOrderCode());
        Assertions.assertEquals(dto.getOrderDate(), mappedDto.getOrderDate());
    }
}

