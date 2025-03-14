package com.vignesh.weather.controller;

import com.vignesh.weather.DTO.UserDataDTO;
import com.vignesh.weather.model.UserDataModel;
import com.vignesh.weather.model.UsersModel;
import com.vignesh.weather.repository.UserDataRepo;
import com.vignesh.weather.repository.UsersRepo;
import com.vignesh.weather.services.JwtService;
import com.vignesh.weather.services.WeatherService;
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
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    UsersRepo usersCollection;

    @Autowired
    UserDataRepo userDataCollection;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    WeatherService weatherApp;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);


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
                return new ResponseEntity<>(new HashMap<String, Object>(){{ put("ERROR", true); put("MESSAGE", "USER_NOT_FOUND"); }},HttpStatus.OK);
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

    @PostMapping("/updateUserData")
    public ResponseEntity<?> updateUserData(@RequestBody UserDataDTO userData) {
        try {
            Optional<UsersModel> user = usersCollection.findById(userData.getUserId());
            log.info("Request received to update userdata of :{}", user);
            if (user.isEmpty()) {
                return new ResponseEntity<>("Could not find any user with provided userId!", HttpStatus.NOT_FOUND);
            } else {
                boolean isDataUpdated = updateUserData(userData.getUserId(), userData.getDefaultLocation(), userData.getKeepsTrackOf());
                if (isDataUpdated) {
                    return new ResponseEntity<>("User data accepted and updated!", HttpStatus.ACCEPTED);
                } else {
                    return new ResponseEntity<>("Could not update user data!", HttpStatus.NOT_MODIFIED);
                }
            }
        } catch (Exception e) {
            log.error("Error while trying to update user data: {}", e.getMessage());
            return new ResponseEntity<>("Error while processing request!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> verifyUser (String userEmail, String userPassword) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            UsersModel user = usersCollection.findByEmail(userEmail);
            if (user != null) {
                System.out.println("::[UsersController]>> Inside for custom /login: "+user.toString());
                System.out.println("::[UsersController]>> Pre authenticationManager.authenticate ");
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), userPassword));
                System.out.println("::[UsersController]>> Post authenticationManager.authenticate "+authentication.isAuthenticated());
                if(authentication.isAuthenticated()) {
                    String userToken = jwtService.generateToken(user.getUsername());
                    HashMap<String, HashMap<String, String>> userData = weatherApp.getDefaultWeatherStatus(user.getId());
                    result.put("STATUS", "SUCCESS");
                    result.put("USER", user.getUsername());
                    result.put("USER_TOKEN", userToken);
                    result.put("USER_DATA", userData);
                    return new ResponseEntity<>(result,HttpStatus.OK);
                }
            } else {
                result.put("STATUS", "FAILED");
                result.put("MESSAGE", "User not found!");
                return new ResponseEntity<>(result,HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("Exception occurred while verifying user credentials: {}", e.getMessage());
            result.put("STATUS","FAILED");
            if (e.getMessage().equalsIgnoreCase("Bad credentials")) {
                result.put("MESSAGE", "Invalid email or password");
            } else {
                result.put("MESSAGE", "Error processing request");
            }
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
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

    public boolean updateUserData (String userId, String defaultLocation, ArrayList<String> keepTrack) {
        boolean isUserDataUpdated = false;
        try {
            UserDataModel existingUserData = userDataCollection.findByUserId(userId);
            if (existingUserData != null && (defaultLocation != null || keepTrack !=  null)) {
                if (defaultLocation != null) {
                    existingUserData.setDefaultLocation(defaultLocation);
                }
                if (keepTrack != null) {
                    existingUserData.setKeepsTrackOf(keepTrack);
                }
                userDataCollection.save(existingUserData);
                isUserDataUpdated = true;
            } else {
                UserDataModel freshUserData = new UserDataModel(userId, defaultLocation, keepTrack);
                userDataCollection.save(freshUserData);
                isUserDataUpdated = true;
            }
        } catch (Exception e) {
            log.info("Exception occurred while trying to update user data of user: {} {}", userId, e.getMessage());
            return false;
        }
        return isUserDataUpdated;
    }

    public UserDataModel fetchUserData (String userId) {
        try {
            Optional<UserDataModel> userData = Optional.ofNullable(userDataCollection.findByUserId(userId));
            return userData.orElse(null);
        } catch (Exception e) {
            log.error("Error while fetching userData for user: {}, throws {}", userId, e.getMessage());
            return null;
        }
    }

}
