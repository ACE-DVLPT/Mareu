package fr.ace.mareu.utils;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import org.hamcrest.Matchers;
import org.junit.Assert;

public class RecyclerViewItemCountViewAssertion implements ViewAssertion {
    private final org.hamcrest.Matcher<Integer> mMatcher;

    public static RecyclerViewItemCountViewAssertion withItemCount(int expectedCount) {
        return withItemCount(Matchers.is(expectedCount));
    }

    public static RecyclerViewItemCountViewAssertion withItemCount(org.hamcrest.Matcher<Integer> matcher) {
        return new RecyclerViewItemCountViewAssertion(matcher);
    }

    private RecyclerViewItemCountViewAssertion(org.hamcrest.Matcher<Integer> matcher) {
        this.mMatcher = matcher;
    }


    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        Assert.assertThat(adapter.getItemCount(), mMatcher);
    }
}
