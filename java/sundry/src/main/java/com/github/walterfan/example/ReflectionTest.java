package com.github.walterfan.example;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.UUID;

/**
 * Created by yafan on 18/6/2017.
 */

class UserService {
    private String userRepository;
}
public class ReflectionTest {

    public static void main( String[] args ) throws Exception
    {
        System.out.println( "-- ReflectionTest --" );
        UserService userService = new UserService();

        Field field = userService.getClass().getDeclaredField("userRepository");
        field.setAccessible(true);
        field.set(userService, "testService");

        System.out.println(field.get(userService));

    }
}
