package fr.ace.mareu.utils.events;

import fr.ace.mareu.model.Meeting;

/**
 * Event fired when a user click on a meeting
 */
public class HighlightMeetingMembersEvent {

    /**
     * Meeting focused
     */
    public Meeting mMeeting;
    public int mListPosition;

    /**
     * Constructor
     * @param meeting
     */
    public HighlightMeetingMembersEvent(Meeting meeting, int listPosition) {
        mMeeting = meeting;
        mListPosition = listPosition;
    }
}
