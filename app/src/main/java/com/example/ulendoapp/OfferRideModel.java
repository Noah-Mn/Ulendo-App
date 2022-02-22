package com.example.ulendoapp;

public class OfferRideModel {
    public String emailAddress, location, pickupPoint, destination, pickupTime,
            numberOfSeats, bookedSeats, vehicleModel, state, specialInstructions;

    public OfferRideModel() {
    }

    public OfferRideModel(String emailAddress, String latLng, String pickupPoint, String destination, String pickupTime,
                          String numberOfSeats, String bookedSeats, String vehicleModel, String state, String specialInstructions) {
        this.emailAddress = emailAddress;
        this.location = latLng;
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.numberOfSeats = numberOfSeats;
        this.bookedSeats = bookedSeats;
        this.vehicleModel = vehicleModel;
        this.state = state;
        this.specialInstructions = specialInstructions;
    }


    @Override
    public String toString() {
        return "OfferRideModel{" +
                "emailAddress='" + emailAddress + '\'' +
                ", location='" + location + '\'' +
                ", pickupPoint='" + pickupPoint + '\'' +
                ", destination='" + destination + '\'' +
                ", pickupTime='" + pickupTime + '\'' +
                ", numberOfSeats='" + numberOfSeats + '\'' +
                ", bookedSeats='" + bookedSeats + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", state='" + state + '\'' +
                ", specialInstructions='" + specialInstructions + '\'' +
                '}';
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
