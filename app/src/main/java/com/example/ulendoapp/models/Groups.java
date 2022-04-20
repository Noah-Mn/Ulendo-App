package com.example.ulendoapp.models;

/**
 * The type Groups.
 */
public class Groups {
    /**
     * The Starting point.
     */
    String startingPoint, /**
     * The Destination.
     */
    destination, /**
     * The Member name.
     */
    memberName;

    /**
     * Instantiates a new Groups.
     *
     * @param startingPoint the starting point
     * @param destination   the destination
     * @param memberName    the member name
     */
    public Groups(String startingPoint, String destination, String memberName) {
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.memberName = memberName;
    }

    /**
     * Instantiates a new Groups.
     */
    public Groups() {

    }

    /**
     * Gets starting point.
     *
     * @return the starting point
     */
    public String getStartingPoint() {
        return startingPoint;
    }

    /**
     * Sets starting point.
     *
     * @param startingPoint the starting point
     */
    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
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
     * Gets member name.
     *
     * @return the member name
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * Sets member name.
     *
     * @param memberName the member name
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
