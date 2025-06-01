package it.marketplace.microservices.config.validation;


import it.marketplace.microservices.common.enums.CategoryEnum;
import it.marketplace.microservices.common.resource.ProductResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class ProductValidator implements Validator {

    private List<CategoryEnum> categorys = List.of(CategoryEnum.values());

    public boolean supports(Class clazz) {
        return ProductResource.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        ProductResource resource = (ProductResource) target;

        if (StringUtils.isEmpty(resource.getName()))
            errors.reject("ERROR_VALIDATION", "Name is mandatory!");
        if (StringUtils.isEmpty(resource.getProductCode()))
            errors.reject("ERROR_VALIDATION", "ProductCode is mandatory!");
        if (!categorys.contains(resource.getCategory()))
            errors.reject("ERROR_VALIDATION", "Category is mandatory!");
        if (BigDecimal.ZERO.equals(resource.getSupply()) || isNull(resource.getSupply()))
            errors.reject("ERROR_VALIDATION", "Supply is mandatory!");
        if (Double.isNaN(resource.getPrice()))
            errors.reject("ERROR_VALIDATION", "Price is mandatory!");
    }

}
