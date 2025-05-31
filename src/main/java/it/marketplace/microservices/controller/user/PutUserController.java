package it.marketplace.microservices.controller.user;

import it.marketplace.microservices.common.exception.UserServiceException;
import it.marketplace.microservices.common.resource.UserResource;
import it.marketplace.microservices.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static it.marketplace.microservices.common.mapper.UserMapper.toDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
public class PutUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/update")
    public ResponseEntity<String> update(@Valid @RequestBody UserResource userResource) throws UserServiceException {
        userService.update(toDto(userResource));
        return ok().body("User updated!");
    }

}
