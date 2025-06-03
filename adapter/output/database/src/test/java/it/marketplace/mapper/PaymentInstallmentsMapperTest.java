package it.marketplace.mapper;

import it.marketplace.common.dto.PaymentInstallmentsDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.entity.PaymentInstallmentsEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentInstallmentsMapperTest {
    @Test
    void shouldMapPaymentInstallmentsDtoToEntityAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        PaymentInstallmentsDto dto = new PaymentInstallmentsDto(2L, "REF456", "ORD456", StatusOrderEnum.CREATED, 200.0, now);
        // Act
        PaymentInstallmentsEntity entity = PaymentInstallmentsMapper.toEntity(dto);
        PaymentInstallmentsDto mappedDto = PaymentInstallmentsMapper.toDto(entity);
        // Assert
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getReference(), entity.getReference());
        assertEquals(dto.getOrderCode(), entity.getOrderCode());
        assertEquals(dto.getTmsUpdate(), entity.getTmsUpdate());
        assertEquals(dto.getId(), mappedDto.getId());
        assertEquals(dto.getReference(), mappedDto.getReference());
        assertEquals(dto.getOrderCode(), mappedDto.getOrderCode());
        assertEquals(dto.getTmsUpdate(), mappedDto.getTmsUpdate());
    }
}

