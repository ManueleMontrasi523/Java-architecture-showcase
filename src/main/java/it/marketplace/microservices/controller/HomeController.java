package it.marketplace.microservices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling navigation to main pages in the marketplace system.
 * Maps root and main entity URLs to their respective HTML templates.
 */
@Controller
public class HomeController {

    /**
     * Handles navigation to the home page.
     * @return the name of the home page template
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Handles navigation to the user page.
     * @return the name of the user page template
     */
    @GetMapping("/user")
    public String user() {
        return "user";
    }

    /**
     * Handles navigation to the product page.
     * @return the name of the product page template
     */
    @GetMapping("/product")
    public String product() {
        return "product";
    }

    /**
     * Handles navigation to the payment page.
     * @return the name of the payment page template
     */
    @GetMapping("/payment")
    public String payment() {
        return "payment";
    }

}

