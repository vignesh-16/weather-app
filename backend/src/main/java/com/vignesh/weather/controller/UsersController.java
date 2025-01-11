package com.vignesh.weather.controller;

import com.vignesh.weather.model.UsersModel;
import com.vignesh.weather.repository.UsersRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    UsersRepo usersCollection;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(HttpServletRequest request,
                                        @RequestParam(value = "username") String username,
                                        @RequestParam(value = "email") String email,
                                        @RequestParam(value ="password") String password
    ) {
        UsersModel user = usersCollection.save(new UsersModel(username, email, password));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
