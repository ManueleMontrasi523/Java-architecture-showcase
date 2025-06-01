package it.marketplace.microservices.controller.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.common.resource.ProductResource;
import it.marketplace.microservices.config.validation.ProductValidator;
import it.marketplace.microservices.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static it.marketplace.microservices.config.mapper.ProductMapper.toDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/product")
@Tag(name = "Product API", description = "Product management")
public class PostProductController {

    @Autowired
    private ProductValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @Autowired
    private ProductService service;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> save(@Valid @RequestBody ProductResource resource) throws ServiceException {
        service.save(toDto(resource));
        return ok().body(Map.of("message", "Product added!"));
    }

}
