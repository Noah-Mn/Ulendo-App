package com.example.ulendoapp;

import com.google.android.gms.maps.model.LatLng;

public class TripModel extends UserModel{

    private String email, destination, pickupPoint, pickupTime, luggage, complaint, status;
    private LatLng latLng;

    public TripModel() {
    }

    public TripModel(String email, String destination, String pickupPoint, String pickupTime, String luggage, String complaint, String status, LatLng latLng) {
        this.email = email;
        this.destination = destination;
        this.pickupPoint = pickupPoint;
        this.pickupTime = pickupTime;
        this.luggage = luggage;
        this.complaint = complaint;
        this.status = status;
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "TripModel{" +
                "email='" + email + '\'' +
                ", destination='" + destination + '\'' +
                ", pickupPoint='" + pickupPoint + '\'' +
                ", pickupTime='" + pickupTime + '\'' +
                ", luggage='" + luggage + '\'' +
                ", complaint='" + complaint + '\'' +
                ", status='" + status + '\'' +
                ", latLng=" + latLng +
                '}';
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

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
