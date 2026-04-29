package com.midihub.melodyhub.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/")
    public String home() {
        return "MelodyHub is running!";
    }

    @GetMapping("/test")
    public String test() {
        return "API works";
    }
}