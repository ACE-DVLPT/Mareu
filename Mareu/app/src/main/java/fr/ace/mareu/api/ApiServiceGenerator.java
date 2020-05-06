package fr.ace.mareu.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ace.mareu.model.Meeting;

/**
 * Data used in the API mock
 */
public abstract class ApiServiceGenerator {

    /**
     * List of email member
     */
    public static List<String> fakeMembersReminderList = Arrays.asList(
            ("maxime@lamzone.com"),
            ("alex@lamzone.com"),
            ("paul@lamzone.com"),
            ("viviane@lamzone.com"),
            ("amandine@lamzone.com"),
            ("luc@lamzone.com")
    );

    /**
     * List of meeting rooms
     */
    public static List<String> fakeMeetingRoomsList = Arrays.asList(
            ("Peach"),
            ("Mario"),
            ("Luigi"),
            ("Toad"),
            ("Yoshi"),
            ("Daisy"),
            ("Kong"),
            ("Bowser"),
            ("Wario"),
            ("Koopa")
    );

    /**
     * List of meetings displayed when opening the application
     */
    public static List<Meeting> fakeMeetingsList =  Arrays.asList(

    );

    /**
     * @return fakeMembersReminderList -> an {@link ArrayList} of {@link String}
     */
    public static ArrayList<String> generateMembersReminderList() {
        return new ArrayList<>(fakeMembersReminderList);
    }

    /**
     * @return fakeMeetingRoomsList -> an {@link ArrayList} of {@link String}
     */
    public static ArrayList<String> generateMeetingRoomsList() {
        return new ArrayList<>(fakeMeetingRoomsList);
    }

    /**
     * @return fakeMeetingsList -> an {@link ArrayList} of {@link Meeting}
     */
    public static ArrayList<Meeting> generateMeetingsList() {
        return new ArrayList<>(fakeMeetingsList);
    }
}
