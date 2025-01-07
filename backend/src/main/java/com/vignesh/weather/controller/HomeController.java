package com.vignesh.weather.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @RequestMapping("/hello")
    public String loginMethod() {
        return "Hello Pookie!!!";
    }

}
