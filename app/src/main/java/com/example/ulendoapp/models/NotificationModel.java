package com.example.ulendoapp.models;

import java.io.Serializable;

public class NotificationModel{
    public String startPoint, stopPoint, status;

    public NotificationModel(String startPoint, String stopPoint, String status) {
        this.startPoint = startPoint;
        this.stopPoint = stopPoint;
        this.status = status;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getStopPoint() {
        return stopPoint;
    }

    public void setStopPoint(String stopPoint) {
        this.stopPoint = stopPoint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
