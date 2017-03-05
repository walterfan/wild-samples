package com.github.walterfan.example.java8;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by walterfan on 4/3/2017.
 */
public class FilePathTest {

    public static void main(String[] args) {
       String filePath = "/tmp/abc.avi";
        Path inputFile = Paths.get(filePath).normalize();
        System.out.println("inputFile parent:" + inputFile.getParent());
        System.out.println("inputFile filename:" + inputFile.getFileName());
        System.out.println("inputFile path:" + inputFile.subpath(0, inputFile.getNameCount()-1).toAbsolutePath().toString());
        System.out.println("current path:" + Paths.get(".").toAbsolutePath().normalize().toString());


    }
}
