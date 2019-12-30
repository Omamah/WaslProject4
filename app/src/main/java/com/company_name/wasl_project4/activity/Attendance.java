package com.company_name.wasl_project4.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance {



    public List<userAttended> userAttendedList = new ArrayList<userAttended>();


    public HashMap<String, String>  usersToQR = new HashMap<>();

    public HashMap<String, Boolean>  AttendedUsers = new HashMap<>();
    public HashMap<String, Integer>  listIdtoUsers = new HashMap<>();



    private String eventId;
    private int counter=0;
    private int attended=0;

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    private int absent=counter;

    public int getAttended() {
        return attended;
    }

    public void updateAttendded() {
        this.attended ++;
    }

    public int getAbsent() {
        return absent;
    }

    public void updateAbsent() {
        this.absent--;
    }

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
