package com.company_name.wasl_project4.activity;

public class userAttended{
    String name ;
    String attended;
    int id;

    public userAttended(String name1, String attended2) {
        name = name1;
        attended = attended2;
    }

    public userAttended(String name, String attended, int id) {
        this.name = name;
        this.attended = attended;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public userAttended() {
    }

    public String isAttended() {
        return attended;
    }

    public void setAttended(String attended) {
        this.attended = attended;
    }
}
