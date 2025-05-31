package it.marketplace.microservices.controller.user;

import it.marketplace.microservices.common.exception.UserServiceException;
import it.marketplace.microservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
public class DeleteUserController {

    @Autowired
    private UserService userService;

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("email") String email) throws UserServiceException {
        userService.deleteByEmail(email);
        return ok().body("User deleted!");
    }

}
