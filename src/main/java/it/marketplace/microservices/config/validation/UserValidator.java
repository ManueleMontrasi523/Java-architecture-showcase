package it.marketplace.microservices.config.validation;


import it.marketplace.microservices.common.resource.UserResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * Validator for UserResource objects in the marketplace system.
 * Validates required fields such as name, lastname, and email format.
 */
@Component
public class UserValidator implements Validator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Checks if the validator supports the given class.
     * @param clazz the class to check
     * @return true if supported, false otherwise
     */
    public boolean supports(Class clazz) {
        return UserResource.class.equals(clazz);
    }

    /**
     * Validates the given UserResource object and adds errors if validation fails.
     * @param target the object to validate
     * @param errors the Errors object to register validation errors
     */
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
