package fr.ace.mareu.model;

import android.graphics.Color;

public class MeetingRoom {

    private String mName;
    private Color mColor;

    public MeetingRoom(String name, Color color) {
        mName = name;
        mColor = color;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Color getColor() {
        return mColor;
    }

    public void setColor(Color color) {
        mColor = color;
    }
}
