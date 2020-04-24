package fr.ace.mareu.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Meeting {

    private String mTopic;
    private String mPlace;
    private Calendar mDate;
    private String mStringDate;
    private Calendar mStartTime;
    private String mStringStartTime;
    private Calendar mEndTime;
    private Calendar mDuration;
    private String mStringDuration;
    private ArrayList<String> mMembers;

    public Meeting(String topic, String place, Calendar date, Calendar hour, Calendar duration, ArrayList<String> members) {
        mTopic = topic;
        mPlace = place;
        mDate = date;
        mStartTime = hour;
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

    public Calendar getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Calendar startTime) {
        mStartTime = startTime;
    }

    public String getStringStartTime() {
        mStringStartTime = String.format("%02d", mStartTime.get(Calendar.HOUR_OF_DAY)) + "h" + String.format("%02d", mStartTime.get(Calendar.MINUTE));
        return mStringStartTime;
    }

    public Calendar getEndTime(){
        mEndTime = Calendar.getInstance();
        mEndTime.setTime(mStartTime.getTime());
        mEndTime.add(Calendar.HOUR_OF_DAY, mDuration.get(Calendar.HOUR));
        mEndTime.add(Calendar.MINUTE, mDuration.get(Calendar.MINUTE));
        return mEndTime;
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
