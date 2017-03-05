package com.github.walterfan.example.java8;

import java.util.function.Function;

/**
 * Created by walterfan on 3/2/2017.
 */
public class CounterClosure {

    public Function<Integer, Integer> increase() {
        // Outside the scope of the returned function:
        int n = 0;
        return arg -> {
            System.out.print(n + " " + arg + ": ");
            arg += 1;
            // n += arg; // Produces error message
            return n + arg;
        };
    }
    public void test() {
        Function<Integer, Integer>
                x = increase(),
                y = increase();
        for(int i = 0; i < 5; i++)
            System.out.println(x.apply(i));
        for(int i = 10; i < 15; i++)
            System.out.println(y.apply(i));
    }
    public static void main(String[] args) {
        new CounterClosure().test();
    }
}
