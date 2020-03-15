package fr.ace.mareu.utils.events;

import fr.ace.mareu.model.Meeting;

/**
 * Event fired when a user delete a meeting
 */
public class DeleteMeetingEvent {

    /**
     * Meeting to delete
     */
    public Meeting mMeeting;

    /**
     * Constructor
     * @param meeting
     */
    public DeleteMeetingEvent(Meeting meeting) {
        mMeeting = meeting;
    }
}
