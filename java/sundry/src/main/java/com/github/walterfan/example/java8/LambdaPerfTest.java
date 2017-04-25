package com.github.walterfan.example.java8;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by walter on 24/03/2017.
 * @see http://testng.org/doc/documentation-main.html
 */
public class LambdaPerfTest {
    private static final Logger logger = LoggerFactory.getLogger(LambdaPerfTest.class);

    private Map<Integer, Long> oldFibonacciResults;
    private Map<Integer, Long> newFibonacciResults;


    @BeforeClass
    public void init() {
        oldFibonacciResults = new ConcurrentSkipListMap<>();
        newFibonacciResults = new ConcurrentSkipListMap<>();
    }

    @AfterClass
    public void summarize() {
        int rounds = oldFibonacciResults.size();

        System.out.println("--- old vs. new ---");
        oldFibonacciResults.forEach((key, value) -> {
            System.out.println(key + ": " + value + " vs. " + newFibonacciResults.get(key));
        });

    }

    public List<Integer> fibonacci1(int size) {
        List<Integer> list = new ArrayList<>(size);
        int n0 = 1, n1 = 1, n2;
        list.add(n0);
        list.add(n1);

        for(int i=0;i < size - 2; i++) {
            n2 = n1 + n0;
            n0 = n1;
            n1 = n2;
            list.add(n2);
        }
        return list;
    }

    public List<Integer> fibonacci2(int size) {
        return Stream.iterate(new int[]{1, 1}, x -> new int[]{x[1], x[0] + x[1]})
                .limit(size).map(x -> x[0])
                .collect(Collectors.toList());
    }

    public List<Integer> newFibonacci(int size) {
        List<Integer> list = new ArrayList<>(size);
        //Stream<int[]>
        Stream.iterate(new int[]{1, 1}, x -> new int[]{x[1], x[0] + x[1]})
                .limit(size).forEach(x -> { list.add(x[0]); list.add(x[1]);});
        return list;
    }

    @DataProvider
    public Object[][] getFibonacciSize() {
        return new Object[][]{
                {10},
                {50},
                {100},
                {1000},
                {10000}
        };
    }

    @Test(dataProvider = "getFibonacciSize", description = "old fibonacci", timeOut = 1000)
    public void testOldFibonacci(int size) {
        long duration = testFibonacci("testFibonacci1", size, x->fibonacci1(x));
        oldFibonacciResults.put(size, duration);
    }

    @Test(dataProvider = "getFibonacciSize", description = "lambda fibonacci", timeOut = 1000)
    public void testNewFibonacci(int size) {
        long duration = testFibonacci("testFibonacci2", size, x->fibonacci2(x));
        newFibonacciResults.put(size, duration);
    }

    public long testFibonacci(String name, int size, Function<Integer, List<Integer> > func) {

        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Integer> list = func.apply(size);
        stopwatch.stop();
        long duration = stopwatch.elapsed(TimeUnit.MICROSECONDS);

        list.stream().forEach(x -> System.out.print(x +", "));
        System.out.println(String.format("\n--> %s (%d): %d\n" , name, size, duration));
        return duration;
    }

}