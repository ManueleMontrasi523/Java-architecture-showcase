package it.marketplace.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.common.dto.OrderDto;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.common.mapper.OrderMapper;
import it.marketplace.common.resource.OrderResource;
import it.marketplace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static it.marketplace.common.mapper.OrderMapper.toResource;
import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for retrieving orders in the marketplace system.
 * Provides endpoints to get orders by code or retrieve all orders.
 */
@RestController
@RequestMapping("/order")
@Tag(name = "Order API", description = "Order management")
public class GetOrderController {

    @Autowired
    private OrderService service;

    /**
     * Retrieves an order by its code.
     *
     * @param code the order code to retrieve
     *
     * @return a response entity containing the order resource
     *
     * @throws ServiceException if the order cannot be found
     */
    @GetMapping("/get-by-code")
    public ResponseEntity<OrderResource> find(@RequestParam(value = "orderCode") String code) throws ServiceException {
        return ok().body(toResource(service.findByCode(code)));
    }

    /**
     * Retrieves all orders.
     *
     * @return a response entity containing a list of order resources
     *
     * @throws ServiceException if the orders cannot be retrieved
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<OrderResource>> findAll() throws ServiceException {
        List<OrderDto> dtos = service.findAll();

        List<OrderResource> resources = new ArrayList<>(dtos.stream()
                .map(OrderMapper::toResource)
                .toList());

        return ok().body(resources);
    }

}
