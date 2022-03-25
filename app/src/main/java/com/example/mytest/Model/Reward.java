package com.example.mytest.Model;

public class Reward {
    private String id;
    private String rname;
    private String ravailability;

    public Reward(String id, String rname, String ravailability){
        this.id = id;
        this.rname = rname;
        this.ravailability = ravailability;

    }

    public Reward(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRavailability() {
        return ravailability;
    }

    public void setRavailability(String ravailability) {
        this.ravailability = ravailability;
    }
}
