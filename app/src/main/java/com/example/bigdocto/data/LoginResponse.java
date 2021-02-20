package com.example.bigdocto.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("message")
    private String message;
    @JsonProperty("token")
    private String token;

//    public LoginResponse(String userName, String message, String token) {
//        this.userName = userName;
//        this.message = message;
//        this.token = token;
//    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
