package com.github.walterfan.example;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by walterfan on 28/10/2016.
 */
public class LambdaTest {

    private static final List<String> names = Arrays.asList("a", "b");

    public static void  main(String[] args) {
        Runnable noArg = () -> System.out.println("running ");
        ActionListener oneArg = event -> System.out.println("button clicked");
        //names.("c");

    }
}
