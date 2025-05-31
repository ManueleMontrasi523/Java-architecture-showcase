package it.marketplace.microservices.controller.user;

import it.marketplace.microservices.common.dto.UserDto;
import it.marketplace.microservices.common.exception.UserServiceException;
import it.marketplace.microservices.common.mapper.UserMapper;
import it.marketplace.microservices.common.resource.UserResource;
import it.marketplace.microservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static it.marketplace.microservices.common.mapper.UserMapper.toResource;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
public class GetUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/get")
    public ResponseEntity<UserResource> find(@RequestParam(value = "email") String email) throws UserServiceException {
        return ok().body(toResource(userService.findByEmail(email)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserResource>> findAll() throws UserServiceException {
        List<UserDto> dtos = userService.findAll();

        List<UserResource> resources = new ArrayList<>(dtos.stream()
                .map(UserMapper::toResource)
                .toList());

        return ok().body(resources);
    }

}
