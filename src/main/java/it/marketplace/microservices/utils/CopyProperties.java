package it.marketplace.microservices.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

/**
 * Utility class for copying non-null properties from a source object to a target object.
 * Uses Spring's BeanUtils and BeanWrapper to perform the copy operation.
 */
public class CopyProperties {
    /**
     * Copies all non-null properties from the source object to the target object.
     *
     * @param src the source object
     * @param target the target object
     */
    public static void copyNonNullProperties(Object src, Object target) {
        BeanWrapper srcWrap = new BeanWrapperImpl(src);

        String[] nullPropertyNames = Stream.of(srcWrap.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(propName -> srcWrap.getPropertyValue(propName) == null)
                .toArray(String[]::new);

        BeanUtils.copyProperties(src, target, nullPropertyNames);
    }

}
