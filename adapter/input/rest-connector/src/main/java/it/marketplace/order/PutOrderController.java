package it.marketplace.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.common.resource.OrderResource;
import it.marketplace.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static it.marketplace.common.mapper.OrderMapper.toDto;
import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for updating orders in the marketplace system.
 * Provides endpoints to update or cancel an order by its code.
 */
@RestController
@RequestMapping("/order")
@Tag(name = "Order API", description = "Order management")
public class PutOrderController {

    @Autowired
    private OrderService service;

    /**
     * Updates an existing order.
     * @param resource the order resource to update
     * @return a response entity with a confirmation message
     * @throws ServiceException if the order cannot be updated
     */
    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> update(@Valid @RequestBody OrderResource resource) throws ServiceException {
        service.update(toDto(resource));
        return ok().body(Map.of("message", "Order Updated!"));
    }

    /**
     * Cancels an order by its code.
     * @param code the order code to cancel
     * @return a response entity with a confirmation message
     * @throws ServiceException if the order cannot be cancelled
     */
    @PutMapping("/cancel")
    public ResponseEntity<Map<String, String>> cancel(@RequestParam("orderCode") String code) throws ServiceException {
        service.cancel(code);
        return ok().body(Map.of("message", "Order Cancelled!"));
    }

}
