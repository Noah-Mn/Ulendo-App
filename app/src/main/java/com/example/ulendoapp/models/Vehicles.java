package com.example.ulendoapp.models;

import java.io.Serializable;

/**
 * The type Vehicles.
 */
public class Vehicles {
    /**
     * The Vehicle name.
     */
    public String vehicleName, /**
     * The License plate.
     */
    licensePlate;

    /**
     * Instantiates a new Vehicles.
     */
    public Vehicles() {

    }

    /**
     * Instantiates a new Vehicles.
     *
     * @param vehicleName  the vehicle name
     * @param licensePlate the license plate
     */
    public Vehicles(String vehicleName, String licensePlate) {
        this.vehicleName = vehicleName;
        this.licensePlate = licensePlate;
    }

    /**
     * Gets vehicle name.
     *
     * @return the vehicle name
     */
    public String getVehicleName() {
        return vehicleName;
    }

    /**
     * Sets vehicle name.
     *
     * @param vehicleName the vehicle name
     */
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    /**
     * Gets license plate.
     *
     * @return the license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Sets license plate.
     *
     * @param licensePlate the license plate
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

}
