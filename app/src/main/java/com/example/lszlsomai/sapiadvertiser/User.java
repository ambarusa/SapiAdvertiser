package com.example.lszlsomai.sapiadvertiser;

public class User {

    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPhoneNumber;
    private String mAddress;

    public User() {
    }

    public User(String mFirstName, String mLastName, String mEmail, String mPhoneNumber, String mAddress) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mEmail = mEmail;
        this.mPhoneNumber = mPhoneNumber;
        this.mAddress = mAddress;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

}
