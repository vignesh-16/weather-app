package com.vignesh.weather.controller;

import com.vignesh.weather.model.UsersModel;
import com.vignesh.weather.repository.UsersRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    UsersRepo usersCollection;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UsersModel user) {
        UsersModel newUser = usersCollection.save(new UsersModel(user));
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
