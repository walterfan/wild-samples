package com.github.walterfan.example.java8;

import org.testng.annotations.Test;

import java.util.Optional;

/**
 * Created by yafan on 23/12/2017.
 */
public class OptionalTest {
    @Test
    public void testOptional() {
        String x = "walter";
        if (x != null) {
            String t = x.trim();
            if (t.length() > 1) {
                System.out.println(t);
            }
        }

        Optional.ofNullable(x).
                map(String::trim).
                filter(t -> t.length() > 1).
                ifPresent(System.out::println);
    }
}
