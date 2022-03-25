package com.example.mytest.Model;

public class ForumPost {
    private String sender;
    private String id;
    private String message;
    private String catid;
    private String imageURL;
    private String vote;
    private String point;

    public ForumPost(String sender, String receiver, String message, String catid, String imageURL, String vote,  String point){
        this.sender = sender;
        this.id = receiver;
        this.message = message;
        this.catid = catid;
        this.imageURL = imageURL;
        this.vote = vote;
        this.point = point;

    }

    public ForumPost(){

    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getId() {
        return id;
    }

    public void setId(String receiver) {
        this.id = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
