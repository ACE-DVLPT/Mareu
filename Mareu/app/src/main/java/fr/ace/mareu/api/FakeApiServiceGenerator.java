package fr.ace.mareu.api;

import java.util.ArrayList;
import java.util.Arrays;

import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;
import fr.ace.mareu.model.Member;

public abstract class FakeApiServiceGenerator {

    public static ArrayList<Member> fakeMembersReminderList = (ArrayList<Member>) Arrays.asList(
            new Member("maxime@lamzone.com"),
            new Member("alex@lamzone.com"),
            new Member("paul@lamzone.com"),
            new Member("viviane@lamzone.com"),
            new Member("amandine@lamzone.com"),
            new Member("luc@lamzone.com")
    );

    public static ArrayList<MeetingRoom> fakeMeetingRoomsList = (ArrayList<MeetingRoom>) Arrays.asList(
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

    public static ArrayList<Meeting> fakeMeetingsList = (ArrayList<Meeting>) Arrays.asList(
            new Meeting(
                    "Réunion A",
                    "Peach",
                    "14h00",
                    fakeMembersReminderList
            ),
            new Meeting(
                    "Réunion B",
                            "Mario",
                            "16h00",
                    fakeMembersReminderList
            ),
            new Meeting(
                    "Réunion C",
                    "Luigi",
                    "19h00",
                    fakeMembersReminderList
            )
    );
}
