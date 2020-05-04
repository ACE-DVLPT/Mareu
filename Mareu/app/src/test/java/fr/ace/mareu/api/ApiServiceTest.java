package fr.ace.mareu.api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.ui.MeetingCreatorActivity;
import fr.ace.mareu.utils.di.DI;

import static org.junit.Assert.*;

public class ApiServiceTest {

    private ApiService mApiService;
    private MeetingCreatorActivity mMeetingCreatorActivity;
    private ArrayList<Meeting> mMeetingsList;
    private ArrayList<String> mFilterList;

    @Before
    public void setup() {
        mApiService = DI.getApiService();
        mMeetingCreatorActivity = new MeetingCreatorActivity();
        mMeetingsList = new ArrayList<>();
        mFilterList = new ArrayList<>();
    }

    public Calendar initCalendar(){
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, 0);
        mCalendar.set(Calendar.MONTH, 0);
        mCalendar.set(Calendar.DAY_OF_MONTH, 0);
        mCalendar.set(Calendar.HOUR, 0);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);
        return mCalendar;
    }

    @Test
    public void getMeetingsList() {
        assertEquals(mApiService.getMeetingsList(mFilterList).size(), 0);
        mApiService.addMeeting(meetingA());

        assertEquals(mApiService.getMeetingsList(mFilterList).size(), 1);



//        // getMeetingsList and list must be empty
//        mMeetingsList.clear();
//        mMeetingsList.addAll(mApiService.getMeetingsList(mFilterList));
//        assertTrue(mMeetingsList.isEmpty());
//
//        // add some meetings on API meeting list
//        mApiService.addMeeting(meetingA());
//        mApiService.addMeeting(meetingB());
//        mApiService.addMeeting(meetingC());
//
//
//
//
//
//        // when method is called, must find 3 items
//        mMeetingsList.addAll(mApiService.getMeetingsList(mFilterList));
//        assertEquals(mApiService.getMeetingsList(mFilterList).size(), 3);
//
////        assertEquals(mMeetingsList.size(), 3);
    }

    @Test
    public void getMembersReminderList() {
    }

    @Test
    public void addMemberEmailToReminderList() {
    }

    @Test
    public void getMeetingRoomsList() {
    }

    @Test
    public void addMeeting() {
    }

    @Test
    public void removeMeeting() {
    }

    @Test
    public void checkIfNoDuplicationMeeting() {
    }

    private Meeting meetingA() {
        Meeting mMeetingA = new Meeting("MeetingA","Peach",initCalendar(),initCalendar(), new ArrayList<>(Arrays.asList((""))));
        mMeetingA.setDate(mMeetingCreatorActivity.setDate(mMeetingA,2030,0,1));
        mMeetingA.setDate(mMeetingCreatorActivity.setTime(mMeetingA,13,00));
        mMeetingA.setDate(mMeetingCreatorActivity.setDuration(0,45));
        return mMeetingA;
    }

    private Meeting meetingB() {
        Meeting mMeetingB = new Meeting("MeetingB","Peach",initCalendar(),initCalendar(), new ArrayList<>(Arrays.asList((""))));
        mMeetingB.setDate(mMeetingCreatorActivity.setDate(mMeetingB,2030,0,1));
        mMeetingB.setDate(mMeetingCreatorActivity.setTime(mMeetingB,13,45));
        mMeetingB.setDate(mMeetingCreatorActivity.setDuration(0,45));
        return mMeetingB;
    }

    private Meeting meetingC() {
        Meeting mMeetingC = new Meeting("MeetingC","Mario",initCalendar(),initCalendar(), new ArrayList<>(Arrays.asList((""))));
        mMeetingC.setDate(mMeetingCreatorActivity.setDate(mMeetingC,2030,0,1));
        mMeetingC.setDate(mMeetingCreatorActivity.setTime(mMeetingC,13,00));
        mMeetingC.setDate(mMeetingCreatorActivity.setDuration(0,45));
        return mMeetingC;
    }
}