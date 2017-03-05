package com.github.walterfan.example;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{

    private static List<String> countryCodes = Arrays.asList(Locale.getISOCountries());

    private static Set<String> countryCodeSet = ImmutableSet.copyOf(Locale.getISOCountries());

    public static long test1() {
        Stopwatch watch =  Stopwatch.createStarted();
        boolean ret = countryCodes.contains("aaa");
        watch.stop();
        return watch.elapsed(TimeUnit.MICROSECONDS);
    }

    public static long test2() {
        Stopwatch watch =  Stopwatch.createStarted();
        boolean ret = countryCodeSet.contains("aaa");
        watch.stop();
        return watch.elapsed(TimeUnit.MICROSECONDS);
    }

    public static void main( String[] args )
    {
        System.out.println( "search from list:" );

        System.out.println(UUID.randomUUID().toString());

        //new Date()
    }
}
