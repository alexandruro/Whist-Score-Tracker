package com.alexandruro.whistscoretracker;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import com.alexandruro.whistscoretracker.R;
import com.alexandruro.whistscoretracker.activity.MainMenuActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBackUnconditionally;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
public class E2ETest {

    @Rule
    public ActivityScenarioRule<MainMenuActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainMenuActivity.class);

    @Test
    public void e2ETest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.buttonNewGame), withText("New Game"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.radioGameTypeOneEightOne), withText("1..8..1"),
                        childAtPosition(
                                allOf(withId(R.id.radioGroup),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0)));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.radioPrize5), withText("+/- 5"),
                        childAtPosition(
                                allOf(withId(R.id.radioGroup2),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                4)),
                                1)));
        appCompatRadioButton2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                withParent(withId(R.id.nameListView)),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("One"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.buttonAdd),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatImageButton2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.name),
                        withParent(
                                childAtPosition(
                                        withId(R.id.nameListView),
                                1))));
        appCompatEditText2.perform(scrollTo(), replaceText("Two"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.buttonAdd),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatImageButton3.perform(scrollTo(), click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.name),
                        withParent(
                                childAtPosition(
                                        withId(R.id.nameListView),
                                2))));
        appCompatEditText3.perform(scrollTo(), replaceText("Three"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.buttonAdd),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatImageButton4.perform(scrollTo(), click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.name),
                        withParent(
                                childAtPosition(
                                        withId(R.id.nameListView),
                                3))));
        appCompatEditText4.perform(scrollTo(), replaceText("Four"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.buttonAdd),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatImageButton5.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withId(R.id.remove),
                        isDisplayed(),
                        childAtPosition(
                                withParent(withId(R.id.nameListView)),
                                1)));
        appCompatImageButton6.perform(scrollTo(), click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.buttonStart), withText("Start game"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.currentRound), withText("1 hand"),
                        withParent(withParent(withId(R.id.bottom_sheet)))));
        textView.check(matches(withText("1 hand")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.firstPlayer), withText("First player: One"),
                        withParent(withParent(withId(R.id.bottom_sheet)))));
        textView2.check(matches(withText("First player: One")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.dealer), withText("Dealer: Four"),
                        withParent(withParent(withId(R.id.bottom_sheet)))));
        textView3.check(matches(withText("Dealer: Four")));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction button = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction button2 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction button3 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button3.perform(click());

        ViewInteraction button4 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button4.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.roundNumber), withText("1"),
                        withParent(allOf(withId(R.id.scoreRow),
                                withParent(withId(R.id.tableBody)))),
                        isDisplayed()));
        textView4.check(matches(withText("1")));

        ViewInteraction textView5 = onView(
                allOf(withText("0"),
                        childAtPosition(withId(R.id.scoreRow), getViewIndexOfBet(0)),
                        withParent(allOf(withId(R.id.scoreRow),
                                withParent(withId(R.id.tableBody)))),
                        isDisplayed()));
        textView5.check(matches(withText("0")));

        ViewInteraction textView6 = onView(
                allOf(withText("1"),
                        childAtPosition(withId(R.id.scoreRow), getViewIndexOfBet(1)),
                        withParent(allOf(withId(R.id.scoreRow),
                                withParent(withId(R.id.tableBody)))),
                        isDisplayed()));
        textView6.check(matches(withText("1")));

        onView(
                allOf(withText("0"),
                        childAtPosition(withId(R.id.scoreRow), getViewIndexOfBet(2)),
                        withParent(allOf(withId(R.id.scoreRow),
                                withParent(withId(R.id.tableBody)))),
                        isDisplayed()))
                .check(matches(withText("0")));

        onView(
                allOf(withText("1"),
                        childAtPosition(withId(R.id.scoreRow), getViewIndexOfBet(3)),
                        withParent(allOf(withId(R.id.scoreRow),
                                withParent(withId(R.id.tableBody)))),
                        isDisplayed()))
                .check(matches(withText("1")));

        ViewInteraction textView7 = onView(
                allOf(withText("One"),
                        withParent(allOf(withId(R.id.header),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.TableRow.class)))),
                        isDisplayed()));
        textView7.check(matches(withText("One")));

        ViewInteraction textView8 = onView(
                allOf(withText("Two"),
                        withParent(allOf(withId(R.id.header),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.TableRow.class)))),
                        isDisplayed()));
        textView8.check(matches(withText("Two")));

        ViewInteraction textView9 = onView(
                allOf(withText("Three"),
                        withParent(allOf(withId(R.id.header),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.TableRow.class)))),
                        isDisplayed()));
        textView9.check(matches(isDisplayed()));

        ViewInteraction textView10 = onView(
                allOf(withText("Four"),
                        withParent(allOf(withId(R.id.header),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.TableRow.class)))),
                        isDisplayed()));
        textView10.check(matches(isDisplayed()));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_undo), withContentDescription("Undo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Undo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.action_undo), withContentDescription("Undo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction button5 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button5.perform(click());

        ViewInteraction button6 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button6.perform(click());

        ViewInteraction button7 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button7.perform(click());

        ViewInteraction button8 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button8.perform(click());

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction button9 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button9.perform(click());

        ViewInteraction button10 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button10.perform(click());

        ViewInteraction button11 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button11.perform(click());

        ViewInteraction button12 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button12.perform(click());

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton4.perform(click());

        ViewInteraction button13 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button13.perform(click());

        ViewInteraction button14 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button14.perform(click());

        ViewInteraction button15 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button15.perform(click());

        ViewInteraction button16 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button16.perform(click());

        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton5.perform(click());

        ViewInteraction button17 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button17.perform(click());

        ViewInteraction button18 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button18.perform(click());

        ViewInteraction button19 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button19.perform(click());

        ViewInteraction button20 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button20.perform(click());

        ViewInteraction floatingActionButton6 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton6.perform(click());

        ViewInteraction button21 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button21.perform(click());

        ViewInteraction button22 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button22.perform(click());

        ViewInteraction button23 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button23.perform(click());

        ViewInteraction button24 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button24.perform(click());

        ViewInteraction floatingActionButton7 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton7.perform(click());

        ViewInteraction button25 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button25.perform(click());

        ViewInteraction button26 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button26.perform(click());

        ViewInteraction button27 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button27.perform(click());

        ViewInteraction button28 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button28.perform(click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.action_undo), withContentDescription("Undo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("Undo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction floatingActionButton8 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton8.perform(click());

        ViewInteraction button29 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button29.perform(click());

        ViewInteraction button30 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button30.perform(click());

        ViewInteraction button31 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button31.perform(click());

        ViewInteraction button32 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button32.perform(click());

        ViewInteraction floatingActionButton9 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton9.perform(click());

        ViewInteraction button33 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button33.perform(click());

        ViewInteraction button34 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button34.perform(click());

        ViewInteraction button35 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button35.perform(click());

        ViewInteraction button36 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button36.perform(click());

        ViewInteraction floatingActionButton10 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton10.perform(click());

        ViewInteraction button37 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button37.perform(click());

        ViewInteraction button38 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button38.perform(click());

        ViewInteraction button39 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button39.perform(click());

        ViewInteraction button40 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button40.perform(click());

        checkScore("15", 3, 0);
        checkScore("9", 3, 1);
        checkScore("8", 3, 2);
        checkScore("22", 3, 3);

        /////////////
        // Round 5 //
        /////////////

        ViewInteraction floatingActionButton11 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton11.perform(click());

        ViewInteraction button41 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button41.perform(click());

        ViewInteraction button42 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button42.perform(click());

        ViewInteraction button43 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button43.perform(click());

        ViewInteraction button44 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button44.perform(click());

        ViewInteraction floatingActionButton12 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton12.perform(click());

        ViewInteraction button45 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button45.perform(click());

        ViewInteraction button46 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button46.perform(click());

        ViewInteraction button47 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button47.perform(click());

        ViewInteraction button48 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button48.perform(click());

        checkScore("13", 4, 0);
        checkScore("7", 4, 1);
        checkScore("6", 4, 2);
        checkScore("29", 4, 3);

        /////////////
        // Round 6 //
        /////////////

        ViewInteraction floatingActionButton13 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton13.perform(click());

        ViewInteraction button49 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button49.perform(click());

        ViewInteraction button50 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button50.perform(click());

        ViewInteraction button51 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button51.perform(click());

        ViewInteraction button52 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button52.perform(click());

        ViewInteraction floatingActionButton14 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton14.perform(click());

        ViewInteraction button53 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button53.perform(click());

        ViewInteraction button54 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button54.perform(click());

        ViewInteraction button55 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button55.perform(click());

        ViewInteraction button56 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button56.perform(click());

        checkScore("18", 5, 0);
        checkScore("4", 5, 1);
        checkScore("4", 5, 2);
        checkScore("37", 5, 3);

        /////////////
        // Round 7 //
        /////////////

        ViewInteraction floatingActionButton15 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton15.perform(click());

        ViewInteraction button57 = onView(
                allOf(withText("4"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                4),
                        isDisplayed()));
        button57.perform(click());

        ViewInteraction button58 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button58.perform(click());

        ViewInteraction button59 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button59.perform(click());

        ViewInteraction button60 = onView(
                allOf(withText("4"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                4),
                        isDisplayed()));
        button60.perform(click());

        ViewInteraction floatingActionButton16 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton16.perform(click());

        ViewInteraction button61 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button61.perform(click());

        ViewInteraction button62 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button62.perform(click());

        ViewInteraction button63 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button63.perform(click());

        ViewInteraction button64 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button64.perform(click());

        /////////////
        // Round 8 //
        /////////////

        ViewInteraction floatingActionButton17 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton17.perform(click());

        ViewInteraction button65 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button65.perform(click());

        ViewInteraction button66 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button66.perform(click());

        ViewInteraction button67 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button67.perform(click());

        ViewInteraction button68 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button68.perform(click());

        ViewInteraction floatingActionButton18 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton18.perform(click());

        ViewInteraction button69 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button69.perform(click());

        ViewInteraction button70 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button70.perform(click());

        ViewInteraction button71 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button71.perform(click());

        ViewInteraction button72 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button72.perform(click());

        /////////////
        // Round 9 //
        /////////////

        ViewInteraction floatingActionButton19 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton19.perform(click());

        ViewInteraction button73 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button73.perform(click());

        ViewInteraction button74 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button74.perform(click());

        ViewInteraction button75 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button75.perform(click());

        ViewInteraction button76 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button76.perform(click());

        ViewInteraction floatingActionButton20 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton20.perform(click());

        ViewInteraction button77 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button77.perform(click());

        ViewInteraction button78 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button78.perform(click());

        ViewInteraction button79 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button79.perform(click());

        pressBackUnconditionally();

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button1), withText("Undo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction button80 = onView(
                allOf(withText("4"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                4),
                        isDisplayed()));
        button80.perform(click());

        ViewInteraction button81 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button81.perform(click());

        checkScore("35", 8, 0);
        checkScore("-6", 8, 1);
        checkScore("-11", 8, 2);
        checkScore("59", 8, 3);

        //////////////
        // Round 10 //
        //////////////

        ViewInteraction floatingActionButton21 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton21.perform(click());

        ViewInteraction button82 = onView(
                allOf(withText("7"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                7),
                        isDisplayed()));
        button82.perform(click());

        ViewInteraction button83 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button83.perform(click());

        ViewInteraction button84 = onView(
                allOf(withText("5"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                5),
                        isDisplayed()));
        button84.perform(click());

        ViewInteraction button85 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button85.perform(click());

        ViewInteraction floatingActionButton22 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton22.perform(click());

        ViewInteraction button86 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button86.perform(click());

        ViewInteraction button87 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button87.perform(click());

        ViewInteraction button88 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button88.perform(click());

        ViewInteraction button89 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button89.perform(click());

        ViewInteraction floatingActionButton23 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton23.perform(click());

        ViewInteraction button90 = onView(
                allOf(withText("7"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                7),
                        isDisplayed()));
        button90.perform(click());

        ViewInteraction button91 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button91.perform(click());

        ViewInteraction button92 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button92.perform(click());

        ViewInteraction button93 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button93.perform(click());

        ViewInteraction floatingActionButton24 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton24.perform(click());

        ViewInteraction button94 = onView(
                allOf(withText("7"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                7),
                        isDisplayed()));
        button94.perform(click());

        ViewInteraction button95 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button95.perform(click());

        ViewInteraction button96 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button96.perform(click());

        ViewInteraction button97 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button97.perform(click());

        ViewInteraction floatingActionButton25 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton25.perform(click());

        ViewInteraction button98 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button98.perform(click());

        ViewInteraction button99 = onView(
                allOf(withText("4"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                4),
                        isDisplayed()));
        button99.perform(click());

        ViewInteraction button100 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button100.perform(click());

        ViewInteraction button101 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button101.perform(click());

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.currentRound), withText("8 hands"),
                        withParent(withParent(withId(R.id.bottom_sheet)))));
        textView11.check(matches(withText("8 hands")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.firstPlayer), withText("First player: Four"),
                        withParent(withParent(withId(R.id.bottom_sheet)))));
        textView12.check(matches(withText("First player: Four")));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.dealer), withText("Dealer: Three"),
                        withParent(withParent(withId(R.id.bottom_sheet)))));
        textView13.check(matches(withText("Dealer: Three")));

        ViewInteraction floatingActionButton26 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton26.perform(click());

        ViewInteraction button102 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button102.perform(click());

        ViewInteraction button103 = onView(
                allOf(withText("4"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                4),
                        isDisplayed()));
        button103.perform(click());

        ViewInteraction button104 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button104.perform(click());

        ViewInteraction button105 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button105.perform(click());

        ViewInteraction floatingActionButton27 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton27.perform(click());

        ViewInteraction button106 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button106.perform(click());

        ViewInteraction button107 = onView(
                allOf(withText("5"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                5),
                        isDisplayed()));
        button107.perform(click());

        ViewInteraction button108 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button108.perform(click());

        ViewInteraction button109 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button109.perform(click());

        ViewInteraction floatingActionButton28 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton28.perform(click());

        ViewInteraction button110 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button110.perform(click());

        ViewInteraction button111 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button111.perform(click());

        ViewInteraction button112 = onView(
                allOf(withText("7"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                7),
                        isDisplayed()));
        button112.perform(click());

        ViewInteraction button113 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button113.perform(click());

        ViewInteraction floatingActionButton29 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton29.perform(click());

        ViewInteraction button114 = onView(
                allOf(withText("4"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                4),
                        isDisplayed()));
        button114.perform(click());

        ViewInteraction button115 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button115.perform(click());

        ViewInteraction button116 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button116.perform(click());

        ViewInteraction button117 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button117.perform(click());

        ViewInteraction floatingActionButton30 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton30.perform(click());

        ViewInteraction button118 = onView(
                allOf(withText("5"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                5),
                        isDisplayed()));
        button118.perform(click());

        ViewInteraction button119 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button119.perform(click());

        ViewInteraction button120 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button120.perform(click());

        ViewInteraction button121 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button121.perform(click());

        ViewInteraction floatingActionButton31 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton31.perform(click());

        ViewInteraction button122 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button122.perform(click());

        ViewInteraction button123 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button123.perform(click());

        ViewInteraction button124 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button124.perform(click());

        ViewInteraction button125 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button125.perform(click());

        ViewInteraction floatingActionButton32 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton32.perform(click());

        ViewInteraction button126 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button126.perform(click());

        ViewInteraction button127 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button127.perform(click());

        ViewInteraction button128 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button128.perform(click());

        ViewInteraction button129 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button129.perform(click());

        ViewInteraction floatingActionButton33 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton33.perform(click());

        ViewInteraction button130 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button130.perform(click());

        ViewInteraction button131 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button131.perform(click());

        ViewInteraction button132 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button132.perform(click());

        ViewInteraction button133 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button133.perform(click());

        ViewInteraction floatingActionButton34 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton34.perform(click());

        ViewInteraction button134 = onView(
                allOf(withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        button134.perform(click());

        ViewInteraction button135 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button135.perform(click());

        ViewInteraction button136 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button136.perform(click());

        ViewInteraction button137 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button137.perform(click());

        ViewInteraction floatingActionButton35 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton35.perform(click());

        ViewInteraction button138 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button138.perform(click());

        ViewInteraction button139 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button139.perform(click());

        ViewInteraction button140 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button140.perform(click());

        ViewInteraction button141 = onView(
                allOf(withText("5"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                5),
                        isDisplayed()));
        button141.perform(click());

        ViewInteraction floatingActionButton36 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton36.perform(click());

        ViewInteraction button142 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button142.perform(click());

        ViewInteraction button143 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button143.perform(click());

        ViewInteraction button144 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button144.perform(click());

        ViewInteraction button145 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button145.perform(click());

        ViewInteraction floatingActionButton37 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton37.perform(click());

        ViewInteraction button146 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button146.perform(click());

        ViewInteraction button147 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button147.perform(click());

        ViewInteraction button148 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button148.perform(click());

        ViewInteraction button149 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button149.perform(click());

        ViewInteraction floatingActionButton38 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton38.perform(click());

        ViewInteraction button150 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button150.perform(click());

        ViewInteraction button151 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button151.perform(click());

        ViewInteraction button152 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button152.perform(click());

        ViewInteraction button153 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button153.perform(click());

        ViewInteraction floatingActionButton39 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton39.perform(click());

        ViewInteraction button154 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button154.perform(click());

        ViewInteraction button155 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button155.perform(click());

        ViewInteraction button156 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button156.perform(click());

        ViewInteraction button157 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button157.perform(click());

        ViewInteraction floatingActionButton40 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton40.perform(click());

        ViewInteraction button158 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button158.perform(click());

        ViewInteraction button159 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button159.perform(click());

        ViewInteraction button160 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button160.perform(click());

        ViewInteraction button161 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button161.perform(click());

        ViewInteraction floatingActionButton41 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton41.perform(click());

        ViewInteraction button162 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button162.perform(click());

        ViewInteraction button163 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button163.perform(click());

        ViewInteraction button164 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button164.perform(click());

        ViewInteraction button165 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button165.perform(click());

        ViewInteraction floatingActionButton42 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton42.perform(click());

        ViewInteraction button166 = onView(
                allOf(withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        button166.perform(click());

        ViewInteraction button167 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button167.perform(click());

        ViewInteraction button168 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button168.perform(click());

        ViewInteraction button169 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button169.perform(click());

        ViewInteraction floatingActionButton43 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton43.perform(click());

        ViewInteraction button170 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button170.perform(click());

        ViewInteraction button171 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button171.perform(click());

        ViewInteraction button172 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button172.perform(click());

        ViewInteraction button173 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button173.perform(click());

        ViewInteraction floatingActionButton44 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton44.perform(click());

        ViewInteraction button174 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button174.perform(click());

        ViewInteraction button175 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button175.perform(click());

        ViewInteraction button176 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button176.perform(click());

        ViewInteraction button177 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button177.perform(click());

        // Asserts with onView won't work at this point because the recycler view does not hold all the views at the same time
        // An assert using onData would work but for now it's easier to just check the scores at the end

        //////////////
        // Round 22 //
        //////////////

        ViewInteraction floatingActionButton45 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton45.perform(click());

        ViewInteraction button178 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button178.perform(click());

        ViewInteraction button179 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button179.perform(click());

        ViewInteraction button180 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button180.perform(click());

        ViewInteraction button181 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button181.perform(click());

        ViewInteraction floatingActionButton46 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton46.perform(click());

        ViewInteraction button182 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button182.perform(click());

        ViewInteraction button183 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button183.perform(click());

        ViewInteraction button184 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button184.perform(click());

        ViewInteraction button185 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button185.perform(click());

        ViewInteraction floatingActionButton47 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton47.perform(click());

        ViewInteraction button186 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button186.perform(click());

        ViewInteraction button187 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button187.perform(click());

        ViewInteraction button188 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button188.perform(click());

        ViewInteraction button189 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button189.perform(click());

        ViewInteraction floatingActionButton48 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton48.perform(click());

        ViewInteraction button190 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button190.perform(click());

        ViewInteraction button191 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button191.perform(click());

        ViewInteraction button192 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button192.perform(click());

        ViewInteraction button193 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button193.perform(click());

        ViewInteraction floatingActionButton49 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton49.perform(click());

        ViewInteraction button194 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button194.perform(click());

        ViewInteraction button195 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button195.perform(click());

        ViewInteraction button196 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button196.perform(click());

        ViewInteraction button197 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button197.perform(click());

        ViewInteraction floatingActionButton50 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton50.perform(click());

        ViewInteraction button198 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button198.perform(click());

        ViewInteraction button199 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button199.perform(click());

        ViewInteraction button200 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button200.perform(click());

        ViewInteraction button201 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button201.perform(click());

        checkLeaderboard(0, "One", "87");
        checkLeaderboard(1, "Four", "84");
        checkLeaderboard(2, "Two", "5");
        checkLeaderboard(3, "Three", "-10");

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.alertTitle), withText("Game over!"),
                        withParent(allOf(withId(R.id.title_template),
                                withParent(withId(R.id.topPanel)))),
                        isDisplayed()));
        textView16.check(matches(isDisplayed()));

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button2), withText("Dismiss"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                2)));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.action_undo), withContentDescription("Undo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView4.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(android.R.id.button1), withText("Undo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton7.perform(scrollTo(), click());

        ViewInteraction floatingActionButton51 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.game_coord_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        floatingActionButton51.perform(click());

        ViewInteraction button202 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button202.perform(click());

        ViewInteraction button203 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button203.perform(click());

        ViewInteraction button204 = onView(
                allOf(withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        button204.perform(click());

        ViewInteraction button205 = onView(
                allOf(withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.grid),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        button205.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(android.R.id.button1), withText("Return to menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton8.perform(scrollTo(), click());

        ViewInteraction button206 = onView(
                allOf(withId(R.id.buttonNewGame), withText("NEW GAME"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button206.check(matches(isDisplayed()));
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

    /**
     * Gets the index of the child view that has the bet of a player
     *
     * @param playerIndex The index of the player
     * @return The index of the child view
     */
    private int getViewIndexOfBet(int playerIndex) {
        return 3 * playerIndex + 2;
    }

    /**
     * Gets the index of the child view that has the result of a player
     *
     * @param playerIndex The index of the player
     * @return The index of the child view
     */
    private int getViewIndexOfScore(int playerIndex) {
        return 3 * playerIndex + 3;
    }

    private void checkScore(String expectedScore, int roundIndex, int playerIndex) {
        onView(allOf(
                withText(expectedScore),
                childAtPosition(
                        allOf(
                                withId(R.id.scoreRow),
                                childAtPosition(isDisplayed(), roundIndex)
                        ),
                        getViewIndexOfScore(playerIndex))))
                .check(matches(isDisplayed()));
    }

    private void checkLeaderboard(int index, String playerName, String score) {
        onView(allOf(withId(R.id.playerName), withParent(childAtPosition(isDisplayed(), index)))).check(matches(withText(playerName)));
        onView(allOf(withId(R.id.playerScore), withParent(childAtPosition(isDisplayed(), index)))).check(matches(withText(score)));
    }
}
