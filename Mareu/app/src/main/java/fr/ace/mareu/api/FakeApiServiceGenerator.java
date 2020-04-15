package fr.ace.mareu.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;

public abstract class FakeApiServiceGenerator {

    public static List<String> fakeMembersReminderList = Arrays.asList(
            ("maxime@lamzone.com"),
            ("alex@lamzone.com"),
            ("paul@lamzone.com"),
            ("viviane@lamzone.com"),
            ("amandine@lamzone.com"),
            ("luc@lamzone.com")
    );

    public static List<MeetingRoom> fakeMeetingRoomsList = Arrays.asList(
            new MeetingRoom("Peach", "blue"),
            new MeetingRoom("Mario", "blue"),
            new MeetingRoom("Luigi", "blue"),
            new MeetingRoom("Toad", "blue"),
            new MeetingRoom("Yoshi", "blue"),
            new MeetingRoom("Daisy", "blue"),
            new MeetingRoom("Kong", "blue"),
            new MeetingRoom("Bowser", "blue"),
            new MeetingRoom("Wario", "blue"),
            new MeetingRoom("Koopa", "blue")
    );

    public static List<Meeting> fakeMeetingsList =  Arrays.asList(

            new Meeting(
                    "Réunion A",
                    "Peach",
                    "01/02/2021",
                    "14h00",
                    "1h",
                    new ArrayList<>(Arrays.asList(
                            ("maxime@lamzone.com"),
                            ("alex@lamzone.com")
                    ))
            ),
            new Meeting(
                    "Réunion B",
                    "Mario",
                    "01/03/2021",
                    "16h00",
                    "1h",
                    new ArrayList<>(Arrays.asList(
                            ("paul@lamzone.com"),
                            ("viviane@lamzone.com")
                    ))
            ),
            new Meeting(
                    "Réunion C",
                    "Luigi",
                    "01/04/2021",
                    "19h00",
                    "1h30",
                    new ArrayList<>(Arrays.asList(
                            ("amandine@lamzone.com"),
                            ("luc@lamzone.com")
                    ))
            )
    );

    public static ArrayList<String> generateMembersReminderList() {
        return new ArrayList<>(fakeMembersReminderList);
    }

    public static ArrayList<MeetingRoom> generateMeetingRoomsList() {
        return new ArrayList<>(fakeMeetingRoomsList);
    }

    public static ArrayList<Meeting> generateMeetingsList() {
        return new ArrayList<>(fakeMeetingsList);
    }
}
