package com.github.walterfan.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by walter on 22/01/2017.
 */
@Aspect
@Component
public class TimingAspect {
    private final static Logger log = LoggerFactory.getLogger(TimingAspect.class);


    @Around("@annotation( timingAnnotation ) ")
    public Object measureTimeRequest(final ProceedingJoinPoint pjp, Timing timingAnnotation) throws Throwable {
        final long start = System.currentTimeMillis();
        final Object retVal = pjp.proceed();
        final long duration = System.currentTimeMillis() - start;
        try {
            final String measurement = timingAnnotation.value();
            final long timeout = timingAnnotation.timeout();
            final boolean isTimeout = timeout != 0 && duration >= timeout;
            if (isTimeout) {
                log.warn("timing for {} expected: {}ms actual: {}ms", measurement, timeout, duration);
            }
            //write influxDB

        } catch (Exception ex) {
            log.warn("Cannot emit the timed metrics {}...", timingAnnotation.value(), ex);
        }
        return retVal;
    }

}
