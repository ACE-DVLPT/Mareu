package fr.ace.mareu.ui;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.ace.mareu.R;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;


@RunWith(AndroidJUnit4.class)
public class MeetingCreatorActivityTest {
    @Rule
    public ActivityTestRule<MeetingCreatorActivity> mActivityTestRule =
            new ActivityTestRule<>(MeetingCreatorActivity.class,true,false);

    // Start activity
    @Before
    public void setUp(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        mActivityTestRule.launchActivity(intent);
    }

    @Test
    public void activity_whenActivityStarted_toolbarMustDisplayCorrectTitle(){
        ViewInteraction textView = onView(
                allOf(withText("Création d'une Réunion"),
                        childAtPosition(
                                allOf(withId(R.id.activity_meeting_creator_button_toolbar),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Création d'une Réunion")));
    }

    @Test
    public void activity_whenUserCompleteFields_thenDisplayedResultMustBeMatch(){
        // Fields completion
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.activity_meeting_creator_editText_topic), childAtPosition(childAtPosition(withClassName(is("android.widget.LinearLayout")), 0), 1)));
        appCompatEditText.perform(scrollTo(), replaceText("Meeting"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(allOf(withId(R.id.activity_meeting_creator_spinner_place), childAtPosition(childAtPosition(withClassName(is("android.widget.LinearLayout")), 0), 3)));
        appCompatSpinner.perform(scrollTo(), click());

        DataInteraction appCompatCheckedTextView = onData(anything()).inAdapterView(childAtPosition(withClassName(is("android.widget.PopupWindow$PopupBackgroundView")), 0)).atPosition(2);
        appCompatCheckedTextView.perform(click());

        ViewInteraction materialTextView = onView(allOf(withId(R.id.activity_meeting_creator_text_view_date), childAtPosition(childAtPosition(withClassName(is("android.widget.LinearLayout")), 0), 5)));
        materialTextView.perform(scrollTo(), click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 5, 1));
        onView(withId(android.R.id.button1)).perform(click());

        ViewInteraction materialTextView2 = onView(allOf(withId(R.id.activity_meeting_creator_text_view_hour), childAtPosition(childAtPosition(withClassName(is("android.widget.LinearLayout")), 0), 7)));
        materialTextView2.perform(scrollTo(), click());

        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(20, 30));
        onView(withId(android.R.id.button1)).perform(click());

        ViewInteraction materialTextView3 = onView(allOf(withId(R.id.activity_meeting_creator_text_view_duration), childAtPosition(childAtPosition(withClassName(is("android.widget.LinearLayout")), 0), 9)));
        materialTextView3.perform(scrollTo(), click());

        ViewInteraction materialButton3 = onView(allOf(withId(android.R.id.button1), withText("OK"), childAtPosition(childAtPosition(withId(R.id.buttonPanel), 0), 3)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction appCompatMultiAutoCompleteTextView = onView(allOf(withId(R.id.activity_meeting_creator_multi_auto_complete_text_view_members), childAtPosition(childAtPosition(withId(R.id.activity_meeting_creator_zone_add_members), 0), 0), isDisplayed()));
        appCompatMultiAutoCompleteTextView.perform(replaceText("alex@lamzone.com"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(allOf(withId(R.id.activity_meeting_creator_button_add_email), childAtPosition(allOf(withId(R.id.activity_meeting_creator_zone_add_members), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 1)), 1)));
        appCompatImageButton.perform(scrollTo(), click());

        // Test
        onView(AllOf.allOf(ViewMatchers.withId(R.id.activity_meeting_creator_editText_topic), isDisplayed()))
                .check(matches(withText("Meeting")));

        onView(AllOf.allOf(ViewMatchers.withId(R.id.activity_meeting_creator_spinner_place)))
                .check(matches(withSpinnerText(containsString("Mario"))));

//        onView(AllOf.allOf(ViewMatchers.withId(R.id.activity_meeting_creator_text_view_date), isDisplayed()))
//                .check(matches(withText("vendredi 1 mai")));

        onView(AllOf.allOf(ViewMatchers.withId(R.id.activity_meeting_creator_text_view_hour), isDisplayed()))
                .check(matches(withText("20h30")));

        onView(AllOf.allOf(ViewMatchers.withId(R.id.activity_meeting_creator_text_view_duration), isDisplayed()))
                .check(matches(withText("45min")));

        ViewInteraction viewGroup = onView(
                allOf(withId(R.id.activity_meeting_creator_chip_group_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_meeting_creator_zone_add_members),
                                        0),
                                1),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));
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