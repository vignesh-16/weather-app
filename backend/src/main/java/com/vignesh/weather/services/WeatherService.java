package com.vignesh.weather.services;

import com.vignesh.weather.model.UserDataModel;
import com.vignesh.weather.repository.UserDataRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class WeatherService {

    private static final Logger log = LogManager.getLogger(WeatherService.class);
    @Autowired
    UserDataRepo usersData;

    public HashMap<String, Object> getDefaultWeatherStatus(String userid) {
        HashMap<String, Object> status = new HashMap<>();
        try {
            UserDataModel userData = usersData.findByUserId(userid);
            status.put(userData.getDefaultLocation(), "Processing");
            ArrayList<String> variables = userData.getKeepsTrackOf();
            for (String weatherDetails : variables) {
                status.put(weatherDetails, "Processing");
            }
            return status;
        } catch (Exception e) {
            log.error("Error while fetching basic Weather details for user: {}", e.getMessage());
            return null;
        }
    }

}
