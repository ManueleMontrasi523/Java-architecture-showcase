package it.marketplace.microservices.controller.product;

import it.marketplace.microservices.common.exception.ProductServiceException;
import it.marketplace.microservices.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/product")
public class DeleteProductController {

    @Autowired
    private ProductService productService;

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("productCode") String code) throws ProductServiceException {
        productService.deleteByCode(code);
        return ok().body("Product deleted!");
    }

}
