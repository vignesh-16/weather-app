package com.vignesh.weather.controller;

import com.vignesh.weather.model.UsersModel;
import com.vignesh.weather.repository.UsersRepo;
import com.vignesh.weather.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    UsersRepo usersCollection;

    @Autowired
    JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UsersModel user) {
        UsersModel newUser = usersCollection.save(new UsersModel(user.getUsername(), user.getEmail(), user.getPassword()));
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    public String verifyUser (String userEmail) {
        UsersModel user = usersCollection.findByEmail(userEmail);
        if(user !=  null) {
            return jwtService.generateToken(user.getUsername());
        }
        return "User not found!!!";
    }

}
