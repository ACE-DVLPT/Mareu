package fr.ace.mareu.model;

import java.util.ArrayList;

public class Meeting {

    private String mTopic;
    private String mPlace;
    private String mHour;
    private ArrayList<Member> mMembersByArrayList;
    private String mMembersByString;

    public Meeting(String topic, String place, String hour, ArrayList<Member> membersList) {
        mTopic = topic;
        mPlace = place;
        mHour = hour;
        mMembersByArrayList = membersList;
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

    public ArrayList<Member> getMembersByArrayList() {
        return mMembersByArrayList;
    }

    public String getMembersByString() {
        StringBuffer buffer = new StringBuffer();
        for (int i=0 ; i < mMembersByArrayList.size() ; i++){
            if ((i == (mMembersByArrayList.size())-1)) {
                buffer.append(mMembersByArrayList.get(i).getEmail());
            } else {
                buffer.append(mMembersByArrayList.get(i).getEmail() + ", ");
            }
        }
        mMembersByString = buffer.toString();
        return mMembersByString;
    }

    public void setMembersByArrayList(ArrayList<Member> membersByArrayList) {
        mMembersByArrayList = membersByArrayList;
    }
}
