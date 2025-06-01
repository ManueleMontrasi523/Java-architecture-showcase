package it.marketplace.microservices.controller.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.common.resource.OrderResource;
import it.marketplace.microservices.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static it.marketplace.microservices.config.mapper.OrderMapper.toDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/order")
@Tag(name = "Order API", description = "Order management")
public class PostOrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> save(@RequestBody OrderResource resource) throws ServiceException {
        service.save(toDto(resource));
        return ok().body(Map.of("message", "Order added!"));
    }

}
