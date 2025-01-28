package com.vignesh.weather.model;

public class PasswordResetModel {
    private String userId;
    private String newPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "PasswordResetModel{" +
                "userId='" + userId + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
