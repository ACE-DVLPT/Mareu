package fr.ace.mareu.model;

public class MeetingRoom {

    private String mName;
    private String mColor;

    public MeetingRoom(String name, String color) {
        mName = name;
        mColor = color;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }
}
