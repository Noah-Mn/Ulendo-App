package com.example.ulendoapp;

import com.google.android.gms.maps.model.LatLng;

public class FindRideModel extends UserModel{
    private String email, destination, pickupPoint, pickupTime, luggage, bookedSeats, complaint, status, specialInstructions;
    private String latLng, date, currDate;

    public FindRideModel(String destination, String pickupPoint, String pickupTime, String luggage, String bookedSeats, String latLng) {
        this.destination = destination;
        this.pickupPoint = pickupPoint;
        this.pickupTime = pickupTime;
        this.luggage = luggage;
        this.bookedSeats = bookedSeats;
        this.latLng = latLng;
    }


    public FindRideModel(String destination, String pickupPoint, String pickupTime, String luggage, String bookedSeats, String latLng, String date, String currDate) {
        this.destination = destination;
        this.pickupPoint = pickupPoint;
        this.pickupTime = pickupTime;
        this.luggage = luggage;
        this.bookedSeats = bookedSeats;
        this.latLng = latLng;
        this.date = date;
        this.currDate = currDate;
    }

    public FindRideModel() {
    }

    public FindRideModel(String email, String destination, String pickupPoint, String pickupTime,
                         String luggage, String bookedSeats, String complaint, String status, String specialInstructions, String latLng) {
        this.email = email;
        this.destination = destination;
        this.pickupPoint = pickupPoint;
        this.pickupTime = pickupTime;
        this.luggage = luggage;
        this.bookedSeats = bookedSeats;
        this.complaint = complaint;
        this.status = status;
        this.specialInstructions = specialInstructions;
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "FindRideModel{" +
                "email='" + email + '\'' +
                ", destination='" + destination + '\'' +
                ", pickupPoint='" + pickupPoint + '\'' +
                ", pickupTime='" + pickupTime + '\'' +
                ", luggage='" + luggage + '\'' +
                ", bookedSeats='" + bookedSeats + '\'' +
                ", complaint='" + complaint + '\'' +
                ", status='" + status + '\'' +
                ", specialInstructions='" + specialInstructions + '\'' +
                ", latLng='" + latLng + '\'' +
                ", date='" + date + '\'' +
                ", currDate='" + currDate + '\'' +
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

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getLuggage() {
        return luggage;
    }

    public void setLuggage(String luggage) {
        this.luggage = luggage;
    }

    public String getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(String bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }
}
