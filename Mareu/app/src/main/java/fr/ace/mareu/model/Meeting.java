package fr.ace.mareu.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Meeting {

    private String mTopic;
    private String mPlace;
    private Calendar mDate;
    private String mStringDate;
    private Calendar mHour;
    private String mStringHour;
    private Calendar mDuration;
    private String mStringDuration;
    private ArrayList<String> mMembers;

    public Meeting(String topic, String place, Calendar date, Calendar hour, Calendar duration, ArrayList<String> members) {
        mTopic = topic;
        mPlace = place;
        mDate = date;
        mHour = hour;
        mDuration = duration;
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

    public Calendar getDate() {
        return mDate;
    }

    public void setDate(Calendar date) {
        mDate = date;
    }

    public String getStringDate() {
        mStringDate = DateFormat.getDateInstance(DateFormat.FULL).format(mDate.getTime());
        return mStringDate;
    }

    public Calendar getHour() {
        return mHour;
    }

    public void setHour(Calendar hour) {
        mHour = hour;
    }

    public String getStringHour() {
        mStringHour = String.format("%02d",mHour.get(Calendar.HOUR_OF_DAY)) + "h" + String.format("%02d",mHour.get(Calendar.MINUTE));
        return mStringHour;
    }

    public Calendar getDuration() {
        return mDuration;
    }

    public void setDuration(Calendar duration) {
        mDuration = duration;
    }

    public String getStringDuration() {
        if (mDuration.get(Calendar.HOUR) > 0){
            if (mDuration.get(Calendar.MINUTE) == 0){
                mStringDuration = (mDuration.get(Calendar.HOUR) + "h");
            } else {
                mStringDuration = (mDuration.get(Calendar.HOUR) + "h" + String.format("%02d", mDuration.get(Calendar.MINUTE)));
            }
        } else {
            mStringDuration = (String.format("%02d", mDuration.get(Calendar.MINUTE)) + "min");
        }
        return mStringDuration;
    }

    public ArrayList<String> getMembers() {
        return mMembers;
    }

    public void setMembers(ArrayList<String> members) {
        mMembers = members;
    }
}
