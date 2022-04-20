package com.example.ulendoapp.models;

import java.io.Serializable;
import java.util.List;

/**
 * The type User model.
 */
public class UserModel implements Serializable {
    private String firstName,name, image, token,id, senderID, lastName, birthday, gender, phoneNumber, email, nationalId, physicalAddress, status, numberOfTrips, rating;

    /**
     * Instantiates a new User model.
     */
    public UserModel(){}

    /**
     * Instantiates a new User model.
     *
     * @param status      the status
     * @param firstName   the first name
     * @param lastName    the last name
     * @param phoneNumber the phone number
     */
    public UserModel(String status, String firstName, String lastName, String phoneNumber) {
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Instantiates a new User model.
     *
     * @param senderID the sender id
     */
    public UserModel(String senderID){
        this.senderID = senderID;
    }

    /**
     * Gets sender id.
     *
     * @return the sender id
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * Sets sender id.
     *
     * @param senderID the sender id
     */
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    /**
     * Instantiates a new User model.
     *
     * @param firstName       the first name
     * @param lastName        the last name
     * @param birthday        the birthday
     * @param gender          the gender
     * @param phoneNumber     the phone number
     * @param email           the email
     * @param nationalId      the national id
     * @param physicalAddress the physical address
     * @param status          the status
     * @param numberOfTrips   the number of trips
     * @param rating          the rating
     */
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

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets birthday.
     *
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Sets birthday.
     *
     * @param birthday the birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    /**
     * Gets national id.
     *
     * @return the national id
     */
    public String getNationalId() {
        return nationalId;
    }

    /**
     * Sets national id.
     *
     * @param nationalId the national id
     */
    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    /**
     * Gets physical address.
     *
     * @return the physical address
     */
    public String getPhysicalAddress() {
        return physicalAddress;
    }

    /**
     * Sets physical address.
     *
     * @param physicalAddress the physical address
     */
    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets number of trips.
     *
     * @return the number of trips
     */
    public String getNumberOfTrips() {
        return numberOfTrips;
    }

    /**
     * Sets number of trips.
     *
     * @param numberOfTrips the number of trips
     */
    public void setNumberOfTrips(String numberOfTrips) {
        this.numberOfTrips = numberOfTrips;
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
}
