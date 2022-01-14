package com.example.ulendoapp;

public class DatabaseHelper {
    public DatabaseHelper() {
    }
    String textFirstName, textLastName, textPhoneNumber, textGender;

    public String getTextFirstName() {
        return textFirstName;
    }

    public void setTextFirstName(String textFirstName) {
        this.textFirstName = textFirstName;
    }

    public String getTextLastName() {
        return textLastName;
    }

    public void setTextLastName(String textLastName) {
        this.textLastName = textLastName;
    }

    public String getTextPhoneNumber() {
        return textPhoneNumber;
    }

    public void setTextPhoneNumber(String textPhoneNumber) {
        this.textPhoneNumber = textPhoneNumber;
    }

    public String getTextGender() {
        return textGender;
    }

    public void setTextGender(String textGender) {
        this.textGender = textGender;
    }

    public DatabaseHelper(String textFirstName, String textLastName, String textPhoneNumber, String textGender) {
        this.textFirstName = textFirstName;
        this.textLastName = textLastName;
        this.textPhoneNumber = textPhoneNumber;
        this.textGender = textGender;
    }
}
