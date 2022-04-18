package com.example.ulendoapp.models;

import java.io.Serializable;

public class NotificationModel implements Serializable{
    public String startPoint, stopPoint;
    public enum ItemType {
        ONE_ITEM, TWO_ITEM, THREE_ITEM;
    }
    private ItemType type;

    public NotificationModel(String startPoint, String stopPoint, ItemType type) {
        this.startPoint = startPoint;
        this.stopPoint = stopPoint;
        this.type = type;
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

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
