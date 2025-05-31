package it.marketplace.microservices.controller.product;

import it.marketplace.microservices.common.exception.ProductServiceException;
import it.marketplace.microservices.common.resource.ProductResource;
import it.marketplace.microservices.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.marketplace.microservices.common.mapper.ProductMapper.toDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/product")
public class PutProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody ProductResource resource) throws ProductServiceException {
        productService.update(toDto(resource));
        return ok().body("User updated!");
    }

}
