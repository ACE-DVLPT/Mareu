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
        return !meetingIsDuplicated(meeting, mMeetingsList);
    }

    // TODO : unit test
    public Boolean meetingIsDuplicated(Meeting meeting, ArrayList<Meeting> meetingsList){
        Boolean result;
        int count = 0;
        if (meetingsList.isEmpty()){
        } else {
            for (int i = 0 ; i < meetingsList.size() ; i++){
                if(
                        meeting.getPlace().equals(meetingsList.get(i).getPlace()) &
                        (meeting.getStringDate().equals(meetingsList.get(i).getStringDate())) &
                        ((meeting.getStartTime().compareTo(meetingsList.get(i).getStartTime()) == 1 & (meeting.getStartTime().compareTo(meetingsList.get(i).getEndTime()) == -1)) ||
                                ((meeting.getEndTime().compareTo(meetingsList.get(i).getStartTime()) == 1) & (meeting.getEndTime().compareTo(meetingsList.get(i).getEndTime()) == -1)) ||
                                ((meeting.getStartTime().compareTo(meetingsList.get(i).getStartTime()) == -1) & (meeting.getEndTime().compareTo(meetingsList.get(i).getEndTime()) == 1)))
                ){
                    count++;
                }
            }
        }
        if (count != 0){
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}

