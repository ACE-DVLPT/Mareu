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

import fr.ace.mareu.R;
import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.utils.DeleteViewAction;
import fr.ace.mareu.utils.RecyclerViewItemCountViewAssertion;

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
                .check(matches(withText("Aucune réunion planifiée")));

    }

    /**
     * Test when a delete button is clicked, the item should be no longer displayed
     */
    @Test
    public void recyclerView_whenDeleteButtonIsClicked_itemShouldBeRemoved(){

        Meeting meeting = new Meeting(
                "topic",
                "place",
                "01/01/2020",
                "20h00",
                "1h",
                new ArrayList<>(Arrays.asList(
                        ("email")
                ))
        );

        mActivityTestRule.getActivity().mMeetingsList.add(meeting);
        refreshRecycleViewAdapter();

        onView(allOf(ViewMatchers.withId(R.id.recyclerview_meetings_list), isDisplayed()))
                .check(RecyclerViewItemCountViewAssertion.withItemCount(1));

        onView(allOf(ViewMatchers.withId(R.id.recyclerview_meetings_list), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));

        onView(withId(R.id.activity_meetings_list_txt_empty_view))
                .check(matches(withText("Aucune réunion planifiée")));

    }

}