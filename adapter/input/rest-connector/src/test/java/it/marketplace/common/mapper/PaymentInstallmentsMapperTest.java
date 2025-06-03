package it.marketplace.common.mapper;

import it.marketplace.common.dto.PaymentInstallmentsDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.common.resource.PaymentInstallmentsResource;
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
}

