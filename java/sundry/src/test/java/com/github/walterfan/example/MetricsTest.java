package com.github.walterfan.example;

import com.codahale.metrics.MetricRegistry;

/**
 * Created by walterfan on 24/10/2016.
 */
public class MetricsTest {

    private MetricRegistry metricRegistry;


    public static void main(String[] args) {

        String meetingId = "123456789";
        String siteName = "go.webex.com";
        String theQuerySipUri = String.format("sip:%s@%s", meetingId, siteName);
        System.out.println(theQuerySipUri);
    }
}
