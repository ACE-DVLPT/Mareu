package fr.ace.mareu.ui;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import fr.ace.mareu.R;
import fr.ace.mareu.api.ApiService;
import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.utils.DeleteViewAction;
import fr.ace.mareu.utils.RecyclerViewItemCountViewAssertion;
import fr.ace.mareu.utils.di.DI;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class MeetingsListActivityTest {

    @Rule
    public ActivityTestRule<MeetingsListActivity> mActivityTestRule =
            new ActivityTestRule<>(MeetingsListActivity.class);

    @Before
    public void setUp() {
        mActivityTestRule.getActivity().mMeetingsList.clear();
        refreshRecycleViewAdapter();
    }

    /**
     * To run "refreshRecycleViewAdapter" method with the right thread
     */
    public void refreshRecycleViewAdapter(){
        try {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mActivityTestRule.getActivity().initList();
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Test if the right message is displayed when the recyclerView is empty
     */
    @Test
    public void recyclerView_whenIsEmpty_displayTheRightMessage() {

        onView(withId(R.id.activity_meetings_list_txt_empty_view))
                .check(matches(withText("Aucune réunion disponible")));

    }

    /**
     * Test when a delete button is clicked, the item should be no longer displayed
     */
    @Test
    public void recyclerView_whenDeleteButtonIsClicked_itemShouldBeRemoved(){

        Calendar meetingDATE = Calendar.getInstance();
        Calendar meetingDuration = Calendar.getInstance();
        meetingDuration.set(Calendar.YEAR, 0);
        meetingDuration.set(Calendar.MONTH, 0);
        meetingDuration.set(Calendar.DAY_OF_MONTH, 0);
        meetingDuration.set(Calendar.HOUR, 1);
        meetingDuration.set(Calendar.HOUR_OF_DAY, 0);
        meetingDuration.set(Calendar.MINUTE, 45);
        meetingDuration.set(Calendar.SECOND, 0);
        meetingDuration.set(Calendar.MILLISECOND, 0);

        Meeting meeting = new Meeting(
                "topic",
                "Peach",
                meetingDATE,
                meetingDuration,
                new ArrayList<>(Arrays.asList(
                        ("email@email.com")
                ))
        );

        mActivityTestRule.getActivity().mApiService.addMeeting(meeting);
        refreshRecycleViewAdapter();

        onView(allOf(ViewMatchers.withId(R.id.recyclerview_meetings_list), isDisplayed()))
                .check(RecyclerViewItemCountViewAssertion.withItemCount(1));

        onView(allOf(ViewMatchers.withId(R.id.recyclerview_meetings_list), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));

        onView(allOf(ViewMatchers.withId(R.id.recyclerview_meetings_list), isDisplayed()))
                .check(RecyclerViewItemCountViewAssertion.withItemCount(0));

    }

}