package fr.ace.mareu.api;

import java.util.ArrayList;
import java.util.List;

import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;

/**
 * API client
 */
public interface ApiService {

    /**
     * @param filtersList is an {@link ArrayList} of {@link String} with the filters. It can contain date and/or meeting room (list can be empty)
     * @return an {@link ArrayList} of {@link Meeting} with all meetings filtered
     */
    ArrayList<Meeting> getMeetingsList(ArrayList<String> filtersList);

    /**
     * @return an {@link ArrayList} of {@link String} with email addresses of members
     */
    ArrayList<String> getMembersReminderList();

    /**
     * @return an {@link ArrayList} of {@link String} with the name of meeting rooms
     */
    ArrayList<MeetingRoom> getMeetingRoomsList();

    /**
     * Add the meeting in parameter to the meeting list
     * @param meeting is an instance of {@link Meeting}
     */
    void addMeeting(Meeting meeting);

    /**
     * Remove the meeting in parameter from the meeting list
     * @param meeting is an instance of {@link Meeting}
     */
    void removeMeeting(Meeting meeting);

    /**
     * Checks that the meeting room is available
     * @param meeting is the instance of {@link Meeting} that must be tested
     * @return a {@link Boolean}. If the result is true,this means that the meeting room is available
     */
    Boolean checkIfNoDuplicationMeeting(Meeting meeting);


}
