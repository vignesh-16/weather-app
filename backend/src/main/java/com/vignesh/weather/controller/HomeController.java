package com.vignesh.weather.controller;

import com.vignesh.weather.model.UsersModel;
import com.vignesh.weather.repository.UsersRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/")
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    UsersController userService;

    @PostMapping("hello")
    public String helloAccess() {
        return "Reached the end-point hello!";
    }

    @PostMapping("api/login")
    public String verifyUser(@RequestBody UsersModel req) {
        System.out.println("::[HomeController]>> Hey here it is: "+req);
        return userService.verifyUser(req.getEmail(), req.getPassword());
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUsers() {
        try {
            ArrayList<UsersModel> users = (ArrayList<UsersModel>) usersRepo.findAll();
            return new ResponseEntity<>(users,HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while fetching users from the database: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
