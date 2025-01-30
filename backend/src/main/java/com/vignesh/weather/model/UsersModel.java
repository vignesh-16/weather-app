package com.vignesh.weather.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class UsersModel {

    @Id
    @Field("_id")
    private String id;
    @Field("username")
    private String username;
    @Field("email")
    private String email;
    @Field("password")
    private String password;
    @Field("createdAt")
    private long createdAt;
    @Field("modifiedAt")
    private long modifiedAt;
    @Field("isVerified")
    private boolean isVerified;
    @Field("isActive")
    private boolean isActive;
    @Field("dataId")
    private String dataId;

    public UsersModel() {
        this.id = new ObjectId().toHexString();
    }

    public UsersModel(String username, String email, String password) {
        this.id = new ObjectId().toHexString();
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(long modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
    
    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Override
    public String toString() {
        return "UsersModel [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
                + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", isVerified=" + isVerified
                + ", isActive=" + isActive + ", dataId=" + dataId + "]";
    }
    
}
