package fr.ace.mareu.api;

import java.util.ArrayList;

import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;
import fr.ace.mareu.model.Member;

public class FakeApiService implements ApiService {

    private ArrayList<Meeting> mMeetingsList = FakeApiServiceGenerator.fakeMeetingsList;
    private ArrayList<Member> mMembersReminderList = FakeApiServiceGenerator.fakeMembersReminderList;
    private ArrayList<MeetingRoom> mMeetingRoomsList = FakeApiServiceGenerator.fakeMeetingRoomsList;

    @Override
    public ArrayList<Meeting> getMeetingsList() {
        return mMeetingsList;
    }

    @Override
    public ArrayList<Member> getMembersReminderList() {
        return mMembersReminderList;
    }

    @Override
    public ArrayList<MeetingRoom> getMeetingRoomList() {
        return mMeetingRoomsList;
    }

    @Override
    public void addMemberToMembersReminderList(Member member) {
        mMembersReminderList.add(member);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        mMeetingsList.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetingsList.remove(meeting);
    }
}
