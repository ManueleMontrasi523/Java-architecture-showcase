package it.marketplace.microservices.config.validation;

import it.marketplace.microservices.common.enums.CategoryEnum;
import it.marketplace.microservices.common.resource.ProductResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductValidatorTest {
    private ProductValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ProductValidator();
    }

    @Test
    void shouldSupportProductResourceClass() {
        // Act & Assert
        assertTrue(validator.supports(ProductResource.class));
        assertFalse(validator.supports(String.class));
    }

    @Test
    void shouldPassValidationForValidProduct() {
        // Arrange
        ProductResource product = new ProductResource();
        product.setName("Laptop");
        product.setProductCode("PROD123");
        product.setCategory(CategoryEnum.TECHNOLOGIES);
        product.setSupply(BigDecimal.TEN);
        product.setPrice(999.99);
        Errors errors = new BeanPropertyBindingResult(product, "product");
        // Act
        validator.validate(product, errors);
        // Assert
        assertFalse(errors.hasErrors());
    }

    @Test
    void shouldRejectIfNameIsMissing() {
        // Arrange
        ProductResource product = new ProductResource();
        product.setProductCode("PROD123");
        product.setCategory(CategoryEnum.TECHNOLOGIES);
        product.setSupply(BigDecimal.TEN);
        product.setPrice(999.99);
        Errors errors = new BeanPropertyBindingResult(product, "product");
        // Act
        validator.validate(product, errors);
        // Assert
        assertTrue(errors.hasErrors());
        assertEquals("Name is mandatory!", errors.getAllErrors().get(0).getDefaultMessage());
    }

    @Test
    void shouldRejectIfProductCodeIsMissing() {
        // Arrange
        ProductResource product = new ProductResource();
        product.setName("Laptop");
        product.setCategory(CategoryEnum.TECHNOLOGIES);
        product.setSupply(BigDecimal.TEN);
        product.setPrice(999.99);
        Errors errors = new BeanPropertyBindingResult(product, "product");
        // Act
        validator.validate(product, errors);
        // Assert
        assertTrue(errors.hasErrors());
        assertEquals("ProductCode is mandatory!", errors.getAllErrors().get(0).getDefaultMessage());
    }

    @Test
    void shouldRejectIfCategoryIsInvalid() {
        // Arrange
        ProductResource product = new ProductResource();
        product.setName("Laptop");
        product.setProductCode("PROD123");
        // Set an invalid category (not in enum)
        product.setSupply(BigDecimal.TEN);
        product.setPrice(999.99);
        Errors errors = new BeanPropertyBindingResult(product, "product");
        // Act
        validator.validate(product, errors);
        // Assert
        assertTrue(errors.hasErrors());
        assertEquals("Category is mandatory!", errors.getAllErrors().get(0).getDefaultMessage());
    }

    @Test
    void shouldRejectIfSupplyIsMissingOrZero() {
        // Arrange
        ProductResource product = new ProductResource();
        product.setName("Laptop");
        product.setProductCode("PROD123");
        product.setCategory(CategoryEnum.TECHNOLOGIES);
        product.setSupply(BigDecimal.ZERO);
        product.setPrice(999.99);
        Errors errors = new BeanPropertyBindingResult(product, "product");
        // Act
        validator.validate(product, errors);
        // Assert
        assertTrue(errors.hasErrors());
        assertEquals("Supply is mandatory!", errors.getAllErrors().get(0).getDefaultMessage());
    }

    @Test
    void shouldRejectIfPriceIsMissingOrNegative() {
        // Arrange
        ProductResource product = new ProductResource();
        product.setName("Laptop");
        product.setProductCode("PROD123");
        product.setCategory(CategoryEnum.TECHNOLOGIES);
        product.setSupply(BigDecimal.TEN);
        product.setPrice(-1.0);
        Errors errors = new BeanPropertyBindingResult(product, "product");
        // Act
        validator.validate(product, errors);
        // Assert
        assertTrue(errors.hasErrors());
        assertEquals("Price is mandatory!", errors.getAllErrors().get(0).getDefaultMessage());
    }
}

