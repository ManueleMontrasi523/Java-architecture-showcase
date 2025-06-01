package it.marketplace.microservices.config.validation;


import it.marketplace.microservices.common.resource.UserResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public boolean supports(Class clazz) {
        return UserResource.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        UserResource resource = (UserResource) target;

        if (StringUtils.isEmpty(resource.getName()))
            errors.reject("ERROR_VALIDATION", "Name is mandatory!");
        if (StringUtils.isEmpty(resource.getLastname()))
            errors.reject("ERROR_VALIDATION", "lastname is mandatory!");

        if (StringUtils.isEmpty(resource.getEmail()) || !EMAIL_PATTERN.matcher(resource.getEmail()).matches())
            errors.reject("ERROR_VALIDATION", "Email missing or with format invalid!");

    }

}
