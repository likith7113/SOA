package com.klu.divisionservices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class divisioncontroller {
@GetMapping("div")
    public String div(@RequestParam int a, @RequestParam int b) {
        if (b == 0) {
            return "Error: Division by zero is not allowed.";
        }
        int result =  a / b;
        return "Division = " + result;
    }
}
