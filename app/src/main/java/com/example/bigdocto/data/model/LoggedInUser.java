package com.example.bigdocto.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("token")
    private String token;

    public LoggedInUser(  @JsonProperty("userName")String userName, @JsonProperty("token") String token) {
        this.userName = userName;
        this.token = token;
    }

    public String getUserNAme() {
        return userName;
    }

    public String getPassword() {
        return token;
    }
}