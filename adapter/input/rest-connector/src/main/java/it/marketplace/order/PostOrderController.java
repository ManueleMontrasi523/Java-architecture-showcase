package it.marketplace.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.common.resource.OrderResource;
import it.marketplace.rabbitmq.RabbitMqProducer;
import it.marketplace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static it.marketplace.common.mapper.OrderMapper.toDto;
import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for creating orders in the marketplace system.
 * Provides endpoints to add a single order or multiple orders.
 */
@RestController
@RequestMapping("/order")
@Tag(name = "Order API", description = "Order management")
public class PostOrderController {

    @Autowired
    private OrderService service;
    @Autowired
    private RabbitMqProducer producer;

    /**
     * Adds a new order.
     *
     * @param resource the order resource to add
     *
     * @return a response entity with a confirmation message
     *
     * @throws ServiceException if the order cannot be added
     */
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> save(@RequestBody OrderResource resource) throws ServiceException {
        String orderCode = service.save(toDto(resource));
        producer.sendMessageNewOrder(orderCode);
        return ok().body(Map.of("message", "Order added!"));
    }

}
