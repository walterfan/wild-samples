package com.github.walterfan.example.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by walterfan on 13/2/2017.
 */
public class OptionalValue {


    class User {
        String name;
        String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }



        public String getEmail() {
            return email;
        }

    }

    @org.testng.annotations.Test
    public void test() {
        Integer a = null;
        Optional<Integer> aa = Optional.ofNullable(a);
        System.out.println(aa);
        aa.ifPresent(System.out::print);
        int cc =  aa.orElse(0);
        System.out.println(cc);


        List<User> users = new ArrayList<>();
        users.add(new User("a", "b"));
        users.stream().map(u -> u.getName()).forEach(System.out::println);


        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        assertTrue(3 == numbers.get(0));

        List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());

    }

    public static void main(String[] args) {
        new OptionalValue().test();
    }
}
