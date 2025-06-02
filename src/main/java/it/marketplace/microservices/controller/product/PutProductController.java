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

/**
 * REST controller for updating products in the marketplace system.
 * Provides an endpoint to update a product.
 */
@RestController
@RequestMapping("/product")
@Tag(name = "Product API", description = "Product management")
public class PutProductController {

    @Autowired
    private ProductValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @Autowired
    private ProductService service;

    /**
     * Updates an existing product.
     * @param resource the product resource to update
     * @return a response entity with a confirmation message
     * @throws ServiceException if the product cannot be updated
     */
    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> update(@Valid @RequestBody ProductResource resource) throws ServiceException {
        service.update(toDto(resource));
        return ok().body(Map.of("message", "Product updated!"));
    }

}
