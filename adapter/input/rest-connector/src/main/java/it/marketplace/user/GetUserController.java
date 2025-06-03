package it.marketplace.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.common.dto.UserDto;
import it.marketplace.common.enums.StatusUserEnum;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.common.mapper.UserMapper;
import it.marketplace.common.resource.UserResource;
import it.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static it.marketplace.common.mapper.UserMapper.toResource;
import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for retrieving users in the marketplace system.
 * Provides endpoints to get users by email or retrieve all users by status.
 */
@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "User management")
public class GetUserController {

    @Autowired
    private UserService service;

    /**
     * Retrieves a user by email.
     *
     * @param email the email of the user to retrieve
     *
     * @return a response entity containing the user resource
     *
     * @throws ServiceException if the user cannot be found
     */
    @GetMapping("/get-by-email")
    public ResponseEntity<UserResource> find(@RequestParam(value = "email") String email) throws ServiceException {
        return ok().body(toResource(service.findByEmail(email)));
    }

    /**
     * Retrieves all users by status.
     *
     * @param status the status to filter users
     *
     * @return a response entity containing a list of user resources
     *
     * @throws ServiceException if the users cannot be retrieved
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<UserResource>> findAll(@RequestParam(value = "status") StatusUserEnum status) throws ServiceException {
        List<UserDto> dtos = service.findAll(status);

        List<UserResource> resources = new ArrayList<>(dtos.stream()
                .map(UserMapper::toResource)
                .toList());

        return ok().body(resources);
    }

}
