package com.github.walterfan.example;

import java.util.UUID;

/**
 * Created by yafan on 17/1/2018.
 */
public class HashCodeTest {

    public static void main( String[] args )
    {
        System.out.println( "-- HashCodeTest ---" );

       long meetingKey = 100_000_000L;
       for(;meetingKey < 999_999_999L; meetingKey ++) {
           if(Long.toString(meetingKey).hashCode() % 3 == 1)
               break;
       }
       System.out.println(meetingKey);
    }
}
