package it.marketplace.microservices.controller.paymentInstallments;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.common.dto.PaymentInstallmentsDto;
import it.marketplace.microservices.common.resource.PaymentInstallmentsResource;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.PaymentInstallmentsMapper;
import it.marketplace.microservices.service.PaymentInstallmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/payment-order")
@Tag(name = "Payment Order API", description = "Payment Order management")
public class GetPaymentInstallmentsController {

    @Autowired
    private PaymentInstallmentsService service;

    @GetMapping("/get-by-code")
    public ResponseEntity<List<PaymentInstallmentsResource>> find(@RequestParam(value = "orderCode") String orderCode) throws ServiceException {
        List<PaymentInstallmentsDto> dtos = service.findAllByCode(orderCode);

        List<PaymentInstallmentsResource> resources = new ArrayList<>(dtos.stream()
                .map(PaymentInstallmentsMapper::toResource)
                .toList());

        return ok().body(resources);
    }
}
