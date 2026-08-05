package com.klu1.multipicationservices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultiplicationController {
    @GetMapping("mul")
    public String mul(@RequestParam int a, @RequestParam int b) {
        int result = a * b;
        return "Multiplication = " + result;
    }
}
