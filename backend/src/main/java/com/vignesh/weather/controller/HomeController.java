package com.vignesh.weather.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/hello")
    public String loginMethod() {
        return "Hello Pookie!!!";
    }

    @GetMapping("/talk")
    public String knowUserName(HttpServletRequest request) {
        return "I see..";
    }

}
