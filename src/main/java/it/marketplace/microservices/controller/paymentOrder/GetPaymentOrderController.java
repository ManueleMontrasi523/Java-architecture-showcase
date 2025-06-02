package it.marketplace.microservices.controller.paymentOrder;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.common.dto.PaymentOrderDto;
import it.marketplace.microservices.common.resource.PaymentOrderResource;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.PaymentOrderMapper;
import it.marketplace.microservices.service.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for retrieving payment orders in the marketplace system.
 * Provides endpoints to get payment orders by user email or retrieve all payment orders.
 */
@RestController
@RequestMapping("/payment-order")
@Tag(name = "Payment Order API", description = "Payment Order management")
public class GetPaymentOrderController {

    @Autowired
    private PaymentOrderService service;

    /**
     * Retrieves all payment orders for a given user email.
     * @param email the user email to retrieve payment orders for
     * @return a response entity containing a list of payment order resources
     * @throws ServiceException if the payment orders cannot be retrieved
     */
    @GetMapping("/get-by-user")
    public ResponseEntity<List<PaymentOrderResource>> find(@RequestParam(value = "email") String email) throws ServiceException {
        List<PaymentOrderDto> dtos = service.findOrderByEmail(email);

        List<PaymentOrderResource> resources = new ArrayList<>(dtos.stream()
                .map(PaymentOrderMapper::toResource)
                .toList());

        return ok().body(resources);
    }

    /**
     * Retrieves all payment orders.
     * @return a response entity containing a list of payment order resources
     * @throws ServiceException if the payment orders cannot be retrieved
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<PaymentOrderResource>> findAll() throws ServiceException {
        List<PaymentOrderDto> dtos = service.findAll();

        List<PaymentOrderResource> resources = new ArrayList<>(dtos.stream()
                .map(PaymentOrderMapper::toResource)
                .toList());

        return ok().body(resources);
    }

}
