package it.marketplace.microservices.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderGeneratorTest {

    @Test
    void shouldGenerateOrderCodeWithPrefixAndSixDigits_ArrangeActAssert() {
        // Arrange/Act
        String code = OrderGenerator.generateOrderCode();
        // Assert
        assertNotNull(code);
        assertTrue(code.startsWith("ORDER"));
        assertEquals(11, code.length());
        String digits = code.substring(5);
        assertTrue(digits.matches("\\d{6}"));
    }
}

