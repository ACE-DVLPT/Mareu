package fr.ace.mareu.utils.events;

import fr.ace.mareu.model.Meeting;

/**
 * Event fired when a user delete a meeting
 */
public class AddMeetingEvent {

    /**
     * Meeting to add
     */
    public Meeting mMeeting;

    /**
     * Constructor
     * @param meeting
     */
    public AddMeetingEvent(Meeting meeting) {
        mMeeting = meeting;
    }
}
