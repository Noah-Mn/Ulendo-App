package com.example.ulendoapp.models;

/**
 * The type Find ride model.
 */
public class FindRideModel extends UserModel{
    private String email, destination, pickupPoint, pickupTime, luggage, complaint, status, specialInstructions;
    private String latLng, date, currDate, pickupDate;
    /**
     * The Booked seats.
     */
    long bookedSeats;

    /**
     * Instantiates a new Find ride model.
     *
     * @param email               the email
     * @param destination         the destination
     * @param pickupPoint         the pickup point
     * @param pickupTime          the pickup time
     * @param luggage             the luggage
     * @param bookedSeats         the booked seats
     * @param complaint           the complaint
     * @param status              the status
     * @param specialInstructions the special instructions
     * @param latLng              the lat lng
     * @param date                the date
     * @param currDate            the curr date
     * @param pickupDate          the pickup date
     */
    public FindRideModel(String email, String destination, String pickupPoint, String pickupTime, String luggage, long bookedSeats, String complaint, String status, String specialInstructions, String latLng, String date, String currDate, String pickupDate) {
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
        this.date = date;
        this.currDate = currDate;
        this.pickupDate = pickupDate;
    }

    /**
     * Instantiates a new Find ride model.
     *
     * @param status              the status
     * @param firstName           the first name
     * @param lastName            the last name
     * @param phoneNumber         the phone number
     * @param email               the email
     * @param destination         the destination
     * @param pickupPoint         the pickup point
     * @param pickupTime          the pickup time
     * @param luggage             the luggage
     * @param bookedSeats         the booked seats
     * @param complaint           the complaint
     * @param status1             the status 1
     * @param specialInstructions the special instructions
     * @param latLng              the lat lng
     * @param date                the date
     * @param currDate            the curr date
     * @param pickupDate          the pickup date
     */
    public FindRideModel(String status, String firstName, String lastName, String phoneNumber, String email, String destination, String pickupPoint, String pickupTime, String luggage, long bookedSeats, String complaint, String status1, String specialInstructions, String latLng, String date, String currDate, String pickupDate) {
        super(status, firstName, lastName, phoneNumber);
        this.email = email;
        this.destination = destination;
        this.pickupPoint = pickupPoint;
        this.pickupTime = pickupTime;
        this.luggage = luggage;
        this.bookedSeats = bookedSeats;
        this.complaint = complaint;
        this.status = status1;
        this.specialInstructions = specialInstructions;
        this.latLng = latLng;
        this.date = date;
        this.currDate = currDate;
        this.pickupDate = pickupDate;
    }

    /**
     * Instantiates a new Find ride model.
     *
     * @param senderID            the sender id
     * @param email               the email
     * @param destination         the destination
     * @param pickupPoint         the pickup point
     * @param pickupTime          the pickup time
     * @param luggage             the luggage
     * @param bookedSeats         the booked seats
     * @param complaint           the complaint
     * @param status              the status
     * @param specialInstructions the special instructions
     * @param latLng              the lat lng
     * @param date                the date
     * @param currDate            the curr date
     * @param pickupDate          the pickup date
     */
    public FindRideModel(String senderID, String email, String destination, String pickupPoint, String pickupTime, String luggage, long bookedSeats, String complaint, String status, String specialInstructions, String latLng, String date, String currDate, String pickupDate) {
        super(senderID);
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
        this.date = date;
        this.currDate = currDate;
        this.pickupDate = pickupDate;
    }


    /**
     * Instantiates a new Find ride model.
     */
    public FindRideModel() {
    }

    /**
     * Instantiates a new Find ride model.
     *
     * @param dest     the dest
     * @param pTime    the p time
     * @param pDate    the p date
     * @param noPeople the no people
     * @param pPoint   the p point
     * @param luggage  the luggage
     */
    public FindRideModel(String dest, String pTime, String pDate, long noPeople, String pPoint, String luggage) {
        this.destination = dest;
        this.pickupTime = pTime;
        this.pickupDate = pDate;
        this.bookedSeats = noPeople;
        this.pickupPoint = pPoint;
        this.luggage = luggage;
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
                ", pickupDate='" + pickupDate + '\'' +
                '}';
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
     * Gets booked seats.
     *
     * @return the booked seats
     */
    public long getBookedSeats() {
        return bookedSeats;
    }

    /**
     * Sets booked seats.
     *
     * @param bookedSeats the booked seats
     */
    public void setBookedSeats(long bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    /**
     * Gets complaint.
     *
     * @return the complaint
     */
    public String getComplaint() {
        return complaint;
    }

    /**
     * Sets complaint.
     *
     * @param complaint the complaint
     */
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

    /**
     * Gets lat lng.
     *
     * @return the lat lng
     */
    public String getLatLng() {
        return latLng;
    }

    /**
     * Sets lat lng.
     *
     * @param latLng the lat lng
     */
    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }
}
