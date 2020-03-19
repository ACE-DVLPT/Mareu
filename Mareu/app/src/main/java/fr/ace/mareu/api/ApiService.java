package fr.ace.mareu.api;

import java.util.ArrayList;

import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;

public interface ApiService {

ArrayList<Meeting> getMeetingsList();

ArrayList<String> getMembersReminderList();

ArrayList<MeetingRoom> getMeetingRoomList();

void addMemberToMembersReminderList(String member);

void addMeeting(Meeting meeting);

void deleteMeeting(Meeting meeting);

}
