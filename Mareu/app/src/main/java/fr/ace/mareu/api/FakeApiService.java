package fr.ace.mareu.api;



import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;

public class FakeApiService implements ApiService {

    private ArrayList<Meeting> mMeetingsList = FakeApiServiceGenerator.generateMeetingsList();
    private ArrayList<String> mMembersReminderList = FakeApiServiceGenerator.generateMembersReminderList();
    private ArrayList<MeetingRoom> mMeetingRoomsList = FakeApiServiceGenerator.generateMeetingRoomsList();

    @Override
    public ArrayList<Meeting> getMeetingsList(ArrayList<String> filtersList) {
        return listFiltered(mMeetingsList,filtersList);
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
    public void addMeeting(Meeting meeting) {
        mMeetingsList.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetingsList.remove(meeting);
    }

    @Override
    public Boolean checkIfNoDuplicationMeeting(Meeting meeting) {
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

    // TODO : unit test
    public ArrayList<Meeting> listFiltered(ArrayList<Meeting> initialList, ArrayList<String> filtersList){
        ArrayList<Meeting> finalList = new ArrayList<>();

        if (filtersList.size() == 1){
            for (int i = 0 ; i < initialList.size() ; i++){
                if(filtersList.get(0).toLowerCase().equals(initialList.get(i).getPlace().toLowerCase()) ||
                        (filtersList.get(0).toLowerCase().equals(DateFormat.getDateInstance(DateFormat.FULL).format(initialList.get(i).getDate().getTime()).toLowerCase()))){
                    finalList.add(initialList.get(i));
                }
            }
        } else if (filtersList.size() == 2){
            for (int i = 0 ; i < initialList.size() ; i++){
                if((filtersList.get(0).toLowerCase().equals(initialList.get(i).getPlace().toLowerCase()) ||
                        (filtersList.get(0).toLowerCase().equals(DateFormat.getDateInstance(DateFormat.FULL).format(initialList.get(i).getDate().getTime()).toLowerCase()))) &&
                        (filtersList.get(1).toLowerCase().equals(initialList.get(i).getPlace().toLowerCase()) ||
                                (filtersList.get(1).toLowerCase().equals(DateFormat.getDateInstance(DateFormat.FULL).format(initialList.get(i).getDate().getTime()).toLowerCase())))){
                    finalList.add(initialList.get(i));
                }
            }
        } else {
            finalList = initialList;
        }
        removeMeetingWhenDateHasPassed(finalList);
        sortMeetings(finalList);
        return finalList;
    }

    public ArrayList<Meeting> sortMeetings(ArrayList<Meeting> meetingsList) {

        Collections.sort(meetingsList, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting meeting01, Meeting meeting02) {
                if(meeting01.getDate().get(Calendar.DATE) == (meeting02.getDate().get(Calendar.DATE))){
                    return meeting01.getStartTime().getTime().compareTo(meeting02.getStartTime().getTime());
                } else {
                    return meeting01.getDate().getTime().compareTo(meeting02.getDate().getTime());
                }
            }
        });
        return meetingsList;
    }

    public ArrayList<Meeting> removeMeetingWhenDateHasPassed(ArrayList<Meeting> meetingsList) {
        Calendar todayDate = Calendar.getInstance();
        todayDate.setTime(new Date());
        for (int i = 0 ; i < meetingsList.size() ; i++) {
            if((DateFormat.getDateInstance(DateFormat.FULL).format(todayDate.getTime()).compareTo(DateFormat.getDateInstance(DateFormat.FULL).format(meetingsList.get(i).getDate().getTime())) < 0) ||
                    (DateFormat.getDateInstance(DateFormat.FULL).format(todayDate.getTime()).equals(DateFormat.getDateInstance(DateFormat.FULL).format(meetingsList.get(i).getDate().getTime()))) &&
                            (DateFormat.getTimeInstance(DateFormat.FULL).format(todayDate.getTime()).compareTo(DateFormat.getTimeInstance(DateFormat.FULL).format(meetingsList.get(i).getEndTime().getTime())) > 0)){
                meetingsList.remove(i);
            }
        }
        return meetingsList;
    }
}

