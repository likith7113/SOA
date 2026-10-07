package com.klu.corsdemo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class DemoController {

    @GetMapping("/message")
    public String message() {
        return "Welcome to Likith's Microservice";
    }
}
