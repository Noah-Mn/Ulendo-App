package com.example.ulendoapp.models;

import java.io.Serializable;

public class OfferRideModel implements Serializable {
    public String emailAddress, pickupPoint, destination, pickupTime, pickupDate,
            luggage, vehicleModel, state, specialInstructions, currDate, tripId, date;
    public double latitude, longitude;
    public long numberOfSeats, remainingSeats;
    private boolean isChecked = false;

    public OfferRideModel() {
    }

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

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public long getRemainingSeats() {
        return remainingSeats;
    }

    public String getLuggage() {
        return luggage;
    }

    public void setLuggage(String luggage) {
        this.luggage = luggage;
    }

    public void setRemainingSeats(int remainingSeats) {
        this.remainingSeats = remainingSeats;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
}
