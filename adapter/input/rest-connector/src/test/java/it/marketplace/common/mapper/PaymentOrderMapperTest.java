package it.marketplace.common.mapper;

import it.marketplace.common.dto.PaymentOrderDto;
import it.marketplace.common.enums.StatusOrderEnum;
import it.marketplace.common.resource.PaymentOrderResource;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentOrderMapperTest {

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

}
