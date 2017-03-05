package com.github.walterfan.example.config;

/**
 * Created by walter on 09/12/2016.
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ConfigTest {

    public static void main( String[] args ) throws IOException {
        System.out.println("search from list:");

        ObjectMapper mapper = new ObjectMapper();
        PstnNumber number1 = new PstnNumber("+14155275048",CountryCode.US,false);
        PstnNumber number2 = new PstnNumber("+18445777275",CountryCode.US,true);

        List<PstnNumber> theList = new ArrayList<PstnNumber>();
        theList.add(number1);
        theList.add(number2);
        theList.forEach(System.out::println);

        //writer.writeValueAsString(theList);

        String json = "[{\"+14155275048\",\"United States\",false},{\"+18445777275\",\"United States\",true}]";

        PstnNumber[] arrObjects = mapper.readValue(json, PstnNumber[].class);
        //As List:

        List<PstnNumber> aList = mapper.readValue(json, new TypeReference<List<PstnNumber>>(){});
        //Another way to specify the List type:

        aList.forEach(System.out::println);
        aList = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, PstnNumber.class));
        aList.forEach(System.out::println);

    }
}
