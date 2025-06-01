package it.marketplace.microservices.controller.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/product")
@Tag(name = "Product API", description = "Product management")
public class DeleteProductController {

    @Autowired
    private ProductService service;

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> delete(@RequestParam("productCode") String code) throws ServiceException {
        service.deleteByCode(code);
        return ok().body(Map.of("message", "Product deleted!"));
    }

}
