package com.github.walterfan.example.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.util.concurrent.Uninterruptibles;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by yafan on 11/1/2018.
 */
@Slf4j
public class GuavaCacheExam implements RemovalListener<String,  String> {

    private LoadingCache<String,  String> internalCache;

    private CacheLoader<String, String> cacheLoader;

    private Map<String, String> configMap = new HashMap<>();

    public GuavaCacheExam() {

        this.cacheLoader = new CacheLoader<String, String>() {
            @Override
            public String load(String s) throws Exception {
                return configMap.get(s);
            }
        };

        this.internalCache = CacheBuilder.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .removalListener(this)
                .build(this.cacheLoader);
    }


    public void runTest() throws ExecutionException {
        configMap.put("name", "walter");
        configMap.put("age", "40");

        internalCache.put("name", "walter");
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        String name = internalCache.get("name");
        log.info("name: {}" , name);
    }


    @Override
    public void onRemoval(RemovalNotification<String, String> removalNotification) {
        log.info("onRemoval: {}" , removalNotification);
    }

    public static void main(String[] args) throws ExecutionException {
        log.info("--- GuavaCacheExample ---");
        GuavaCacheExam guavaCacheExam = new GuavaCacheExam();
        guavaCacheExam.runTest();
        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);

        log.info("--- Bye ---");
    }
}
