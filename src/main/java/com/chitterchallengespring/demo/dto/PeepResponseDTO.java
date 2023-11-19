package com.chitterchallengespring.demo.dto;

import com.chitterchallengespring.demo.model.Peep;
import com.chitterchallengespring.demo.model.User;
import com.chitterchallengespring.demo.repositories.PeepRepository;

public class PeepResponseDTO {

    private String name;

    private String userID;

    private String time;

    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
