package com.example.ulendoapp.models;

public class BookingModel {
    String driverEmail,passengerName, passengerEmail, tripId, origin, dest, date, currDate;
    long noPassengers;

    public BookingModel(String driverEmail, String passengerEmail, String tripId, String origin, String dest, String date, String currDate, long noPassengers) {
        this.driverEmail = driverEmail;
        this.passengerEmail = passengerEmail;
        this.tripId = tripId;
        this.origin = origin;
        this.dest = dest;
        this.date = date;
        this.currDate = currDate;
        this.noPassengers = noPassengers;
    }

    public BookingModel(String email, long noPassengers, String origin, String destination,String passengerName, String date) {
        this.date = date;
        this.noPassengers = noPassengers;
        this.origin = origin;
        this.dest = destination;
        this.passengerEmail = email;
        this.passengerName = passengerName;

    }

    public BookingModel() {

    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        origin = origin;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
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

    public long getNoPassengers() {
        return noPassengers;
    }

    public void setNoPassengers(long noPassengers) {
        this.noPassengers = noPassengers;
    }
}
