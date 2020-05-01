package fr.ace.mareu;

import org.junit.Test;

import fr.ace.mareu.ui.MeetingCreatorActivity;

import static org.junit.Assert.*;


public class UnitTest {

    MeetingCreatorActivity meetingCreatorActivity = new MeetingCreatorActivity();

    @Test
    public void checkEmailStructure() {
        String validEmail = "validemail@email.com";
        String invalidEmail = "invalidEmail";
        assertTrue(meetingCreatorActivity.checkEmailStructure(validEmail));
        assertFalse(meetingCreatorActivity.checkEmailStructure(invalidEmail));
    }


}