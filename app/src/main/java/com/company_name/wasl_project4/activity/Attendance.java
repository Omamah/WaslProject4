package com.company_name.wasl_project4.activity;


import java.util.HashMap;
import java.util.Map;

public class Attendance {

    public Map<String, String>  usersToQR = new HashMap<>();
    private String eventId;
    private boolean isFirst;

    public Attendance(String eventId) {
        this.eventId = eventId;
    }

    public Map<String, String> getUsersToQR() {
        return usersToQR;
    }

    public void setUsersToQR(Map<String, String> usersToQR) {
        this.usersToQR = usersToQR;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
