package com.example.ulendoapp.models;

public class OfferRideModel {
    public String emailAddress, pickupPoint, destination, pickupTime,
            numberOfSeats, bookedSeats, luggage, vehicleModel, state, specialInstructions, date, currDate;
    public double latitude, longitude;

    public OfferRideModel(double latitude, double longitude, String pickupPoint, String destination, String pickupTime,
                          String numberOfSeats, String bookedSeats, String luggage, String state, String date, String currDate, String email) {
        this.emailAddress = email;
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.numberOfSeats = numberOfSeats;
        this.bookedSeats = bookedSeats;
        this.luggage = luggage;
        this.state = state;
        this.date = date;
        this.currDate = currDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public OfferRideModel(double latitude, double longitude, String pickupPoint, String destination,
                          String pickupTime, String numberOfSeats, String bookedSeats, String state, String luggage) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.numberOfSeats = numberOfSeats;
        this.bookedSeats = bookedSeats;
        this.luggage = luggage;
        this.state = state;
    }

//    String pickupPoint = offerRideModelList.get(i).getPickupPoint();
//    String destination = offerRideModelList.get(i).getPickupPoint();
//    String email = offerRideModelList.get(i).getEmailAddress();
//    String pickupTime = offerRideModelList.get(i).getPickupTime();

    public OfferRideModel(String pickupPoint, String destination, String pickupTime, String emailAddress) {
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.emailAddress = emailAddress;
    }

    public OfferRideModel(String pickupPoint, String destination, String pickupTime,
                          String numberOfSeats, String bookedSeats, String luggage, String state, String date, String currDate, String email) {
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.pickupTime = pickupTime;
        this.numberOfSeats = numberOfSeats;
        this.bookedSeats = bookedSeats;
        this.luggage = luggage;
        this.state = state;
        this.date = date;
        this.currDate = currDate;
        this.emailAddress = email;
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
                ", date='" + date + '\'' +
                ", currDate='" + currDate + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
