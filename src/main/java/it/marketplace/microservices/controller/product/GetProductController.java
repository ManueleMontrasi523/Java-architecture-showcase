package it.marketplace.microservices.controller.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.common.dto.ProductDto;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.ProductMapper;
import it.marketplace.microservices.common.resource.ProductResource;
import it.marketplace.microservices.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static it.marketplace.microservices.config.mapper.ProductMapper.toResource;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/product")
@Tag(name = "Product API", description = "Product management")
public class GetProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/get-by-code")
    public ResponseEntity<ProductResource> find(@RequestParam(value = "productCode") String code) throws ServiceException {
        return ok().body(toResource(service.findByCode(code)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductResource>> findAll() throws ServiceException {
        List<ProductDto> dtos = service.findAll();

        List<ProductResource> resources = new ArrayList<>(dtos.stream()
                .map(ProductMapper::toResource)
                .toList());

        return ok().body(resources);
    }

}
