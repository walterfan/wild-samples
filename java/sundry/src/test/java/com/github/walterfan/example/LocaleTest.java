package com.github.walterfan.example;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Set;


/**
 * Created by walter on 23/11/2016.
 */
public class LocaleTest {


    private static Set<String> isoCountries = ImmutableSet.copyOf(Locale.getISOCountries());
    private static Set<String> isoLanguages = ImmutableSet.copyOf(Locale.getISOLanguages());


        public static Locale toLocale1(String localeStr) {
            if(Strings.isNullOrEmpty(localeStr)) {
                return null;
            }

            localeStr =  StringUtils.trimLeadingCharacter(localeStr, '_');

            if(isoLanguages.contains(localeStr))
                return new Locale(localeStr);
            //else if(isoCountries.contains(localeStr))
            //    return new Locale("", localeStr);
            else if(localeStr.length() == 2)
                return new Locale("", localeStr);
            else
                return LocaleUtils.toLocale(localeStr);
        }


        public static Locale toLocale2(String localeStr) {

        Locale locale = null;
        if (!Strings.isNullOrEmpty(localeStr)) {
            //COUNTRY with _ prepended and two letter country code
            if (localeStr.startsWith("_") && localeStr.length() == 3) {
                locale = new Locale("", localeStr.split("_")[1]);
            } else if (localeStr.contains("_")) {
                //language_COUNTRY format
                locale = LocaleUtils.toLocale(localeStr);
            } else if (localeStr.length() == 2) {
                //COUNTRY only
                locale = new Locale("", localeStr);
            } else {
                throw new IllegalArgumentException("Invalid locale ");
            }
        }

        return locale;
    }

    /*
    there is a contructor of locale:

        public Locale(String language) {
            this(language, "", "");
        }
     */

    private static void testAndPrint(String aStr) {
        System.out.println("\n#---" + aStr + "---");
        Locale locale1, locale2;
        try {
            locale1 = toLocale1(aStr);
            System.out.println("* toLocale1: " + locale1.toString() + ", lang=" + locale1.getLanguage() + ", country=" + locale1.getCountry());
        } catch(Exception e) {
            System.out.println("* toLocale1 exception: " + e.getMessage());
        }

        try {
            locale2 = toLocale2(aStr);
            System.out.println("* toLocale2: " + locale2.toString() + ", lang=" + locale2.getLanguage() + ", country=" + locale2.getCountry());
        } catch(Exception e) {
            System.out.println("* toLocale2 exception: " + e.getMessage());
        }


    }

    public static void main(String[] args) {

        Locale locale0 = new Locale("US");
        System.out.println("* new Locale(\"US\"): " + locale0.toString() + ", lang=" + locale0.getLanguage() + ", country=" + locale0.getCountry());

        locale0 = new Locale("en");
        System.out.println("* new Locale(\"en\"): " + locale0.toString() + ", lang=" + locale0.getLanguage() + ", country=" + locale0.getCountry());


        testAndPrint("en");
        testAndPrint("US");
        testAndPrint("_US");
        testAndPrint("en_US");

        testAndPrint("_AB");
        testAndPrint("AB");

        testAndPrint("_ABC");
        testAndPrint("ABC");


    }
 }
