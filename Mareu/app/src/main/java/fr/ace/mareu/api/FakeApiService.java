package fr.ace.mareu.api;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;

/**
 * Mock for ApiService
 */
public class FakeApiService implements ApiService {

    private ArrayList<Meeting> mMeetingsList = FakeApiServiceGenerator.generateMeetingsList();
    private ArrayList<String> mMembersReminderList = FakeApiServiceGenerator.generateMembersReminderList();
    private ArrayList<MeetingRoom> mMeetingRoomsList = FakeApiServiceGenerator.generateMeetingRoomsList();

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Meeting> getMeetingsList(ArrayList<String> filtersList) {
        return filterTheList(mMeetingsList,filtersList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<String> getMembersReminderList() {
        return mMembersReminderList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<MeetingRoom> getMeetingRoomsList() {
        return mMeetingRoomsList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeeting(Meeting meeting) {
        mMeetingsList.add(meeting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeMeeting(Meeting meeting) {
        mMeetingsList.remove(meeting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean checkIfNoDuplicationMeeting(Meeting meeting) {
        return !meetingIsDuplicated(meeting, mMeetingsList);
    }

    /**
     * Verifies that the meeting being tested does not interfere with another meeting on the reference list
     * @param meeting is the instance of {@link Meeting} that must be tested
     * @param meetingsList is the {@link ArrayList} of {@link Meeting} used in reference
     * @return a {@link Boolean}. If the result is true,this means that the meeting room is available
     */
    public Boolean meetingIsDuplicated(Meeting meeting, ArrayList<Meeting> meetingsList){
        Boolean result;
        int count = 0;
        if (meetingsList.isEmpty()){
        } else {
            for (int i = 0 ; i < meetingsList.size() ; i++){
                if(
                        meeting.getPlace().equals(meetingsList.get(i).getPlace()) &
                        (meeting.getStringDate().equals(meetingsList.get(i).getStringDate())) &
                                (((meeting.getDate().compareTo(meetingsList.get(i).getDate()) == -1) && (meeting.getEndTime().compareTo(meetingsList.get(i).getEndTime()) == 1)) ||
                                        ((meeting.getDate().compareTo(meetingsList.get(i).getDate()) == -1) && (meeting.getEndTime().compareTo(meetingsList.get(i).getDate()) == 1)) ||
                                        ((meeting.getDate().compareTo(meetingsList.get(i).getEndTime()) == -1) && (meeting.getEndTime().compareTo(meetingsList.get(i).getEndTime()) == 1)) ||
                                        ((meeting.getDate().compareTo(meetingsList.get(i).getDate()) == 1) && (meeting.getEndTime().compareTo(meetingsList.get(i).getEndTime()) == -1)) ||
                                        (meeting.getDate().compareTo(meetingsList.get(i).getDate()) == 0) ||
                                        (meeting.getEndTime().compareTo(meetingsList.get(i).getEndTime()) == 0))
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

    /**
     * Get initialList, apply filters from filtersList and return the list filtered
     * @param initialList is the {@link ArrayList} of {@link Meeting} to filter
     * @param filtersList is an {@link ArrayList} of {@link String} with the filters. It can contain date and/or meeting room (list can be empty)
     * @return an {@link ArrayList} of {@link Meeting} with all meetings filtered
     */
    public ArrayList<Meeting> filterTheList(ArrayList<Meeting> initialList, ArrayList<String> filtersList){
        ArrayList<Meeting> filteredList = new ArrayList<>();
        if (filtersList.size() == 1){
            for (int i = 0 ; i < initialList.size() ; i++){
                if(filtersList.get(0).toLowerCase().equals(initialList.get(i).getPlace().toLowerCase()) ||
                        (filtersList.get(0).toLowerCase().equals(DateFormat.getDateInstance(DateFormat.FULL).format(initialList.get(i).getDate().getTime()).toLowerCase()))){
                    filteredList.add(initialList.get(i));
                }
            }
        } else if (filtersList.size() == 2){
            for (int i = 0 ; i < initialList.size() ; i++){
                if((filtersList.get(0).toLowerCase().equals(initialList.get(i).getPlace().toLowerCase()) ||
                        (filtersList.get(0).toLowerCase().equals(DateFormat.getDateInstance(DateFormat.FULL).format(initialList.get(i).getDate().getTime()).toLowerCase()))) &&
                        (filtersList.get(1).toLowerCase().equals(initialList.get(i).getPlace().toLowerCase()) ||
                                (filtersList.get(1).toLowerCase().equals(DateFormat.getDateInstance(DateFormat.FULL).format(initialList.get(i).getDate().getTime()).toLowerCase())))){
                    filteredList.add(initialList.get(i));
                }
            }
        } else {
            filteredList = initialList;
        }
        removeMeetingWhereTheDateHasEnded(filteredList);
        sortMeetings(filteredList);
        return filteredList;
    }

    /**
     * @param meetingsList is the {@link ArrayList} of {@link Meeting} to sort
     * @return the meetingsList sorted
     */
    public ArrayList<Meeting> sortMeetings(ArrayList<Meeting> meetingsList) {
        Collections.sort(meetingsList, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting meeting01, Meeting meeting02) {
                return meeting01.getDate().compareTo(meeting02.getDate());
            }
        });
        return meetingsList;
    }

    /**
     * @param meetingsList is the {@link ArrayList} of {@link Meeting} to check
     * @return the meetingsList with only meetings where the date has not ended
     */
    public ArrayList<Meeting> removeMeetingWhereTheDateHasEnded(ArrayList<Meeting> meetingsList) {
        Calendar todayDate = Calendar.getInstance();
        for (int i = 0 ; i < meetingsList.size() ; i++) {
            if((meetingsList.get(i).getEndTime().compareTo(todayDate)) < 0){
                meetingsList.remove(i);
            }
        }
        return meetingsList;
    }
}

