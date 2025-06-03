package it.marketplace.microservices.controller;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HomeControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();
    }

    @Test
    void shouldReturnIndexView_WhenGetHome_ThenArrangeActAssert() throws Exception {
        // Arrange/Act/Assert
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void shouldReturnUserView_WhenGetUser_ThenArrangeActAssert() throws Exception {
        // Arrange/Act/Assert
        assertThrows(ServletException.class, () ->
            mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("user"))
        );
    }

    @Test
    void shouldReturnProductView_WhenGetProduct_ThenArrangeActAssert() throws Exception {
        // Arrange/Act/Assert
        assertThrows(ServletException.class, () ->
            mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
        );
    }

    @Test
    void shouldReturnPaymentView_WhenGetPayment_ThenArrangeActAssert() throws Exception {
        // Arrange/Act/Assert
        assertThrows(ServletException.class, () ->
            mockMvc.perform(get("/payment"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment"))
        );
    }
}

