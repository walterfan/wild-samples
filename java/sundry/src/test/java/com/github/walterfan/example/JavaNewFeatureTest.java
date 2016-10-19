package com.github.walterfan.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.testng.Assert.fail;

/**
 * Created by walter on 9/14/16.
 */

class Application {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Application(String name) {
        this.name = name;
    }
}
class Project {
    private Application app;

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }
}


@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}

public class JavaNewFeatureTest {
    private static final Logger log = LoggerFactory.getLogger(JavaNewFeatureTest.class);
    @BeforeClass
    public void setUp() {
        // code that will be invoked when this test is instantiated
    }

    @Test(groups = { "java8" })
    public void testException() {
        System.out.println("--- testException ---");
        Exception e = null;
        if (e instanceof SocketTimeoutException) {
            System.out.println("yes");
            fail("test failed");
        } else {
            System.out.println("no");
        }
    }

    @Test(groups = { "java8" })
    public void testOptional() {
        System.out.println("--- testOptional ---");
        int i = 0;
        try
        {
            Optional emptyOptional = Optional.empty();
            System.out.println( emptyOptional.get() );
        }
        catch( NoSuchElementException ex )
        {
            log.error( "{} expected NoSuchElementException",i, ex ); //this is executed
        }

        Project project = new Project();
        Optional<Project> optionalProject = Optional.ofNullable(project);

        if (optionalProject.isPresent()) {
            Application applicationType = optionalProject.get().getApp();
            Optional<Application> optionalAppName = Optional.ofNullable(applicationType);
            if (optionalAppName.isPresent()) {
                String typeDirName = optionalAppName.get().getName();
                Optional<String> optionalTypeDirName = Optional.ofNullable(typeDirName);
                if (optionalTypeDirName.isPresent()) {
                    System.out.println(optionalTypeDirName);
                }
            }
        }

        Optional<String> optional = Optional.of("bam");
        optional.isPresent();           // true
        optional.get();                 // "bam"
        optional.orElse("fallback");    // "bam"
        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"


    }


    @Test
    public void testStream() {
        List<String> myList =
                Arrays.asList("a1", "a2", "b1", "c2", "c1");

        myList.stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);

        Stream.of("a1", "a2", "a3")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);  // 3
    }

    @Test
    public void testLambda() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
      /*  Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });*/

        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });

        names.stream().forEach(System.out::println);
    }

    @Test
    public void testFunctionlInterface() {

        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123
    }

    @Test
    public void testConsumer() {
        Consumer<Application> greeter = (p) -> System.out.println("Hello, " + p.getName());
        greeter.accept(new Application("Luke"));
    }

}

/*
* {"devices": ["aa", "bb"]}
*
* */