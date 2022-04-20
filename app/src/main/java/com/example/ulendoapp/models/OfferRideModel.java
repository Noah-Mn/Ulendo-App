package com.example.ulendoapp.models;

import java.io.Serializable;

/**
 * The type Offer ride model.
 */
public class OfferRideModel implements Serializable {
    /**
     * The Email address.
     */
    public String emailAddress, /**
     * The Pickup point.
     */
    pickupPoint, /**
     * The Destination.
     */
    destination, /**
     * The Pickup time.
     */
    pickupTime, /**
     * The Pickup date.
     */
    pickupDate,
    /**
     * The Luggage.
     */
    luggage, /**
     * The Vehicle model.
     */
    vehicleModel, /**
     * The State.
     */
    state, /**
     * The Special instructions.
     */
    specialInstructions, /**
     * The Curr date.
     */
    currDate, /**
     * The Trip id.
     */
    tripId, /**
     * The Date.
     */
    date;
    /**
     * The Latitude.
     */
    public double latitude, /**
     * The Longitude.
     */
    longitude;
    /**
     * The Number of seats.
     */
    public long numberOfSeats, /**
     * The Remaining seats.
     */
    remainingSeats;
    private boolean isChecked = false;

    /**
     * Instantiates a new Offer ride model.
     */
    public OfferRideModel() {
    }

    /**
     * Instantiates a new Offer ride model.
     *
     * @param latitude       the latitude
     * @param longitude      the longitude
     * @param pickupPoint    the pickup point
     * @param destination    the destination
     * @param pickupTime     the pickup time
     * @param numberOfSeats  the number of seats
     * @param remainingSeats the remaining seats
     * @param luggage        the luggage
     * @param state          the state
     * @param pickupDate     the pickup date
     * @param currDate       the curr date
     * @param email          the email
     * @param tripId         the trip id
     */
    public OfferRideModel(double latitude, double longitude, String pickupPoint, String destination, String pickupTime,
                          long numberOfSeats, long remainingSeats, String luggage, String state, String pickupDate,
                          String currDate, String email, String tripId) {
        this.emailAddress = email;
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.numberOfSeats = numberOfSeats;
        this.remainingSeats = remainingSeats;
        this.luggage = luggage;
        this.state = state;
        this.pickupDate = pickupDate;
        this.currDate = currDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tripId = tripId;
    }

    /**
     * Instantiates a new Offer ride model.
     *
     * @param pickupPoint    the pickup point
     * @param destination    the destination
     * @param pickupTime     the pickup time
     * @param pickupDate     the pickup date
     * @param emailAddress   the email address
     * @param tripId         the trip id
     * @param luggage        the luggage
     * @param remainingSeats the remaining seats
     * @param latitude       the latitude
     * @param longitude      the longitude
     * @param numberOfSeats  the number of seats
     * @param state          the state
     * @param date           the date
     * @param currDate       the curr date
     * @param specialInst    the special inst
     */
    public OfferRideModel(String pickupPoint, String destination, String pickupTime, String pickupDate, String emailAddress, String tripId,
     String luggage, long remainingSeats, double latitude, double longitude, long numberOfSeats, String state, String date, String currDate, String specialInst) {
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.pickupDate = pickupDate;
        this.emailAddress = emailAddress;
        this.tripId = tripId;
        this.luggage = luggage;
        this.remainingSeats = remainingSeats;
        this.latitude = latitude;
        this.longitude =longitude;
        this.numberOfSeats = numberOfSeats;
        this.state = state;
        this.date =date;
        this.currDate = currDate;
        this.specialInstructions = specialInst;
    }

    @Override
    public String toString() {
        return "OfferRideModel{" +
                "emailAddress='" + emailAddress + '\'' +
                ", pickupPoint='" + pickupPoint + '\'' +
                ", destination='" + destination + '\'' +
                ", pickupTime='" + pickupTime + '\'' +
                ", pickupDate='" + pickupDate + '\'' +
                ", numberOfSeats='" + numberOfSeats + '\'' +
                ", bookedSeats='" + remainingSeats + '\'' +
                ", luggage='" + luggage + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", state='" + state + '\'' +
                ", specialInstructions='" + specialInstructions + '\'' +
                ", currDate='" + currDate + '\'' +
                ", tripId='" + tripId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", isChecked=" + isChecked +
                '}';
    }

    /**
     * Gets trip id.
     *
     * @return the trip id
     */
    public String getTripId() {
        return tripId;
    }

    /**
     * Sets trip id.
     *
     * @param tripId the trip id
     */
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    /**
     * Is checked boolean.
     *
     * @return the boolean
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * Sets checked.
     *
     * @param checked the checked
     */
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    /**
     * Gets pickup date.
     *
     * @return the pickup date
     */
    public String getPickupDate() {
        return pickupDate;
    }

    /**
     * Sets pickup date.
     *
     * @param pickupDate the pickup date
     */
    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    /**
     * Gets curr date.
     *
     * @return the curr date
     */
    public String getCurrDate() {
        return currDate;
    }

    /**
     * Sets curr date.
     *
     * @param currDate the curr date
     */
    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    /**
     * Gets email address.
     *
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets email address.
     *
     * @param emailAddress the email address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets pickup point.
     *
     * @return the pickup point
     */
    public String getPickupPoint() {
        return pickupPoint;
    }

    /**
     * Sets pickup point.
     *
     * @param pickupPoint the pickup point
     */
    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
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
     * Gets pickup time.
     *
     * @return the pickup time
     */
    public String getPickupTime() {
        return pickupTime;
    }

    /**
     * Sets pickup time.
     *
     * @param pickupTime the pickup time
     */
    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    /**
     * Gets number of seats.
     *
     * @return the number of seats
     */
    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets number of seats.
     *
     * @param numberOfSeats the number of seats
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Gets remaining seats.
     *
     * @return the remaining seats
     */
    public long getRemainingSeats() {
        return remainingSeats;
    }

    /**
     * Gets luggage.
     *
     * @return the luggage
     */
    public String getLuggage() {
        return luggage;
    }

    /**
     * Sets luggage.
     *
     * @param luggage the luggage
     */
    public void setLuggage(String luggage) {
        this.luggage = luggage;
    }

    /**
     * Sets remaining seats.
     *
     * @param remainingSeats the remaining seats
     */
    public void setRemainingSeats(int remainingSeats) {
        this.remainingSeats = remainingSeats;
    }

    /**
     * Gets vehicle model.
     *
     * @return the vehicle model
     */
    public String getVehicleModel() {
        return vehicleModel;
    }

    /**
     * Sets vehicle model.
     *
     * @param vehicleModel the vehicle model
     */
    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets special instructions.
     *
     * @return the special instructions
     */
    public String getSpecialInstructions() {
        return specialInstructions;
    }

    /**
     * Sets special instructions.
     *
     * @param specialInstructions the special instructions
     */
    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
}
