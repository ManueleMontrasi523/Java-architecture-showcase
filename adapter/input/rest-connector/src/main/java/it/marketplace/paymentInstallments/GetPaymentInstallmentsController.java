package it.marketplace.paymentInstallments;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.common.dto.PaymentInstallmentsDto;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.common.mapper.PaymentInstallmentsMapper;
import it.marketplace.common.resource.PaymentInstallmentsResource;
import it.marketplace.service.PaymentInstallmentsService;
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
 * REST controller for retrieving payment installments in the marketplace system.
 * Provides an endpoint to get all payment installments by order code.
 */
@RestController
@RequestMapping("/payment-order")
@Tag(name = "Payment Order API", description = "Payment Order management")
public class GetPaymentInstallmentsController {

    @Autowired
    private PaymentInstallmentsService service;

    /**
     * Retrieves all payment installments for a given order code.
     *
     * @param orderCode the order code to retrieve installments for
     *
     * @return a response entity containing a list of payment installment resources
     *
     * @throws ServiceException if the installments cannot be retrieved
     */
    @GetMapping("/get-by-code")
    public ResponseEntity<List<PaymentInstallmentsResource>> find(@RequestParam(value = "orderCode") String orderCode) throws ServiceException {
        List<PaymentInstallmentsDto> dtos = service.findAllByCode(orderCode);

        List<PaymentInstallmentsResource> resources = new ArrayList<>(dtos.stream()
                .map(PaymentInstallmentsMapper::toResource)
                .toList());

        return ok().body(resources);
    }
}
