package it.marketplace.microservices.utils;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

public class CopyProperties {
    public static void copyNonNullProperties(Object src, Object target) {
        BeanWrapper srcWrap = new BeanWrapperImpl(src);

        String[] nullPropertyNames = Stream.of(srcWrap.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(propName -> srcWrap.getPropertyValue(propName) == null)
                .toArray(String[]::new);

        BeanUtils.copyProperties(src, target, nullPropertyNames);
    }

}
