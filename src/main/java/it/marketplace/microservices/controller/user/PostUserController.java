package it.marketplace.microservices.controller.user;

import it.marketplace.microservices.common.exception.UserServiceException;
import it.marketplace.microservices.common.resource.UserResource;
import it.marketplace.microservices.common.validation.UserValidator;
import it.marketplace.microservices.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static it.marketplace.microservices.common.mapper.UserMapper.toDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
public class PostUserController {

    @Autowired
    private UserValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> save(@Valid @RequestBody UserResource userResource, BindingResult bindingResult) throws UserServiceException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        userService.save(toDto(userResource));
        return ok().body("User added!");
    }

}
