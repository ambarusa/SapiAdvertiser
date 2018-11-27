package com.example.lszlsomai.sapiadvertiser;

public class Post {

    private String postTitle;
    private String postBriefDetails;
    private String avatarURL;
    private String postThumbnailURL;
    private String postNumberClicked;

    public Post() {
    }

    public Post(String postTitle, String postBriefDetails, String avatarURL, String postThumbnailURL, String postNumberClicked) {
        this.postTitle = postTitle;
        this.postBriefDetails = postBriefDetails;
        this.avatarURL = avatarURL;
        this.postThumbnailURL = postThumbnailURL;
        this.postNumberClicked = postNumberClicked;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostBriefDetails() {
        return postBriefDetails;
    }

    public void setPostBriefDetails(String postBriefDetails) {
        this.postBriefDetails = postBriefDetails;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getPostThumbnailURL() {
        return postThumbnailURL;
    }

    public void setPostThumbnailURL(String postThumbnailURL) {
        this.postThumbnailURL = postThumbnailURL;
    }

    public String getPostNumberClicked() {
        return postNumberClicked;
    }

    public void setPostNumberClicked(String postNumberClicked) {
        this.postNumberClicked = postNumberClicked;
    }
}
