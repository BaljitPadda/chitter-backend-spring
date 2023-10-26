package com.chitterchallengespring.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

@Document("peeps_")

public class Peep {

    @Id
    @JsonProperty("userID")
    private String userID;

    @JsonSetter("time")
    private String time;

    @JsonSetter("message")
    @NotEmpty(message="Psst.. Did you forget to write your message?")
    private String message;

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
