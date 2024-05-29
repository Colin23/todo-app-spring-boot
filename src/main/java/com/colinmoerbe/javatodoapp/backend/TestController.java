package com.colinmoerbe.javatodoapp.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is currently only for testing the initial setup only.
 */
@RestController
@RequestMapping("/api")
class TestController {
    @Value("${test.password}")
    private String testPassword;

    @Value("${test.username}")
    private String testUsername;

    @GetMapping("/test")
    public String test() {
        return "Testing " + testUsername + " " + testPassword;
    }

    @GetMapping("/test/{id}")
    public String specificTest(@PathVariable Integer id) {
        return "test" + id;
    }
}
