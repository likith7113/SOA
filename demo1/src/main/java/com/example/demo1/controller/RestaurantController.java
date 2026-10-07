package com.example.demo1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.entity.Restaurant;

@RestController
public class RestaurantController {

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants() {

        Restaurant r1 = new Restaurant(1, "KFC", "Hyderabad");
        Restaurant r2 = new Restaurant(2, "Dominos", "Vijayawada");

        List<Restaurant> list = new ArrayList<>();

        list.add(r1);
        list.add(r2);

        return list;
    }
}