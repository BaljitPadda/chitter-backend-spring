package com.chitterchallengespring.demo.dto;
import java.util.Objects;

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


    // Need to override equals method to compare objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeepResponseDTO that = (PeepResponseDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(userID, that.userID) && Objects.equals(time, that.time) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userID, time, message);
    }


}
