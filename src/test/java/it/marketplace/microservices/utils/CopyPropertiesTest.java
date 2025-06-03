package it.marketplace.microservices.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CopyPropertiesTest {

    static class TestBean {
        private String a;
        private String b;
        public String getA() { return a; }
        public void setA(String a) { this.a = a; }
        public String getB() { return b; }
        public void setB(String b) { this.b = b; }
    }

    @Test
    void shouldCopyOnlyNonNullProperties_ArrangeActAssert() {
        // Arrange
        TestBean src = new TestBean();
        src.setA("foo");
        src.setB(null);
        TestBean target = new TestBean();
        target.setA("bar");
        target.setB("baz");
        // Act
        CopyProperties.copyNonNullProperties(src, target);
        // Assert
        assertEquals("foo", target.getA());
        assertEquals("baz", target.getB()); // b non viene sovrascritto perché null in src
    }
}

