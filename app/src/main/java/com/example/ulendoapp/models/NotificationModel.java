package com.example.ulendoapp.models;

import java.io.Serializable;

public class NotificationModel{
    public String startPoint, stopPoint;
    int type;

    public NotificationModel(String startPoint, String stopPoint, int type) {
        this.startPoint = startPoint;
        this.stopPoint = stopPoint;
        this.type = type;
    }

    public NotificationModel() {

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
