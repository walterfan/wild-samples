package com.github.walterfan.example.config;

import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by walter on 07/01/2017.
 */
public class EnvTest {

    public static void main( String[] args ) throws IOException {
        Properties prop = System.getProperties();
        String logLevel = prop.getProperty("loglevel");
        System.out.println("logLevel: " + logLevel);

        Map<String, String> env0 = System.getenv();
        System.out.println("logLevel: " + env0.get("loglevel"));

        Environment env1 = new StandardEnvironment();
        System.out.println("logLevel: " + env1.getProperty("loglevel"));
    }
}
