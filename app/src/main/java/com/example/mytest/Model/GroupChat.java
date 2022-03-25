package com.example.mytest.Model;

public class GroupChat {
    private String sender;
    private String id;
    private String message;

    public GroupChat(String sender, String receiver, String message){
        this.sender = sender;
        this.id = receiver;
        this.message = message;

    }

    public GroupChat(){

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
}
