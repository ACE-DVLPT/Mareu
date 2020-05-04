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

    private MeetingCreatorActivity mMeetingCreatorActivity;
    private ArrayList<Meeting> mMeetingsList;
    private ArrayList<String> mFilterList;

    @Before
    public void setup() {
        mApiService = DI.getApiService();
        mMeetingCreatorActivity = new MeetingCreatorActivity();
        mMeetingsList = new ArrayList<>();
        mFilterList = new ArrayList<>();
        for(int i = 0 ; i < mApiService.getMeetingsList(new ArrayList<String>()).size() ; i++){
            mApiService.removeMeeting(
                    mApiService.getMeetingsList(new ArrayList<String>()).get(i)
            );
        }
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
        ArrayList<Meeting> testList = new ArrayList<>();
        assertEquals(0,testList.size());

        // Add meetings to the API
        mApiService.addMeeting(meetingA());
        mApiService.addMeeting(meetingB());
        mApiService.addMeeting(meetingC());

        // getMeetingsList from the API
        testList.addAll(mApiService.getMeetingsList(mFilterList));
        assertEquals(3,testList.size());

        // Filter by place = "Peach"
        mFilterList.add("Peach");
        testList.clear();
        testList.addAll(mApiService.getMeetingsList(mFilterList));
        assertEquals(2,testList.size());

        // Filter by place = "Peach" + "mardi 1 janvier 2030"
        mFilterList.add("mardi 1 janvier 2030");
        testList.clear();
        testList.addAll(mApiService.getMeetingsList(mFilterList));
        assertEquals(1,testList.size());
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
        assertEquals(0,mApiService.getMeetingsList(mFilterList).size());

        // Add meeting to the API + Test
        mApiService.addMeeting(meetingA());
        assertEquals(1,mApiService.getMeetingsList(mFilterList).size());
    }

    @Test
    public void removeMeeting() {
        Meeting meeting = meetingA();
        assertEquals(0,mApiService.getMeetingsList(mFilterList).size());

        // Add meeting to the API
        mApiService.addMeeting(meeting);
        assertEquals(1,mApiService.getMeetingsList(mFilterList).size());

        // Remove meeting + Test
        mApiService.removeMeeting(meeting);
        assertEquals(0,mApiService.getMeetingsList(mFilterList).size());
    }

    @Test
    public void checkIfNoDuplicationMeeting() {
        Meeting meetingA = meetingA();
        Meeting meetingX = meetingX();

        for(int i = 0 ; i < mApiService.getMeetingsList(new ArrayList<String>()).size() ; i++){
            mApiService.removeMeeting(
                    mApiService.getMeetingsList(new ArrayList<String>()).get(i)
            );
        }

        assertEquals(0,mApiService.getMeetingsList(mFilterList).size());

        // Add reference meeting -> the meeting starts at 13h00 and lasts 45 minutes
        mApiService.addMeeting(meetingA);
        assertEquals(1,mApiService.getMeetingsList(mFilterList).size());

        // meetingX is the same meeting as the meetingA
        assertFalse(mApiService.checkIfNoDuplicationMeeting(meetingX));

        // meetingX start before the  meetingA and finished during the meetingA
        meetingX.setDate(mMeetingCreatorActivity.setTime(meetingX,12,30));
        assertFalse(mApiService.checkIfNoDuplicationMeeting(meetingX));

        // meetingX start during the meetingA and finished after the meetingA
        meetingX.setDate(mMeetingCreatorActivity.setTime(meetingX,13,30));
        assertFalse(mApiService.checkIfNoDuplicationMeeting(meetingX));

        // meetingX start before the meetingA and finished after the meetingA
        meetingX.setDate(mMeetingCreatorActivity.setTime(meetingX,12,30));
        meetingX.setDuration(mMeetingCreatorActivity.setTime(meetingX,2,0));
        assertFalse(mApiService.checkIfNoDuplicationMeeting(meetingX));

        // meetingX start during the meetingA and finished before the meetingA
        meetingX.setDate(mMeetingCreatorActivity.setTime(meetingX,13,15));
        meetingX.setDuration(mMeetingCreatorActivity.setTime(meetingX,0,15));
        assertFalse(mApiService.checkIfNoDuplicationMeeting(meetingX));

        // meetingX start in the same time of the meetingA
        meetingX.setDate(mMeetingCreatorActivity.setTime(meetingX,13,0));
        meetingX.setDuration(mMeetingCreatorActivity.setTime(meetingX,1,0));
        assertFalse(mApiService.checkIfNoDuplicationMeeting(meetingX));

        // meetingX finished in the same time of the meetingA
        meetingX.setDate(mMeetingCreatorActivity.setTime(meetingX,12,45));
        meetingX.setDuration(mMeetingCreatorActivity.setTime(meetingX,1,0));
        assertFalse(mApiService.checkIfNoDuplicationMeeting(meetingX));

        // meetingX finished when meetingA start
        meetingX.setDate(mMeetingCreatorActivity.setTime(meetingX,12,15));
        meetingX.setDuration(mMeetingCreatorActivity.setTime(meetingX,0,45));
        assertTrue(mApiService.checkIfNoDuplicationMeeting(meetingX));

        // meetingX start when meetingA finished
        meetingX.setDate(mMeetingCreatorActivity.setTime(meetingX,12,15));
        meetingX.setDuration(mMeetingCreatorActivity.setTime(meetingX,0,45));
        assertTrue(mApiService.checkIfNoDuplicationMeeting(meetingX));
    }

    private Meeting meetingA() {
        Meeting mMeetingA = new Meeting("MeetingA","Peach",initCalendar(),initCalendar(), new ArrayList<String>());
        mMeetingA.setDate(mMeetingCreatorActivity.setDate(mMeetingA,2030,0,1));
        mMeetingA.setDate(mMeetingCreatorActivity.setTime(mMeetingA,13,00));
        mMeetingA.setDuration(mMeetingCreatorActivity.setDuration(0,45));
        return mMeetingA;
    }

    private Meeting meetingB() {
        Meeting mMeetingB = new Meeting("MeetingB","Peach",initCalendar(),initCalendar(), new ArrayList<String>());
        mMeetingB.setDate(mMeetingCreatorActivity.setDate(mMeetingB,2030,1,1));
        mMeetingB.setDate(mMeetingCreatorActivity.setTime(mMeetingB,13,45));
        mMeetingB.setDuration(mMeetingCreatorActivity.setDuration(0,45));
        return mMeetingB;
    }

    private Meeting meetingC() {
        Meeting mMeetingC = new Meeting("MeetingC","Mario",initCalendar(),initCalendar(), new ArrayList<String>());
        mMeetingC.setDate(mMeetingCreatorActivity.setDate(mMeetingC,2030,0,1));
        mMeetingC.setDate(mMeetingCreatorActivity.setTime(mMeetingC,13,00));
        mMeetingC.setDuration(mMeetingCreatorActivity.setDuration(0,45));
        return mMeetingC;
    }

    private Meeting meetingX() {
        Meeting mMeetingX = new Meeting("MeetingX","Peach",initCalendar(),initCalendar(), new ArrayList<String>());
        mMeetingX.setDate(mMeetingCreatorActivity.setDate(mMeetingX,2030,0,1));
        mMeetingX.setDate(mMeetingCreatorActivity.setTime(mMeetingX,13,00));
        mMeetingX.setDuration(mMeetingCreatorActivity.setDuration(0,45));
        return mMeetingX;
    }
}