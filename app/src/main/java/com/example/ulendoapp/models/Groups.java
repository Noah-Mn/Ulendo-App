package com.example.ulendoapp.models;

public class Groups {
    String startingPoint, destination,memberName;

    public Groups(String startingPoint, String destination, String memberName) {
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.memberName = memberName;
    }

    public Groups() {

    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
