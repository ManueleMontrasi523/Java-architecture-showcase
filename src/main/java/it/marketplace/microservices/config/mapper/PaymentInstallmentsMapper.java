package it.marketplace.microservices.config.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.marketplace.microservices.common.dto.PaymentInstallmentsDto;
import it.marketplace.microservices.common.resource.PaymentInstallmentsResource;
import it.marketplace.microservices.database.entity.PaymentInstallmentsEntity;

public class PaymentInstallmentsMapper {

    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static PaymentInstallmentsResource toResource(PaymentInstallmentsDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, PaymentInstallmentsResource.class);
    }

    public static PaymentInstallmentsDto toDto(PaymentInstallmentsResource resource) {
        if (resource == null) return null;
        return mapper.convertValue(resource, PaymentInstallmentsDto.class);
    }

    public static PaymentInstallmentsDto toDto(PaymentInstallmentsEntity entity) {
        if (entity == null) return null;
        return mapper.convertValue(entity, PaymentInstallmentsDto.class);
    }

    public static PaymentInstallmentsEntity toEntity(PaymentInstallmentsDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, PaymentInstallmentsEntity.class);
    }

}
