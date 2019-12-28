package com.company_name.wasl_project4.activity;


import java.util.HashMap;
import java.util.Map;

public class Attendance {

    public HashMap<String, String>  usersToQR = new HashMap<>();
    private String eventId;
    private int counter=0;


    public Attendance() {
    }

    public Map<String, String> getUsersToQR() {
        return usersToQR;
    }

    public void setUsersToQR(HashMap<String, String> usersToQR) {
        this.usersToQR = usersToQR;
    }


    public int getCounter() {
        return counter;
    }

    public void setCounter() {
        this.counter ++;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }


}
