package com.atlas.techtask;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

public class TestReporterUtils {

    public static Set<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation, String packageName) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackage(packageName)
                .setScanners(Scanners.MethodsAnnotated));
        return reflections.getMethodsAnnotatedWith(annotation);
    }

    public static void printMethodsToFile(Set<Method> methodsAnnotatedWithTest, String fileName) throws IOException {
        String methods = methodsAnnotatedWithTest
                .stream()
                .map(Method::getName)
                .collect(Collectors.joining(","));
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.print(methods);
        }
    }
}
