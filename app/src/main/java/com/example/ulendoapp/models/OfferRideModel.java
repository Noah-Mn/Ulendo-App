package com.example.ulendoapp.models;

import java.io.Serializable;

public class OfferRideModel implements Serializable {
    public String emailAddress, pickupPoint, destination, pickupTime, pickupDate,
            numberOfSeats, bookedSeats, luggage, vehicleModel, state, specialInstructions, currDate;
    public double latitude, longitude;
    private boolean isChecked = false;

    public OfferRideModel(double latitude, double longitude, String pickupPoint, String destination, String pickupTime,
                          String numberOfSeats, String bookedSeats, String luggage, String state, String pickupDate, String currDate, String email) {
        this.emailAddress = email;
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.numberOfSeats = numberOfSeats;
        this.bookedSeats = bookedSeats;
        this.luggage = luggage;
        this.state = state;
        this.pickupDate = pickupDate;
        this.currDate = currDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public OfferRideModel(String pickupPoint, String destination, String pickupTime, String pickupDate, String emailAddress) {
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.pickupDate = pickupDate;
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "OfferRideModel{" +
                "emailAddress='" + emailAddress + '\'' +
                ", pickupPoint='" + pickupPoint + '\'' +
                ", destination='" + destination + '\'' +
                ", pickupTime='" + pickupTime + '\'' +
                ", numberOfSeats='" + numberOfSeats + '\'' +
                ", bookedSeats='" + bookedSeats + '\'' +
                ", luggage='" + luggage + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", state='" + state + '\'' +
                ", specialInstructions='" + specialInstructions + '\'' +
                ", date='" + pickupDate + '\'' +
                ", currDate='" + currDate + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
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

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getBookedSeats() {
        return bookedSeats;
    }

    public String getLuggage() {
        return luggage;
    }

    public void setLuggage(String luggage) {
        this.luggage = luggage;
    }

    public void setBookedSeats(String bookedSeats) {
        this.bookedSeats = bookedSeats;
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
