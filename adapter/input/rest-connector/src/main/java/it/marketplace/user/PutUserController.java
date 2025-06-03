package it.marketplace.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.common.resource.UserResource;
import it.marketplace.common.validation.UserValidator;
import it.marketplace.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static it.marketplace.common.mapper.UserMapper.toDto;
import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for updating users in the marketplace system.
 * Provides an endpoint to update a user.
 */
@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "User management")
public class PutUserController {

    @Autowired
    private UserValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @Autowired
    private UserService service;

    /**
     * Updates an existing user.
     * @param userResource the user resource to update
     * @return a response entity with a confirmation message
     * @throws ServiceException if the user cannot be updated
     */
    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> update(@Valid @RequestBody UserResource userResource) throws ServiceException {
        service.update(toDto(userResource));
        return ok().body(Map.of("message", "User updated!"));
    }

}
