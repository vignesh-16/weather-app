package com.vignesh.weather.DTO;

import java.util.ArrayList;

public class UserDataDTO {
    private String userId;
    private String defaultLocation;
    private ArrayList<String> keepsTrackOf;

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getDefaultLocation() { return defaultLocation; }
    public void setDefaultLocation(String defaultLocation) { this.defaultLocation = defaultLocation; }

    public ArrayList<String> getKeepsTrackOf() { return keepsTrackOf; }
    public void setKeepsTrackOf(ArrayList<String> keepsTrackOf) { this.keepsTrackOf = keepsTrackOf; }
}
