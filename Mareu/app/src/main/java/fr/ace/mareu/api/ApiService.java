package fr.ace.mareu.api;

import java.util.ArrayList;

import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;
import fr.ace.mareu.model.Member;

public interface ApiService {

ArrayList<Meeting> getMeetingsList();

ArrayList<Member> getMembersReminderList();

ArrayList<MeetingRoom> getMeetingRoomList();

void addMemberToMembersReminderList(Member member);

void addMeeting(Meeting meeting);

void deleteMeeting(Meeting meeting);

}
