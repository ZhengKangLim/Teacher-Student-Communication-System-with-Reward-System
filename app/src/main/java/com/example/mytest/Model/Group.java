package com.example.mytest.Model;

public class Group {
    private String id;
    private String groupname;
    private String imageURL;


    public Group(){

    }

    public Group(String id, String username, String imageURL){
        this.id = id;
        this.groupname = username;
        this.imageURL = imageURL;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String username) {
        this.groupname = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
