package it.marketplace.paymentOrder;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.service.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for processing payment of an order in the marketplace system.
 * Provides an endpoint to pay an order, with or without installments.
 */
@RestController
@RequestMapping("/payment-order")
@Tag(name = "Payment Order API", description = "Payment Order management")
public class PutPaymentOrderController {

    @Autowired
    private PaymentOrderService service;

    /**
     * Pays an order, specifying if it is in installments.
     * @param orderCode the order code to pay
     * @param isInstallments whether the payment is for installments
     * @return a response entity with a confirmation message
     * @throws ServiceException if the payment cannot be processed
     */
    @PutMapping("/pay")
    public ResponseEntity<Map<String, String>> pay(
            @RequestParam(value = "orderCode") String orderCode,
            @RequestParam(value = "isInstallments") Boolean isInstallments
    ) throws ServiceException {
        service.payOrder(orderCode, isInstallments);
        return ok().body(Map.of("message", "Order paid!"));
    }

}
