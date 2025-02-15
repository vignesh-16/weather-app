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

    public HashMap<String, HashMap<String, String>> getDefaultWeatherStatus(String userid) {
        HashMap<String, HashMap<String, String>> status = new HashMap<>();
        HashMap<String, String> data = new HashMap<>();
        try {
            UserDataModel userData = usersData.findByUserId(userid);
            data.put(userData.getDefaultLocation(), "Processing");
            status.put("defaultLocation", data);
            status.put("keepsTrackOf", getWeatherConditions(userData.getDefaultLocation() ,userData.getKeepsTrackOf()));
            return status;
        } catch (Exception e) {
            log.error("Error while fetching basic Weather details for user: {}", e.getMessage());
            return null;
        }
    }

    public HashMap<String, String> getWeatherConditions(String location, ArrayList<String> subsets) {
        try {
            HashMap<String, String> data = new HashMap<>();
            for (String subset : subsets) {
                data.put(subset, "Processing");
            }
            return data;
        } catch (Exception e) {
            log.error("Exception occurred while fetching Weather subsets for location: {}", e.getMessage());
            return null;
        }

    }

}
