package it.marketplace.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.common.dto.ProductDto;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.common.mapper.ProductMapper;
import it.marketplace.common.resource.ProductResource;
import it.marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static it.marketplace.common.mapper.ProductMapper.toResource;
import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for retrieving products in the marketplace system.
 * Provides endpoints to get products by code or retrieve all products.
 */
@RestController
@RequestMapping("/product")
@Tag(name = "Product API", description = "Product management")
public class GetProductController {

    @Autowired
    private ProductService service;

    /**
     * Retrieves a product by its code.
     * @param code the product code to retrieve
     * @return a response entity containing the product resource
     * @throws ServiceException if the product cannot be found
     */
    @GetMapping("/get-by-code")
    public ResponseEntity<ProductResource> find(@RequestParam(value = "productCode") String code) throws ServiceException {
        return ok().body(toResource(service.findByCode(code)));
    }

    /**
     * Retrieves all products.
     * @return a response entity containing a list of product resources
     * @throws ServiceException if the products cannot be retrieved
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<ProductResource>> findAll() throws ServiceException {
        List<ProductDto> dtos = service.findAll();

        List<ProductResource> resources = new ArrayList<>(dtos.stream()
                .map(ProductMapper::toResource)
                .toList());

        return ok().body(resources);
    }

}
