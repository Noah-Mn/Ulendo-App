package com.example.ulendoapp;

public class DriverVehiclesModel {
    public String  vehicleBrand, vehicleModel, vehicleModelYr, vehicleColor, vehicleBookingType, vehicleLicensePlate, vehicleLicenseId,
            driverStatus, passengerRides, drivingRides, rating, email;

     public DriverVehiclesModel(){}

    public DriverVehiclesModel(String vehicleBrand, String vehicleModel, String vehicleModelYr, String vehicleColor) {
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehicleModelYr = vehicleModelYr;
        this.vehicleColor = vehicleColor;
    }

    public DriverVehiclesModel(String vehicleBrand, String vehicleModel, String vehicleModelYr, String vehicleColor,
                               String vehicleBookingType, String vehicleLicensePlate, String vehicleLicenseId,
                               String driverStatus, String passengerRides, String drivingRides, String rating, String email) {
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehicleModelYr = vehicleModelYr;
        this.vehicleColor = vehicleColor;
        this.vehicleBookingType = vehicleBookingType;
        this.vehicleLicensePlate = vehicleLicensePlate;
        this.vehicleLicenseId = vehicleLicenseId;
        this.driverStatus = driverStatus;
        this.passengerRides = passengerRides;
        this.drivingRides = drivingRides;
        this.rating = rating;
        this.email = email;
    }

    @Override
    public String toString() {
        return "DriverVehiclesModel{" +
                "vehicleBrand='" + vehicleBrand + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", vehicleModelYr='" + vehicleModelYr + '\'' +
                ", vehicleColor='" + vehicleColor + '\'' +
                ", vehicleBookingType='" + vehicleBookingType + '\'' +
                ", vehicleLicensePlate='" + vehicleLicensePlate + '\'' +
                ", vehicleLicenseId='" + vehicleLicenseId + '\'' +
                ", driverStatus='" + driverStatus + '\'' +
                ", passengerRides='" + passengerRides + '\'' +
                ", drivingRides='" + drivingRides + '\'' +
                ", rating='" + rating + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleModelYr() {
        return vehicleModelYr;
    }

    public void setVehicleModelYr(String vehicleModelYr) {
        this.vehicleModelYr = vehicleModelYr;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleBookingType() {
        return vehicleBookingType;
    }

    public void setVehicleBookingType(String vehicleBookingType) {
        this.vehicleBookingType = vehicleBookingType;
    }

    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    public void setVehicleLicensePlate(String vehicleLicensePlate) {
        this.vehicleLicensePlate = vehicleLicensePlate;
    }

    public String getVehicleLicenseId() {
        return vehicleLicenseId;
    }

    public void setVehicleLicenseId(String vehicleLicenseId) {
        this.vehicleLicenseId = vehicleLicenseId;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getPassengerRides() {
        return passengerRides;
    }

    public void setPassengerRides(String passengerRides) {
        this.passengerRides = passengerRides;
    }

    public String getDrivingRides() {
        return drivingRides;
    }

    public void setDrivingRides(String drivingRides) {
        this.drivingRides = drivingRides;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
