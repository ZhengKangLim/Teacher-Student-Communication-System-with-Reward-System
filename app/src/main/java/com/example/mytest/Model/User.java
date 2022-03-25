package com.example.mytest.Model;

public class User {
    private String id;
    private String username;
    private String imageURL;
    private String anonymous;
    private String occupation;
    private String points;


    public User(){

    }

    public User(String id, String username, String imageURL, String anonymous, String occupation, String points){
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.anonymous = anonymous;
        this.occupation = occupation;
        this.points = points;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
