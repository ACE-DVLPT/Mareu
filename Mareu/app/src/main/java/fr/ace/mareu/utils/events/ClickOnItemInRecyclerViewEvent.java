package fr.ace.mareu.utils.events;

import fr.ace.mareu.model.Meeting;

/**
 * Event fired when a user delete a meeting
 */
public class ClickOnItemInRecyclerViewEvent {

    /**
     * Meeting to delete
     */
    public Meeting mMeeting;
    public int mListPosition;

    /**
     * Constructor
     * @param meeting
     */
    public ClickOnItemInRecyclerViewEvent(Meeting meeting, int listPosition) {
        mMeeting = meeting;
        mListPosition = listPosition;
    }
}
