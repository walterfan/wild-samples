package com.github.walterfan.example.java8;

import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Created by walter on 09/03/2017.
 */
public class LambdaTest {

    public long oldCountPrimers(int upTo) {
        long totalCount = 0;
        for(int i=0; i< upTo; i++) {
            boolean isPrime = true;
            for(int j=2; j<i; j++) {
                if(i%j==0) {
                    isPrime = false;
                }
            }
            if(isPrime) totalCount++;
        }
        return totalCount;
    }

    public long countPrimers(int upTo) {
        return IntStream.range(1, upTo)
                .parallel()
                .filter(this::isPrime)
                .count();
    }

    public boolean isPrime(int  number)
    {
        return IntStream.range(2, number).allMatch(x -> (number %x )!= 0);
    }

    @Test
    public void testCountPrimers() {
        long cnt = countPrimers(1000);
        System.out.println("count=" + cnt);
    }

    @Test
    public void testThreadLocal() {
        ThreadLocal<DateFormat> localFormatter = ThreadLocal.withInitial(() -> new SimpleDateFormat());

        DateFormat formatter = localFormatter.get();

        AtomicInteger threadId = new AtomicInteger();
        ThreadLocal<Integer> localId = ThreadLocal.withInitial(() -> threadId.getAndIncrement());
        int idForThisThread = localId.get();
        System.out.println(idForThisThread);
    }



}
