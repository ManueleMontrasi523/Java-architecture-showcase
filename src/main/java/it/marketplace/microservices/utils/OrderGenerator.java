package it.marketplace.microservices.utils;

public class OrderGenerator {
    public static String generateOrderCode() {
        int random6Digits = (int) (Math.random() * 900000) + 100000;
        return "ORDER".concat(String.valueOf(random6Digits));
    }
}
