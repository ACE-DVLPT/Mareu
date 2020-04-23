package fr.ace.mareu.api;

import java.util.ArrayList;

import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;

public class FakeApiService implements ApiService {

    private ArrayList<Meeting> mMeetingsList = FakeApiServiceGenerator.generateMeetingsList();
    private ArrayList<String> mMembersReminderList = FakeApiServiceGenerator.generateMembersReminderList();
    private ArrayList<MeetingRoom> mMeetingRoomsList = FakeApiServiceGenerator.generateMeetingRoomsList();

    @Override
    public ArrayList<Meeting> getMeetingsList() {
        return mMeetingsList;
    }

    @Override
    public ArrayList<String> getMembersReminderList() {
        return mMembersReminderList;
    }

    @Override
    public ArrayList<MeetingRoom> getMeetingRoomList() {
        return mMeetingRoomsList;
    }

    @Override
    public void addMemberToMembersReminderList(String member) {
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

    @Override
    public Boolean checkNoDuplicateMeeting(Meeting meeting) {
        Boolean result = false;

        for (int i = 0 ; i < mMeetingsList.size() ; i++){
            if(
                    meeting.getPlace().equals(mMeetingsList.get(i).getPlace()) &
                    meeting.getDate().equals(mMeetingsList.get(i).getDate())
                    //TODO
            ){
                result = true;
            } else{
                result = false;
            }
        }

        return result;
    }
}
