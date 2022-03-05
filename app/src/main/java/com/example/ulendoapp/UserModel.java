package com.example.ulendoapp;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable {
    private String firstName,name, image, token, senderID, lastName, birthday, gender, phoneNumber, email, nationalId, physicalAddress, status, numberOfTrips, rating;

    public UserModel(){}

    public UserModel(String status, String firstName, String lastName, String phoneNumber) {
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
    public UserModel(String senderID){
        this.senderID = senderID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public UserModel(String firstName, String lastName, String birthday, String gender, String phoneNumber, String email, String nationalId, String physicalAddress, String status, String numberOfTrips, String rating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nationalId = nationalId;
        this.physicalAddress = physicalAddress;
        this.status = status;
        this.numberOfTrips = numberOfTrips;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "firstName='" + firstName + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", token='" + token + '\'' +
                ", senderID='" + senderID + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", physicalAddress='" + physicalAddress + '\'' +
                ", status='" + status + '\'' +
                ", numberOfTrips='" + numberOfTrips + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(String numberOfTrips) {
        this.numberOfTrips = numberOfTrips;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
