package com.example.build;

import com.example.build.info.BuildInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Hello World");
            System.out.println(new BuildInfo().toString());
            Arrays.asList(args).forEach(arg -> {
                try {
                    Files.readAllLines(Paths.get(arg)).forEach(System.out::println);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
