package com.vignesh.weather.controller;

import com.vignesh.weather.model.UsersModel;
import com.vignesh.weather.repository.UsersRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    UsersController userService;

    @GetMapping("/hello")
    public String loginMethod() {
        return "Hello Pookie!!!";
    }

    @PostMapping("/signin")
    public String verifyUser(@RequestBody UsersModel req) {
        return userService.verifyUser(req.getEmail());
    }

    @GetMapping("talk")
    public String knowUserName(HttpServletRequest request) {
        return "I see..";
    }

    @GetMapping("/freePass")
    public void checkTheEndPointHere(@RequestBody UsersModel req) {
        System.out.println(usersRepo.findByUsername(req.getUsername()));

    }

}
