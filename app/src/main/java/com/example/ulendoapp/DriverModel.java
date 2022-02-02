package com.example.ulendoapp;

public class DriverModel {
    private String carModel, plateNumber, physicalAddress, emailAddress;
     public DriverModel(){}

    public DriverModel(String carModel, String plateNumber, String physicalAddress, String emailAddress) {
        this.carModel = carModel;
        this.plateNumber = plateNumber;
        this.physicalAddress = physicalAddress;
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "DriverModel{" +
                "carModel='" + carModel + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", physicalAddress='" + physicalAddress + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
