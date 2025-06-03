package it.marketplace.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for deleting users in the marketplace system.
 * Provides an endpoint to delete a user by email.
 */
@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "User management")
public class DeleteUserController {

    @Autowired
    private UserService service;

    /**
     * Deletes a user by email.
     * @param email the email of the user to delete
     * @return a response entity with a confirmation message
     * @throws ServiceException if the user cannot be deleted
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> delete(@RequestParam("email") String email) throws ServiceException {
        service.deleteByEmail(email);
        return ok().body(Map.of("message", "User deleted!"));
    }

}
