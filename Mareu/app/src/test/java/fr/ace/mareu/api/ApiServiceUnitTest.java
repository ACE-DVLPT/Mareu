package fr.ace.mareu.api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;
import fr.ace.mareu.ui.MeetingCreatorActivity;
import fr.ace.mareu.utils.di.DI;

import static org.junit.Assert.*;

public class ApiServiceUnitTest {

    private ApiService mApiService;

    private ApiServiceImpl mApiServiceImpl;

    private MeetingCreatorActivity mMeetingCreatorActivity;
    private ArrayList<Meeting> mMeetingsList;
    private ArrayList<String> mFilterList;

    @Before
    public void setup() {
        mApiService = DI.getApiService();
        mApiServiceImpl = new ApiServiceImpl();
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
//        ArrayList<Meeting> testList = new ArrayList<>();
//        assertEquals(0,testList.size());
//
//        assertEquals(0,mApiServiceImpl.mMeetingsList.size());
////        mApiServiceImpl.addMeeting(meetingA());
//        mApiService.addMeeting(meetingA());
//
//
//        assertEquals(1,mApiServiceImpl.mMeetingsList.size());
//
////        testList.addAll(mApiServiceImpl.getMeetingsList(mFilterList));
////        assertEquals(1,testList.size());
    }

    @Test
    public void getMembersReminderList() {
        ArrayList membersReminderList = new ArrayList();
        int membersCount = mApiService.getMembersReminderList().size();
//        assertEquals(6, membersCount);
        assertTrue(membersReminderList.isEmpty());
        membersReminderList.addAll(mApiService.getMembersReminderList());
        assertEquals(membersCount,membersReminderList.size());
    }

    @Test
    public void addMemberEmailToReminderList() {
        ArrayList membersReminderList = new ArrayList();
        int membersCount = mApiService.getMembersReminderList().size();
        membersReminderList.addAll(mApiService.getMembersReminderList());
        assertEquals(membersCount,membersReminderList.size());

        mApiService.addMemberEmailToReminderList("test@test.com");
        membersReminderList.clear();
        membersReminderList.addAll(mApiService.getMembersReminderList());
        assertEquals((membersCount + 1),membersReminderList.size());
    }

    @Test
    public void getMeetingRoomsList() {
        ArrayList meetingRoomsList = new ArrayList();
        int meetingRoomsCount = mApiService.getMeetingRoomsList().size();
//        assertEquals(10, meetingRoomsCount);
        assertTrue(meetingRoomsList.isEmpty());
        meetingRoomsList.addAll(mApiService.getMeetingRoomsList());
        assertEquals(meetingRoomsCount,meetingRoomsList.size());
    }

    @Test
    public void addMeeting() {
//        assertEquals(0,mApiService.getMeetingsList(new ArrayList<String>()).size());
//        mApiService.addMeeting(meetingA());
//
//
//        int count = mApiServiceImpl.mMeetingsList.size();
//        assertEquals(1, count);
////                assertEquals(1,mApiService.getMeetingsList(new ArrayList<String>()).size());

    }

    @Test
    public void removeMeeting() {
    }

    @Test
    public void checkIfNoDuplicationMeeting() {
    }

    private Meeting meetingA() {
        Meeting mMeetingA = new Meeting("MeetingA","Peach",initCalendar(),initCalendar(), new ArrayList<String>());
        mMeetingA.setDate(mMeetingCreatorActivity.setDate(mMeetingA,2030,0,1));
        mMeetingA.setDate(mMeetingCreatorActivity.setTime(mMeetingA,13,00));
        mMeetingA.setDate(mMeetingCreatorActivity.setDuration(0,45));
        return mMeetingA;
    }

    private Meeting meetingB() {
        Meeting mMeetingB = new Meeting("MeetingB","Peach",initCalendar(),initCalendar(), new ArrayList<String>());
        mMeetingB.setDate(mMeetingCreatorActivity.setDate(mMeetingB,2030,0,1));
        mMeetingB.setDate(mMeetingCreatorActivity.setTime(mMeetingB,13,45));
        mMeetingB.setDate(mMeetingCreatorActivity.setDuration(0,45));
        return mMeetingB;
    }

    private Meeting meetingC() {
        Meeting mMeetingC = new Meeting("MeetingC","Mario",initCalendar(),initCalendar(), new ArrayList<String>());
        mMeetingC.setDate(mMeetingCreatorActivity.setDate(mMeetingC,2030,0,1));
        mMeetingC.setDate(mMeetingCreatorActivity.setTime(mMeetingC,13,00));
        mMeetingC.setDate(mMeetingCreatorActivity.setDuration(0,45));
        return mMeetingC;
    }
}