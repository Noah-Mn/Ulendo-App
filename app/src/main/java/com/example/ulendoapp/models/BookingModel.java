package com.example.ulendoapp.models;

/**
 * The type Booking model.
 */
public class BookingModel {
    /**
     * The Driver email.
     */
    String driverEmail, /**
     * The Passenger name.
     */
    passengerName, /**
     * The Passenger email.
     */
    passengerEmail, /**
     * The Trip id.
     */
    tripId, /**
     * The Origin.
     */
    origin, /**
     * The Dest.
     */
    dest, /**
     * The Date.
     */
    date, /**
     * The Curr date.
     */
    currDate;
    /**
     * The No passengers.
     */
    long noPassengers;

    /**
     * Instantiates a new Booking model.
     *
     * @param driverEmail    the driver email
     * @param passengerEmail the passenger email
     * @param tripId         the trip id
     * @param origin         the origin
     * @param dest           the dest
     * @param date           the date
     * @param currDate       the curr date
     * @param noPassengers   the no passengers
     */
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

    /**
     * Instantiates a new Booking model.
     *
     * @param email         the email
     * @param noPassengers  the no passengers
     * @param origin        the origin
     * @param destination   the destination
     * @param passengerName the passenger name
     * @param date          the date
     */
    public BookingModel(String email, long noPassengers, String origin, String destination,String passengerName, String date) {
        this.date = date;
        this.noPassengers = noPassengers;
        this.origin = origin;
        this.dest = destination;
        this.passengerEmail = email;
        this.passengerName = passengerName;

    }

    /**
     * Instantiates a new Booking model.
     */
    public BookingModel() {

    }

    /**
     * Gets passenger name.
     *
     * @return the passenger name
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * Sets passenger name.
     *
     * @param passengerName the passenger name
     */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    /**
     * Gets driver email.
     *
     * @return the driver email
     */
    public String getDriverEmail() {
        return driverEmail;
    }

    /**
     * Sets driver email.
     *
     * @param driverEmail the driver email
     */
    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    /**
     * Gets passenger email.
     *
     * @return the passenger email
     */
    public String getPassengerEmail() {
        return passengerEmail;
    }

    /**
     * Sets passenger email.
     *
     * @param passengerEmail the passenger email
     */
    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
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
     * Gets origin.
     *
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets origin.
     *
     * @param origin the origin
     */
    public void setOrigin(String origin) {
        origin = origin;
    }

    /**
     * Gets dest.
     *
     * @return the dest
     */
    public String getDest() {
        return dest;
    }

    /**
     * Sets dest.
     *
     * @param dest the dest
     */
    public void setDest(String dest) {
        this.dest = dest;
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
     * Gets no passengers.
     *
     * @return the no passengers
     */
    public long getNoPassengers() {
        return noPassengers;
    }

    /**
     * Sets no passengers.
     *
     * @param noPassengers the no passengers
     */
    public void setNoPassengers(long noPassengers) {
        this.noPassengers = noPassengers;
    }
}
