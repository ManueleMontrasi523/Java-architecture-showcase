package it.marketplace.microservices.controller.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.microservices.common.enums.StatusUserEnum;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for updating the status of a user in the marketplace system.
 * Provides an endpoint to update a user's status by email.
 */
@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "User management")
public class StatusUserController {

    @Autowired
    private UserService service;

    /**
     * Updates the status of a user by email.
     * @param email the email of the user to update
     * @param status the new status to set
     * @return a response entity with a confirmation message
     * @throws ServiceException if the user status cannot be updated
     */
    @PutMapping("/status")
    public ResponseEntity<Map<String, String>> status(@RequestParam("email") String email, @RequestParam("status") StatusUserEnum status) throws ServiceException {
        service.statusByEmail(email, status);
        return ok().body(Map.of("message", "User updated!"));
    }

}
