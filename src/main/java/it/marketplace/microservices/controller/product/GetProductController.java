package it.marketplace.microservices.controller.product;

import it.marketplace.microservices.common.dto.ProductDto;
import it.marketplace.microservices.common.exception.ProductServiceException;
import it.marketplace.microservices.common.mapper.ProductMapper;
import it.marketplace.microservices.common.resource.ProductResource;
import it.marketplace.microservices.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static it.marketplace.microservices.common.mapper.ProductMapper.toResource;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/product")
public class GetProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/get")
    public ResponseEntity<ProductResource> find(@RequestParam(value = "productCode") String code) throws ProductServiceException {
        return ok().body(toResource(productService.findByCode(code)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductResource>> findAll() throws ProductServiceException {
        List<ProductDto> dtos = productService.findAll();

        List<ProductResource> resources = new ArrayList<>(dtos.stream()
                .map(ProductMapper::toResource)
                .toList());

        return ok().body(resources);
    }

}
