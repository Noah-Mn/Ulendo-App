package com.example.ulendoapp.models;

/**
 * The type Driver vehicles model.
 */
public class DriverVehiclesModel {
    /**
     * The Vehicle brand.
     */
    public String  vehicleBrand, /**
     * The Vehicle model.
     */
    vehicleModel, /**
     * The Vehicle model yr.
     */
    vehicleModelYr, /**
     * The Vehicle color.
     */
    vehicleColor, /**
     * The Vehicle booking type.
     */
    vehicleBookingType, /**
     * The Vehicle license plate.
     */
    vehicleLicensePlate, /**
     * The Vehicle license id.
     */
    vehicleLicenseId,
    /**
     * The Driver status.
     */
    driverStatus, /**
     * The Passenger rides.
     */
    passengerRides, /**
     * The Driving rides.
     */
    drivingRides, /**
     * The Rating.
     */
    rating, /**
     * The Email.
     */
    email;

    /**
     * Instantiates a new Driver vehicles model.
     */
    public DriverVehiclesModel(){}

    /**
     * Instantiates a new Driver vehicles model.
     *
     * @param vehicleBrand   the vehicle brand
     * @param vehicleModel   the vehicle model
     * @param vehicleModelYr the vehicle model yr
     * @param vehicleColor   the vehicle color
     */
    public DriverVehiclesModel(String vehicleBrand, String vehicleModel, String vehicleModelYr, String vehicleColor) {
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehicleModelYr = vehicleModelYr;
        this.vehicleColor = vehicleColor;
    }

    /**
     * Instantiates a new Driver vehicles model.
     *
     * @param vehicleBrand        the vehicle brand
     * @param vehicleModel        the vehicle model
     * @param vehicleModelYr      the vehicle model yr
     * @param vehicleColor        the vehicle color
     * @param vehicleBookingType  the vehicle booking type
     * @param vehicleLicensePlate the vehicle license plate
     * @param vehicleLicenseId    the vehicle license id
     * @param driverStatus        the driver status
     * @param passengerRides      the passenger rides
     * @param drivingRides        the driving rides
     * @param rating              the rating
     * @param email               the email
     */
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

    /**
     * Gets vehicle brand.
     *
     * @return the vehicle brand
     */
    public String getVehicleBrand() {
        return vehicleBrand;
    }

    /**
     * Sets vehicle brand.
     *
     * @param vehicleBrand the vehicle brand
     */
    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    /**
     * Gets vehicle model.
     *
     * @return the vehicle model
     */
    public String getVehicleModel() {
        return vehicleModel;
    }

    /**
     * Sets vehicle model.
     *
     * @param vehicleModel the vehicle model
     */
    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    /**
     * Gets vehicle model yr.
     *
     * @return the vehicle model yr
     */
    public String getVehicleModelYr() {
        return vehicleModelYr;
    }

    /**
     * Sets vehicle model yr.
     *
     * @param vehicleModelYr the vehicle model yr
     */
    public void setVehicleModelYr(String vehicleModelYr) {
        this.vehicleModelYr = vehicleModelYr;
    }

    /**
     * Gets vehicle color.
     *
     * @return the vehicle color
     */
    public String getVehicleColor() {
        return vehicleColor;
    }

    /**
     * Sets vehicle color.
     *
     * @param vehicleColor the vehicle color
     */
    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    /**
     * Gets vehicle booking type.
     *
     * @return the vehicle booking type
     */
    public String getVehicleBookingType() {
        return vehicleBookingType;
    }

    /**
     * Sets vehicle booking type.
     *
     * @param vehicleBookingType the vehicle booking type
     */
    public void setVehicleBookingType(String vehicleBookingType) {
        this.vehicleBookingType = vehicleBookingType;
    }

    /**
     * Gets vehicle license plate.
     *
     * @return the vehicle license plate
     */
    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    /**
     * Sets vehicle license plate.
     *
     * @param vehicleLicensePlate the vehicle license plate
     */
    public void setVehicleLicensePlate(String vehicleLicensePlate) {
        this.vehicleLicensePlate = vehicleLicensePlate;
    }

    /**
     * Gets vehicle license id.
     *
     * @return the vehicle license id
     */
    public String getVehicleLicenseId() {
        return vehicleLicenseId;
    }

    /**
     * Sets vehicle license id.
     *
     * @param vehicleLicenseId the vehicle license id
     */
    public void setVehicleLicenseId(String vehicleLicenseId) {
        this.vehicleLicenseId = vehicleLicenseId;
    }

    /**
     * Gets driver status.
     *
     * @return the driver status
     */
    public String getDriverStatus() {
        return driverStatus;
    }

    /**
     * Sets driver status.
     *
     * @param driverStatus the driver status
     */
    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    /**
     * Gets passenger rides.
     *
     * @return the passenger rides
     */
    public String getPassengerRides() {
        return passengerRides;
    }

    /**
     * Sets passenger rides.
     *
     * @param passengerRides the passenger rides
     */
    public void setPassengerRides(String passengerRides) {
        this.passengerRides = passengerRides;
    }

    /**
     * Gets driving rides.
     *
     * @return the driving rides
     */
    public String getDrivingRides() {
        return drivingRides;
    }

    /**
     * Sets driving rides.
     *
     * @param drivingRides the driving rides
     */
    public void setDrivingRides(String drivingRides) {
        this.drivingRides = drivingRides;
    }

    /**
     * Gets rating.
     *
     * @return the rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * Sets rating.
     *
     * @param rating the rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
