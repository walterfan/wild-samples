package com.github.walterfan.example;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by walter on 13/01/2017.
 */

public class Java7Test {
    private static final Logger logger = LoggerFactory.getLogger(Java7Test.class);

    public String test(String fileInClassPath) throws IOException {
        try(InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileInClassPath)) {
            return IOUtils.toString(in);
        }
    }

    public static void main( String[] args )
    {
        System.out.println( "--- Java7Test ---" );



    }
}
