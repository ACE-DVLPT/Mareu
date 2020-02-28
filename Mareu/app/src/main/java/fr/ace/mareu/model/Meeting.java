package fr.ace.mareu.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Meeting {

    private String mTopic;
    private String mPlace;
    private Date mDate;
    private Time mHour;
    private ArrayList<Member> mMembers;

    public Meeting(String topic, String place, Date date, Time hour, ArrayList<Member> members) {
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Time getHour() {
        return mHour;
    }

    public void setHour(Time hour) {
        mHour = hour;
    }

    public ArrayList<Member> getMembers() {
        return mMembers;
    }

    public void setMembers(ArrayList<Member> members) {
        mMembers = members;
    }
}
