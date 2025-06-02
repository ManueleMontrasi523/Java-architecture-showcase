package it.marketplace.microservices.controller.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.common.dto.ProductDto;
import it.marketplace.microservices.common.resource.ProductResource;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.ProductMapper;
import it.marketplace.microservices.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static it.marketplace.microservices.config.mapper.ProductMapper.toDto;
import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for creating products in the marketplace system.
 * Provides endpoints to add a single product or multiple products.
 */
@RestController
@RequestMapping("/product")
@Tag(name = "Product API", description = "Product management")
public class PostProductController {

//    @Autowired
//    private ProductValidator validator;
//
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.addValidators(validator);
//    }

    @Autowired
    private ProductService service;

    /**
     * Adds a new product.
     * @param resource the product resource to add
     * @return a response entity with a confirmation message
     * @throws ServiceException if the product cannot be added
     */
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> save(@RequestBody ProductResource resource) throws ServiceException {
        service.save(toDto(resource));
        return ok().body(Map.of("message", "Product added!"));
    }

    /**
     * Adds multiple new products.
     * @param resources the list of product resources to add
     * @return a response entity with a confirmation message
     * @throws ServiceException if the products cannot be added
     */
    @PostMapping("/add-all")
    public ResponseEntity<Map<String, String>> saveAll(@RequestBody List<ProductResource> resources) throws ServiceException {
        List<ProductDto> dtos = resources.stream().map(ProductMapper::toDto).toList();
        service.saveAll(dtos);
        return ok().body(Map.of("message", "Products added!"));
    }

}
