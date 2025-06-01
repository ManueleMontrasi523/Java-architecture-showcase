package it.marketplace.microservices.controller.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.common.resource.OrderResource;
import it.marketplace.microservices.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static it.marketplace.microservices.config.mapper.OrderMapper.toDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/order")
@Tag(name = "Order API", description = "Order management")
public class PutOrderController {

    @Autowired
    private OrderService service;

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> update(@Valid @RequestBody OrderResource resource) throws ServiceException {
        service.update(toDto(resource));
        return ok().body(Map.of("message", "Order Updated!"));
    }

    @PutMapping("/cancel")
    public ResponseEntity<Map<String, String>> cancel(@RequestParam("orderCode") String code) throws ServiceException {
        service.cancel(code);
        return ok().body(Map.of("message", "Order Cancelled!"));
    }

}
