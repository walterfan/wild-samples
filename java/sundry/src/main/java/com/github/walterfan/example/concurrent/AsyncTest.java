package com.github.walterfan.example.concurrent;

import com.google.common.util.concurrent.Uninterruptibles;

import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by walter on 22/04/2017.
 */
public class AsyncTest {
    public void testUrl() throws Exception {
        URL url = new URL("http://www.jianshu.com/u/e0b365801f48");

        try(InputStream in  = url.openStream()) {
            Files.copy(in, Paths.get("test.txt"));
        }
    }

    public void testFuture() throws Exception {

        try(AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("test.txt"))) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024*1024*100);
            Future<Integer> result = channel.read(buffer, 0);

            while(!result.isDone()) {
                System.out.println("do other things");
                Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            }
            System.out.print("bytes read: " + result.get());
        }
    }

    public void testCompletionHandler() throws Exception {
        byte[] data = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};

        ByteBuffer buffer = ByteBuffer.wrap(data);

        CompletionHandler<Integer, Object> handler = new CompletionHandler<Integer, Object>() {

            @Override
            public void completed(Integer writtern, Object attachment) {
                System.out.println("Bytes writtern" + writtern);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("Bytes write failed");
            }
        };

        try(AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("test.txt"),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            channel.write(buffer, 0, null, handler);
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        }


    }

}
