package it.marketplace.utils;

/**
 * Utility class for generating unique order codes in the marketplace system.
 */
public class OrderGenerator {
    /**
     * Generates a unique order code in the format ORDERXXXXXX, where XXXXXX is a random 6-digit number.
     *
     * @return the generated order code
     */
    public static String generateOrderCode() {
        int random6Digits = (int) (Math.random() * 900000) + 100000;
        return "ORDER".concat(String.valueOf(random6Digits));
    }
}
