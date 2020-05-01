package fr.ace.mareu.ui;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class MeetingCreatorActivityTest {
    @Rule
    public ActivityTestRule<MeetingCreatorActivity> mActivityTestRule =
            new ActivityTestRule<>(MeetingCreatorActivity.class);

}