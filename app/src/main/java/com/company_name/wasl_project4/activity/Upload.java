package com.company_name.wasl_project4.activity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Upload {
    private String mName;
    private String mImageUrl;
    private  String mFor;
    private  String mAdress;
    private  String mTime;
    private  String mDesc;
    private  String mKey;
    private  String mOrganizer;
    private static Map<String, String> usersToQR = new HashMap<>();


    public Upload() {
        //empty constructor needed
    }


    public Upload(String name, String imageUrl , String foor , String adress , String  time , String desc ,String organizer ) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
        mFor = foor;
        mAdress = adress;
        mTime = time;
        mDesc = desc;
        mOrganizer=organizer;

    }

    public static Map<String, String> getUsersToQR() {
        return usersToQR;
    }

    public void setUsersToQR (Map<String, String> usersToQR) {
        this.usersToQR = usersToQR;
    }

    public String getDesc() {
        return mDesc;
    }
    public void setDesc(String desc) {
        mDesc = desc;
    }

    public String getTime() {
        return mTime;
    }
    public void setTime(String time) {
        mTime = time;
    }

    public String getAdress() {
        return mAdress;
    }
    public void setAdress(String adress) {
        mAdress = adress;
    }

    public String getFor() {
        return mFor;
    }
    public void setFor(String foor) {
        mFor = foor;
    }

    public String getOrganizer() {
        return mOrganizer;
    }
    public void setOrganizer(String organizer) {
        mOrganizer = organizer;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}
