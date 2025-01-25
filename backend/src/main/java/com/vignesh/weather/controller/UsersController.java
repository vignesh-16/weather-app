package com.vignesh.weather.controller;

import com.vignesh.weather.model.UsersModel;
import com.vignesh.weather.repository.UsersRepo;
import com.vignesh.weather.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    UsersRepo usersCollection;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UsersModel user) {
        UsersModel newUser = usersCollection.save(new UsersModel(user.getUsername(), user.getEmail(), encoder.encode(user.getPassword())));
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    public String verifyUser (String userEmail, String userPassword) {
        UsersModel user = usersCollection.findByEmail(userEmail);
        System.out.println("::[UsersController]>> Inside for custom /login: "+user.toString());
        if (user != null) {
            System.out.println("::[UsersController]>> Pre authenticationManager.authenticate ");
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), userPassword));
            System.out.println("::[UsersController]>> Post authenticationManager.authenticate "+authentication.isAuthenticated());
            if(authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getEmail());
            }
        }
        return "User not found!!!";
    }

}
