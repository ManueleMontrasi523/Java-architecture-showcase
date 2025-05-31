package it.marketplace.microservices.controller.user;

import it.marketplace.microservices.common.enums.StatusUserEnum;
import it.marketplace.microservices.common.exception.UserServiceException;
import it.marketplace.microservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
public class StatusUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/status")
    public ResponseEntity<String> status(@RequestParam("email") String email, @RequestParam("status") StatusUserEnum status) throws UserServiceException {
        userService.statusByEmail(email, status);
        return ok().body("User updated!");
    }

}
