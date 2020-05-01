package fr.ace.mareu.ui;

import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import fr.ace.mareu.R;
import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.utils.DeleteViewAction;
import fr.ace.mareu.utils.RecyclerViewItemCountViewAssertion;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
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

    @Test
    public void recyclerView_whenIsEmpty_displayTheRightMessage() {

        onView(withId(R.id.activity_meetings_list_txt_empty_view))
                .check(matches(withText("Aucune réunion disponible")));

    }

    @Test
    public void filterMenu_whenPlaceFilterSelected_theCorrectDialogMustBeDisplayed(){
        ViewInteraction actionMenuItemView = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.activity_meetings_list_toolbar),
                                1),
                        0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction materialTextView = onView(
                Matchers.allOf(withId(R.id.title), withText("Filtre par salle"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        // test
        onView(allOf(ViewMatchers.withId(R.id.fragment_dialog_filter_place_spinner_place))).check(matches(isDisplayed()));
    }

    @Test
    public void filterMenu_whenDateFilterSelected_theCorrectDialogMustBeDisplayed(){
        ViewInteraction actionMenuItemView = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.activity_meetings_list_toolbar),
                                1),
                        0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction materialTextView = onView(
                Matchers.allOf(withId(R.id.title), withText("Filtre par date"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        // test
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(matches(isDisplayed()));
    }

    @Test
    public void filterMenu_whenFilterIsAdded_TheChipGroupMustBeDisplayed(){
        // Before does not exist
        ViewInteraction viewGroup = onView(
                Matchers.allOf(withId(R.id.activity_meetings_list_chip_group_filters),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        viewGroup.check(doesNotExist());

        // Add chip group
        ViewInteraction actionMenuItemView = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.activity_meetings_list_toolbar),
                                1),
                        0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction materialTextView = onView(
                Matchers.allOf(withId(R.id.title), withText("Filtre par salle"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialButton = onView(
                Matchers.allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        // Chip group is displayed
        viewGroup.check(matches(isDisplayed()));
    }

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

    @Test
    public void landScapeView_whenMeetingIsClicked_memberEmailsMustBeDisplayed(){
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
        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(allOf(withId(R.id.recyclerview_meetings_list),isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click() ));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         // test
        onView(allOf(ViewMatchers.withId(R.id.fragment_member_list_text_view), isDisplayed()))
                .check(matches(withText("email@email.com")));
    }

    @Test
    public void activity_whenAddFloatingButtonClicked_startMeetingCreatorActivity(){
        Intents.init();

        onView(allOf(withId(R.id.activity_meetings_list_btn_add_meeting),isDisplayed()))
                .perform(click());

        // Ensure that the correct activity has started
        intended(hasComponent(MeetingCreatorActivity.class.getName()));

        Intents.release();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}