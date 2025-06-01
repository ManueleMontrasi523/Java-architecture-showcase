package it.marketplace.microservices.controller.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.common.resource.UserResource;
import it.marketplace.microservices.config.validation.UserValidator;
import it.marketplace.microservices.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static it.marketplace.microservices.config.mapper.UserMapper.toDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "User management")
public class PostUserController {

    @Autowired
    private UserValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @Autowired
    private UserService service;

    @PostMapping("/add")
    public ResponseEntity<?> save(@Valid @RequestBody UserResource userResource, BindingResult bindingResult) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        service.save(toDto(userResource));
        return ok().body(Map.of("message", "User added!"));
    }

}
