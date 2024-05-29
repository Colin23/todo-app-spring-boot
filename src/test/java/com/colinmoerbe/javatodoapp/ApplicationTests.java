package com.colinmoerbe.javatodoapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This testing class is currently only for testing the initial setup only.
 */
@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void exampleTest() {
        final int a = 2;
        final int b = 3;
        final int c = a + b;
        Assertions.assertEquals(5, c);
    }
}
