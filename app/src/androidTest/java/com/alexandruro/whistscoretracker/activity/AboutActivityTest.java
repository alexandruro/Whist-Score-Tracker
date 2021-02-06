package com.alexandruro.whistscoretracker.activity;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.alexandruro.whistscoretracker.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class AboutActivityTest {

    @Rule
    public ActivityScenarioRule<AboutActivity> mActivityScenarioRule = new ActivityScenarioRule<>(AboutActivity.class);

    @Test
    public void test() {
        onView(withId(R.id.textViewAbout))
                .check(matches(allOf(withText(R.string.about_text), isDisplayed())));
    }

}