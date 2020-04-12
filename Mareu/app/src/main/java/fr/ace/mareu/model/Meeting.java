package fr.ace.mareu.model;

import java.util.ArrayList;

public class Meeting {

    private String mTopic;
    private String mPlace;
    private String mDate;
    private String mHour;
    private String mDuration;
    private ArrayList<String> mMembersByArrayList;

    public Meeting(String topic, String place, String date, String hour, String duration, ArrayList<String> membersByArrayList) {
        mTopic = topic;
        mPlace = place;
        mDate = date;
        mHour = hour;
        mDuration = duration;
        mMembersByArrayList = membersByArrayList;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
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

    public String getHour() {
        return mHour;
    }

    public void setHour(String hour) {
        mHour = hour;
    }

    public ArrayList<String> getMembersByArrayList() {
        return mMembersByArrayList;
    }

    public void setMembersByArrayList(ArrayList<String> membersByArrayList) {
        mMembersByArrayList = membersByArrayList;
    }
}
