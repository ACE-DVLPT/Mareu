package fr.ace.mareu.model;

import java.util.ArrayList;

public class Meeting {

    private String mTopic;
    private String mPlace;
    private String mDate;
    private String mHour;
    private ArrayList<Member> mMembers;

    public Meeting(String topic, String place, String date, String hour, ArrayList<Member> members) {
        mTopic = topic;
        mPlace = place;
        mDate = date;
        mHour = hour;
        mMembers = members;
    }

    public String getTopic() {
        return mTopic;
    }

    public void setTopic(String topic) {
        mTopic = topic;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getHour() {
        return mHour;
    }

    public void setHour(String hour) {
        mHour = hour;
    }

    public ArrayList<Member> getMembers() {
        return mMembers;
    }

    public void setMembers(ArrayList<Member> members) {
        mMembers = members;
    }
}
