package it.marketplace.paymentInstallments;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.service.PaymentInstallmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for processing payment of a specific installment in the marketplace system.
 * Provides an endpoint to pay a specific rate for an order.
 */
@RestController
@RequestMapping("/payment-order")
@Tag(name = "Payment Order API", description = "Payment Order management")
public class PutPaymentInstallmentsController {

    @Autowired
    private PaymentInstallmentsService service;

    /**
     * Pays a specific installment (rate) for an order.
     * @param orderCode the order code for which to pay the installment
     * @param number the installment number to pay
     * @return a response entity with a confirmation message
     * @throws ServiceException if the payment cannot be processed
     */
    @PutMapping("/pay-rate")
    public ResponseEntity<Map<String, String>> pay(
            @RequestParam(value = "orderCode") String orderCode,
            @RequestParam(value = "number") int number
    ) throws ServiceException {
        service.payInstallments(orderCode, number);
        return ok().body(Map.of("message", "Rate paid!"));
    }

}
