package com.github.walterfan.example.java8;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by walterfan on 13/2/2017.
 */
@Slf4j
public class StreamTest {

    @Data
    class User {
        final String firstName;
        final String lastName;
        final String email;

        public User(String firstNamename, String lastName, String email) {
            this.firstName = firstNamename;
            this.lastName = lastName;
            this.email = email;
        }



    }

    @Test
    public void testStream() {

        Function<User, String> fullNameFunction = u -> { String fullName = u.getFirstName() + " " + u.getLastName(); return fullName; };
        List<User> users = new ArrayList<>();
        users.add(new User("walter", "fan", "walter.fan@world.com"));
        users.add(new User("walt", "zhou", "walter.zhou@world.com"));
        List<String> names = users.stream().map(fullNameFunction).collect(Collectors.toList());
        log.info("--- name list ---");
        names.forEach(log::info);
        assertEquals("walter fan" , names.get(0));


        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
        log.info("--- numbers ---");
        squaresList.forEach(x -> System.out.println(x));

        log.info("--- sort and limited numbers ---");

        squaresList.stream().sorted().limit(3).forEach(System.out::println);

    }
}
