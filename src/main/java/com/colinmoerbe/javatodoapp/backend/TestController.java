package com.colinmoerbe.javatodoapp.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test/{id}")
    public String specificTest(@PathVariable Integer id) {
        return "test" + id;
    }
}
