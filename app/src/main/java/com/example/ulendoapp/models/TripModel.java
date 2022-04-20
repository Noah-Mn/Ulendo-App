package com.example.ulendoapp.models;

import java.io.Serializable;

/**
 * The type Trip model.
 */
public class TripModel{
    /**
     * The Start point.
     */
    String startPoint, /**
     * The Destination.
     */
    destination, /**
     * The Time.
     */
    time, /**
     * The Date.
     */
    date, /**
     * The Email.
     */
    email, /**
     * The Status.
     */
    status;

    /**
     * Instantiates a new Trip model.
     */
    public TripModel() {
    }

    /**
     * Instantiates a new Trip model.
     *
     * @param startPoint  the start point
     * @param destination the destination
     * @param time        the time
     * @param date        the date
     * @param email       the email
     * @param status      the status
     */
    public TripModel(String startPoint, String destination, String time, String date, String email, String status) {
        this.startPoint = startPoint;
        this.destination = destination;
        this.time = time;
        this.date = date;
        this.email = email;
        this.status = status;
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
     * Gets destination.
     *
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets destination.
     *
     * @param destination the destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
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
