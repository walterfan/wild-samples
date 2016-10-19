package com.github.walterfan.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Created by walter on 9/19/16.
 */
public class MDCTest {

     public static void main(String[] args) throws Exception {

        // You can put values in the MDC at any time. Before anything else
        // we put the first name
        MDC.put("first", "Walter");



        Logger logger = LoggerFactory.getLogger(MDCTest.class);
        // We now put the last name
        MDC.put("last", "White");

        // The most beautiful two words in the English language according
        // to Dorothy Parker:
        logger.info("Check enclosed.");
        logger.debug("The most beautiful two words in English.");

        MDC.put("first", "Walter");
        MDC.put("last", "Fan");
        logger.info("I am not a crook.");
        logger.info("Attributed to the former US president. 17 Nov 1973.");
    }
}
