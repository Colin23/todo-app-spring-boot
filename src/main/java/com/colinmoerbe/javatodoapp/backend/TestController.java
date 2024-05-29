package com.colinmoerbe.javatodoapp.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is currently only for testing the initial setup only.
 */
@RestController
@RequestMapping("/api")
class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Value("${test.password}")
    private String testPassword;

    @Value("${test.username}")
    private String testUsername;

    @GetMapping("/tests")
    public String getTestsEndpoint() {
        LOGGER.info("Successfully called the /tests endpoint");
        return "Testing " + testUsername + " asdf " + testPassword;
    }

    @GetMapping("/test/{id}")
    public String getTestEndpoint(@PathVariable Integer id) {
        return "Test with ID: " + id;
    }

    @PutMapping("/test/{id}")
    public String putTestEndpoint(@PathVariable Integer id) {
        return "Update method is not yet implemented";
    }

    @PostMapping("/tests")
    public String postTestsEndpoint() {
        return "Create method is not yet implemented";
    }

    @DeleteMapping("/test/{id}")
    public String deleteTestEndpoint(@PathVariable Integer id) {
        return "Delete method is not yet implemented";
    }
}
