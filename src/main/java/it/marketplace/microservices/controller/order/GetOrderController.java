package it.marketplace.microservices.controller.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.common.dto.OrderDto;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.OrderMapper;
import it.marketplace.microservices.common.resource.OrderResource;
import it.marketplace.microservices.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static it.marketplace.microservices.config.mapper.OrderMapper.toResource;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/order")
@Tag(name = "Order API", description = "Order management")
public class GetOrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/get-by-code")
    public ResponseEntity<OrderResource> find(@RequestParam(value = "orderCode") String code) throws ServiceException {
        return ok().body(toResource(service.findByCode(code)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<OrderResource>> findAll() throws ServiceException {
        List<OrderDto> dtos = service.findAll();

        List<OrderResource> resources = new ArrayList<>(dtos.stream()
                .map(OrderMapper::toResource)
                .toList());

        return ok().body(resources);
    }

}
