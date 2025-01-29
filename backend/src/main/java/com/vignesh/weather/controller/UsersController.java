package com.vignesh.weather.controller;

import com.vignesh.weather.model.UsersModel;
import com.vignesh.weather.repository.UsersRepo;
import com.vignesh.weather.services.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

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

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UsersModel user) {
        UsersModel newUser = usersCollection.save(new UsersModel(user.getUsername(), user.getEmail(), encoder.encode(user.getPassword())));
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/getUserDetails")
    public ResponseEntity<?> getUserDetails(@RequestParam String userId) {
        log.info("Request to fetch a user details with id: {}", userId);
        UsersModel user = null;
        try {
            user = usersCollection.findByEmail(userId);
            user = user == null ? usersCollection.findByUsername(userId) : user;
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error while trying to reset user password for: {} {} - ", userId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUsers() {
        try {
            ArrayList<UsersModel> users = (ArrayList<UsersModel>) usersCollection.findAll();
            return new ResponseEntity<>(users,HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while fetching users from the database: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String verifyUser (String userEmail, String userPassword) {
        try {
            UsersModel user = usersCollection.findByEmail(userEmail);
            if (user != null) {
                System.out.println("::[UsersController]>> Inside for custom /login: "+user.toString());
                System.out.println("::[UsersController]>> Pre authenticationManager.authenticate ");
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), userPassword));
                System.out.println("::[UsersController]>> Post authenticationManager.authenticate "+authentication.isAuthenticated());
                if(authentication.isAuthenticated()) {
                    return jwtService.generateToken(user.getUsername());
                }
            } else {
                return "User not found!!!";
            }
        } catch (Exception e) {
            log.error("Exception occurred while verifying user credentials: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    public HashMap<String, Object> resetUserPassword(String userId, String newPassword) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            UsersModel user = usersCollection.findByEmail(userId);
            user = user == null ? usersCollection.findByUsername(userId) : user;
            if (user == null) {
                result.put("errorType", "Could not find any user with given userId: "+userId);
                result.put("isSuccessfullyChanged", false);
                return result;
            } else {
               user.setPassword(encoder.encode(newPassword));
               user.setModifiedAt(System.currentTimeMillis());
               UsersModel updatedUser = usersCollection.save(user);
               result.put("isSuccessfullyChanged", true);
               result.put("updatedUserDetails", updatedUser);
            }
        } catch (Exception e) {
            log.error("Error while updating user password for {} :", userId);
            result.put("errorType", "Exception while updating user password");
            result.put("isSuccessfullyChanged", false);
            return result;
        }
        return result;
    }

}
