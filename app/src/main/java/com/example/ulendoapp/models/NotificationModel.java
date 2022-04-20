package com.example.ulendoapp.models;

import java.io.Serializable;

/**
 * The type Notification model.
 */
public class NotificationModel{
    /**
     * The Start point.
     */
    public String startPoint, /**
     * The Stop point.
     */
    stopPoint, /**
     * The Status.
     */
    status;

    /**
     * Instantiates a new Notification model.
     *
     * @param startPoint the start point
     * @param stopPoint  the stop point
     * @param status     the status
     */
    public NotificationModel(String startPoint, String stopPoint, String status) {
        this.startPoint = startPoint;
        this.stopPoint = stopPoint;
        this.status = status;
    }

    /**
     * Instantiates a new Notification model.
     */
    public NotificationModel() {

    }

    /**
     * Gets start point.
     *
     * @return the start point
     */
    public String getStartPoint() {
        return startPoint;
    }

    /**
     * Sets start point.
     *
     * @param startPoint the start point
     */
    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * Gets stop point.
     *
     * @return the stop point
     */
    public String getStopPoint() {
        return stopPoint;
    }

    /**
     * Sets stop point.
     *
     * @param stopPoint the stop point
     */
    public void setStopPoint(String stopPoint) {
        this.stopPoint = stopPoint;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
