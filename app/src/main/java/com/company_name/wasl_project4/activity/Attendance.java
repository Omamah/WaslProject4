package com.company_name.wasl_project4.activity;


import java.util.HashMap;
import java.util.Map;

public class Attendance {

    String id;
    public HashMap<String, String>  usersToQR = new HashMap<>();
    private String eventId;
    private int counter=0;


    public Attendance(String id) {
        this.id = id;
        this.counter++;
    }

    public Map<String, String> getUsersToQR() {
        return usersToQR;
    }

    public void setUsersToQR(HashMap<String, String> usersToQR) {
        this.usersToQR = usersToQR;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }


}
