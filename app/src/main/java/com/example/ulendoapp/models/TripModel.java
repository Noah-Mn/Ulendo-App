package com.example.ulendoapp.models;

import java.io.Serializable;

public class TripModel{
    String startPoint, destination, time, date, email, status;

    public TripModel() {
    }

    public TripModel(String startPoint, String destination, String time, String date, String email, String status) {
        this.startPoint = startPoint;
        this.destination = destination;
        this.time = time;
        this.date = date;
        this.email = email;
        this.status = status;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
