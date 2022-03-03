package com.example.ulendoapp.models;

import java.io.Serializable;

public class Vehicles {
    public String vehicleName, licensePlate;

    public Vehicles() {

    }

    public Vehicles(String vehicleName, String licensePlate) {
        this.vehicleName = vehicleName;
        this.licensePlate = licensePlate;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

}
