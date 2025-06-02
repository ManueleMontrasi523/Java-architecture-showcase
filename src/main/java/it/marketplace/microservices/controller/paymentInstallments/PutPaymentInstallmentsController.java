package it.marketplace.microservices.controller.paymentInstallments;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.service.PaymentInstallmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/payment-order")
@Tag(name = "Payment Order API", description = "Payment Order management")
public class PutPaymentInstallmentsController {

    @Autowired
    private PaymentInstallmentsService service;

    @PutMapping("/pay-rate")
    public ResponseEntity<Map<String, String>> pay(
            @RequestParam(value = "orderCode") String orderCode,
            @RequestParam(value = "number") int number
    ) throws ServiceException {
        service.payInstallments(orderCode, number);
        return ok().body(Map.of("message", "Rate paid!"));
    }

}
