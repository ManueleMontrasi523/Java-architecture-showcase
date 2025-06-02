package it.marketplace.microservices.config.mapper;

import it.marketplace.microservices.common.dto.PaymentInstallmentsDto;
import it.marketplace.microservices.common.resource.PaymentInstallmentsResource;
import it.marketplace.microservices.database.entity.PaymentInstallmentsEntity;
import it.marketplace.microservices.common.enums.StatusOrderEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentInstallmentsMapperTest {
    @Test
    void shouldMapPaymentInstallmentsDtoToResourceAndBack() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        PaymentInstallmentsDto dto = new PaymentInstallmentsDto(1L, "REF123", "ORD123", StatusOrderEnum.PAID, 100.0, now);
        // Act
        PaymentInstallmentsResource resource = PaymentInstallmentsMapper.toResource(dto);
        PaymentInstallmentsDto mappedDto = PaymentInstallmentsMapper.toDto(resource);
        // Assert
        assertEquals(dto.getId(), resource.getId());
        assertEquals(dto.getReference(), resource.getReference());
        assertEquals(dto.getOrderCode(), resource.getOrderCode());
        assertEquals(dto.getTmsUpdate(), resource.getTmsUpdate());
        assertEquals(dto.getId(), mappedDto.getId());
        assertEquals(dto.getReference(), mappedDto.getReference());
        assertEquals(dto.getOrderCode(), mappedDto.getOrderCode());
        assertEquals(dto.getTmsUpdate(), mappedDto.getTmsUpdate());
    }

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

