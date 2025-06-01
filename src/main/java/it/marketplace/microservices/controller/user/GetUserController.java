package it.marketplace.microservices.controller.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.common.dto.UserDto;
import it.marketplace.microservices.common.enums.StatusUserEnum;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.UserMapper;
import it.marketplace.microservices.common.resource.UserResource;
import it.marketplace.microservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static it.marketplace.microservices.config.mapper.UserMapper.toResource;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "User management")
public class GetUserController {

    @Autowired
    private UserService service;

    @GetMapping("/get-by-email")
    public ResponseEntity<UserResource> find(@RequestParam(value = "email") String email) throws ServiceException {
        return ok().body(toResource(service.findByEmail(email)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserResource>> findAll(@RequestParam(value = "status") StatusUserEnum status) throws ServiceException {
        List<UserDto> dtos = service.findAll(status);

        List<UserResource> resources = new ArrayList<>(dtos.stream()
                .map(UserMapper::toResource)
                .toList());

        return ok().body(resources);
    }

}
