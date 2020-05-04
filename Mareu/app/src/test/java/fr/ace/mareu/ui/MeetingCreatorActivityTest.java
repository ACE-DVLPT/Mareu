package fr.ace.mareu.ui;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Calendar;

import fr.ace.mareu.model.Meeting;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MeetingCreatorActivityTest {

    private MeetingCreatorActivity mMeetingCreatorActivity;
    private Meeting mMeeting;
    private Calendar mCalendar;

    @Before
    public void setup() {
        initCalendar();
        mMeetingCreatorActivity = new MeetingCreatorActivity();
        mMeeting = new Meeting("Meeting","Peach",mCalendar,mCalendar,new ArrayList<String>());
    }

    public void initCalendar(){
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, 0);
        mCalendar.set(Calendar.MONTH, 0);
        mCalendar.set(Calendar.DAY_OF_MONTH, 0);
        mCalendar.set(Calendar.HOUR, 0);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * check that the method detects the structure of an email address
     */
    @Test
    public void checkEmailStructure() {
        String validEmail = "validemail@email.com";
        String invalidEmail = "invalidEmail";
        assertTrue(mMeetingCreatorActivity.checkEmailStructure(validEmail));
        assertFalse(mMeetingCreatorActivity.checkEmailStructure(invalidEmail));
    }

    /**
     * Check if the setDate() method have the right behavior
     */
    @Test
    public void setDate() {
        // init the meeting
        initCalendar();
        mCalendar.set(Calendar.HOUR_OF_DAY, 13);
        mCalendar.set(Calendar.MINUTE, 30);
        mMeeting.setDate(mCalendar);
        assertFalse(mMeeting.getStringDate().equals("mercredi 1 janvier 2020"));
        assertTrue(mMeeting.getStringStartTime().equals("13h30"));

        // set the meeting date with the tested method
        // the start time of the meeting must not have changed
        mMeeting.setDate(mMeetingCreatorActivity.setDate(mMeeting,2020,0,1));
        assertTrue(mMeeting.getStringDate().equals("mercredi 1 janvier 2020"));
        assertTrue(mMeeting.getStringStartTime().equals("13h30"));
    }

    /**
     * Check if the setTime() method have the right behavior
     */
    @Test
    public void setTime() {
        // init the meeting
        initCalendar();
        mCalendar.set(Calendar.YEAR, 2020);
        mCalendar.set(Calendar.MONTH, 0);
        mCalendar.set(Calendar.DAY_OF_MONTH, 2);
        mMeeting.setDate(mCalendar);
        assertTrue(mMeeting.getStringDate().equals("jeudi 2 janvier 2020"));
        assertFalse(mMeeting.getStringStartTime().equals("14h45"));

        // set the meeting start time with the tested method
        // the date of the meeting must not have changed
        mMeeting.setDate(mMeetingCreatorActivity.setTime(mMeeting,14,45));
        assertTrue(mMeeting.getStringDate().equals("jeudi 2 janvier 2020"));
        assertTrue(mMeeting.getStringStartTime().equals("14h45"));
    }

    /**
     * Check if the setDuration() method have the right behavior
     */
    @Test
    public void setDuration() {
        // init the meeting
        initCalendar();
        mMeeting.setDate(mCalendar);
        assertFalse(mMeeting.getStringDuration().equals("2h45"));

        // set the meeting duration with the tested method
        mMeeting.setDuration(mMeetingCreatorActivity.setDuration(2,45));
        assertTrue(mMeeting.getStringDuration().equals("2h45"));
    }
}