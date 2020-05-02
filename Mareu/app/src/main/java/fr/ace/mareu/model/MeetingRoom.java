package fr.ace.mareu.model;

import androidx.annotation.NonNull;

/**
 * Model object representing a meeting room
 */
public class MeetingRoom {

    /** name of the meeting room*/
    private String mName;

    public MeetingRoom(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    @NonNull
    @Override
    public String toString() {
        return mName;
    }
}
