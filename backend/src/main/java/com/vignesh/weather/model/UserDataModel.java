package com.vignesh.weather.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Document(collection = "usersData")
public class UserDataModel {
    @Id
    @Field("_id")
    private String id;
    @Field("userId")
    private String userId;
    @Field("defaultLocation")
    private String defaultLocation;
    @Field("keepsTrackOf")
    private ArrayList<String> keepsTrackOf;

    public UserDataModel() {this.id = new ObjectId().toHexString();}

    public UserDataModel(String userId, String defaultLocation, ArrayList<String> keepsTrackOf) {
        this.id = new ObjectId().toHexString();
        this.userId = userId;
        this.defaultLocation = defaultLocation;
        this.keepsTrackOf = keepsTrackOf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public ArrayList<String> getKeepsTrackOf() {
        return keepsTrackOf;
    }

    public void setKeepsTrackOf(ArrayList<String> keepsTrackOf) {
        this.keepsTrackOf = keepsTrackOf;
    }

    @Override
    public String toString() {
        return "UsersDataModel{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", defaultLocation='" + defaultLocation + '\'' +
                ", keepsTrackOf=" + keepsTrackOf +
                '}';
    }
}
