package it.marketplace.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.common.dto.UserDto;
import it.marketplace.common.mapper.UserMapper;
import it.marketplace.common.resource.UserResource;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static it.marketplace.common.mapper.UserMapper.toDto;
import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for creating users in the marketplace system.
 * Provides endpoints to add a single user or multiple users.
 */
@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "User management")
public class PostUserController {

//    @Autowired
//    private UserValidator validator;
//
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.addValidators(validator);
//    }

    @Autowired
    private UserService service;

    /**
     * Adds a new user.
     * @param userResource the user resource to add
     * @param bindingResult the binding result for validation errors
     * @return a response entity with a confirmation message or validation errors
     * @throws ServiceException if the user cannot be added
     */
    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody UserResource userResource, BindingResult bindingResult) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        service.save(toDto(userResource));
        return ok().body(Map.of("message", "User added!"));
    }

    /**
     * Adds multiple new users.
     * @param resources the list of user resources to add
     * @return a response entity with a confirmation message
     * @throws ServiceException if the users cannot be added
     */
    @PostMapping("/add-all")
    public ResponseEntity<?> saveAll(@RequestBody List<UserResource> resources) throws ServiceException {
        List<UserDto> dtos = resources.stream().map(UserMapper::toDto).toList();
        service.saveAll(dtos);
        return ok().body(Map.of("message", "Users added!"));
    }

}
