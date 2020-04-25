package fr.ace.mareu.api;

import java.util.ArrayList;

import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;

public interface ApiService {

ArrayList<Meeting> getMeetingsList(ArrayList<String> filtersList);

ArrayList<String> getMembersReminderList();

ArrayList<MeetingRoom> getMeetingRoomList();

void addMeeting(Meeting meeting);

void deleteMeeting(Meeting meeting);

Boolean checkIfNoDuplicationMeeting(Meeting meeting);

}
