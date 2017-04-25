package com.github.walterfan.example.concurrent;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.Uninterruptibles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by walter on 15/04/2017.
 */
public class FutureTest {
    public static final String AUTHOR_NAME = "Joseph Bowbeer";
    private AtomicInteger counter = new AtomicInteger(0);
    private Logger logger = LoggerFactory.getLogger(FutureTest.class);


    private ExecutorService pool;

    private String[] authors  = new String[] {
            "Brian Goetz",
            "Tim Peierls",
            "Joshua Bloch",
            "Joseph Bowbeer",
            "David Holmes",
            "Doug Lea" };

    private Instant earliestDate = Instant.parse("2005-12-31T00:00:00.00Z");

    public static class Book {
        final String title;
        final String author;
        final String isbn;
        final Instant publicationDate;

        public Book(String title, String author, String isbn, Instant publicationDate) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.publicationDate = publicationDate;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", isbn='" + isbn + '\'' +
                    ", publicationDate=" + publicationDate +
                    '}';
        }
    }

    public Book queryBook(String title, String author, Instant earliestDate) {
        int num = counter.incrementAndGet();
        int ms = 80;

        logger.info("{}. query book, times  {} " , num, author);

        if("Joshua Bloch".equals(author)) {
            ms = 120;
        }
        Uninterruptibles.sleepUninterruptibly(ms, TimeUnit.MILLISECONDS);

        if(Arrays.asList(new String[]{"Brian Goetz","Tim Peierls","Joshua Bloch"}).contains(author)) {
            return null;
        }

        return new Book("Java Concurrency in Practice", author, String.valueOf(num), Instant.parse("2006-05-19T00:00:00.00Z"));
    }



    @BeforeClass
    public void init() {
        int corePoolSize = 4;
        int maxPoolSize = 16;
        long keepAliveTime = 5000;
        int queueCapacity = 200;

        pool = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(queueCapacity)
        );
    }

    @AfterClass
    public void clean() {
        if(null == pool) {
            return;
        }

        try {
            logger.info("attempt to shutdown executor");
            pool.shutdown();
            pool.awaitTermination(2, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            logger.error("tasks interrupted");
        }
        finally {
            if (!pool.isTerminated()) {
                logger.error("cancel non-finished tasks");
            }
            pool.shutdownNow();
            logger.info("shutdown finished");
        }
    }


    public Optional<Book> querySequentially() {

        for (String author : authors) {
            Optional<Book> book = Optional.ofNullable(queryBook("java", author, earliestDate));
            if (book.isPresent()) {
                return book;
            }
        }
        return Optional.empty();

    }

    public Optional<Book> queryConcurrently ()  {
        List<Future<Book>> futureBooks = new ArrayList<>(authors.length);
        for (String author : authors) {
            futureBooks.add(pool.submit(() -> queryBook("java", author, earliestDate)));
        }

        Optional<Book> ret = null;
        for (Future<Book> futureBook : futureBooks) {
            try {
                ret = Optional.ofNullable(futureBook.get(100, TimeUnit.MILLISECONDS));
            } catch (TimeoutException |InterruptedException|ExecutionException e) {
                continue;
            }
            if (ret.isPresent()) {
                break;
            }
        }
        return ret;
    }

    public Optional<Book> queryParallelly ()  {
        List<Optional<Book>> books = Arrays.asList(authors).parallelStream()
                .map(x -> queryBook("java", x, earliestDate))
                .map(x -> Optional.ofNullable(x))
                .collect(Collectors.toList());

        //books.stream().filter(x -> x.isPresent()).forEach(x -> logger.info(x.get().toString()));
        for(String author: authors) {

            Optional<Book> opt = books.stream().filter(x -> x.isPresent()).map(x -> x.get())
                    .filter(x -> author.equals(x.author)).findFirst();

            if(opt.isPresent())
                return opt;

        }

        return Optional.empty();
    }

    @Test
    public void testQuery() {


        logger.info("-- querySequentially ---");
        long durationSequentially  = recordExecutionTime(() -> querySequentially());

        logger.info("-- queryConcurrently ---");
        long durationConcurrently  = recordExecutionTime( () -> queryConcurrently());

        logger.info("-- queryParallelly ---");
        long durationParallelly  = recordExecutionTime(() -> queryParallelly());

        logger.info("duration: querySequentially={} > queryParallelly={} > queryConcurrently={},", durationSequentially, durationParallelly, durationConcurrently );
        Assert.assertTrue(durationSequentially > durationConcurrently
                && durationSequentially > durationConcurrently
                && durationParallelly > durationConcurrently);
    }


    public long recordExecutionTime(Supplier<Optional<Book>> supplier)  {
        counter.set(0);
        final Stopwatch stopwatch = Stopwatch.createStarted();

        Optional<Book> book = supplier.get();


        stopwatch.stop();

        long duration = stopwatch.elapsed(TimeUnit.MILLISECONDS);

        Assert.assertEquals(book.map(x -> x.author).orElse(null), AUTHOR_NAME);
        return duration;

    }


}
