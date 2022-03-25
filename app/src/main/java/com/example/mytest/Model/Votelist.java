package com.example.mytest.Model;

public class Votelist {
    private String id;
    private String downvoted;
    private String upvoted;

    public Votelist(String id, String downvoted, String upvoted){
        this.id = id;
        this.downvoted = downvoted;
        this.upvoted = upvoted;

    }

    public Votelist(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDownvoted() {
        return downvoted;
    }

    public void setDownvoted(String downvoted) {
        this.downvoted = downvoted;
    }

    public String getUpvoted() {
        return upvoted;
    }

    public void setUpvoted(String upvoted) {
        this.upvoted = upvoted;
    }

}
