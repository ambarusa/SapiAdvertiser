package ro.sapientia.ms.sapiadvertiser.Models;

public class Ad {
    private String title;
    private String shortDescription;
    private String longDescription;
    private String phoneNumber;
    private String email;
    private String address;
    private String views;

    public Ad() {
    }

    public Ad(String title, String shortDescription, String longDescription, String phoneNumber, String email, String address, String views) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.views = views;
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

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
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

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}
