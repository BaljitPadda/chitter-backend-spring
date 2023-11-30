package com.chitterchallengespring.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("peeps_")
public class Peep {

    // I need to access a peep's unique id in order to be able to edit it.
    // This annotation grants me access to the id mongodb creates.
    @Id
    private String id;

    @JsonProperty("userID")
    @NotEmpty(message="A peep must have a userID.")
    private String userID;

    @JsonProperty("time")
    @NotEmpty(message="A peep must have a time created.")
    private String time;

    @JsonProperty("message")
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
