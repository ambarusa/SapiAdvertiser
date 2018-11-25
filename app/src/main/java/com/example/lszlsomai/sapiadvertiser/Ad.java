package com.example.lszlsomai.sapiadvertiser;

public class Ad {
    private String        title;
    private String        shortDescription;
    private StringBuilder longDescription;
    private String        phoneNumber;
    private String        email;
    private String        location;
    private int           views;

    public Ad(String title, String shortDescription, StringBuilder longDescription, String phoneNumber, String location) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.views = 0;
        this.email = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public StringBuilder getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(StringBuilder longDescription) {
        this.longDescription = longDescription;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
