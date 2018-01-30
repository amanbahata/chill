package com.chillbox.app.anticipatedmovies;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;

import com.chillbox.app.FragmentTestRule;
import com.example.aman1.myapplication.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by aman1 on 29/12/2017.
 */
@RunWith(AndroidJUnit4.class)
public class AnticipatedFragmentTest {

    @Rule
    public FragmentTestRule<AnticipatedFragment> mFragmentTestRule =
            new FragmentTestRule<>(AnticipatedFragment.class);

    @Before
    public void setUp(){
        mFragmentTestRule.launchActivity(null);
    }

    @Test
    public void fragmentCanBeInstantiated() throws InterruptedException {
        onView(withId(R.id.fl_anticipated)).check(matches(isDisplayed()));
        Thread.sleep(1500);
    }

    @Test
    public void testRecyclerViewScrolling() throws InterruptedException {
        Thread.sleep(1500);
        onView(withId(R.id.anticipated_recyclerview)).perform(RecyclerViewActions.scrollToPosition(4),
                RecyclerViewActions.scrollToPosition(1));
    }


    @Test
    public void testRecyclerViewThenPerformClick() throws InterruptedException {
        Thread.sleep(1500);
        onView(withId(R.id.anticipated_recyclerview)).perform(
                RecyclerViewActions.scrollToPosition(3)
                , RecyclerViewActions.actionOnItemAtPosition(8, click()));

    }

    @After
    public void tearDown(){
        mFragmentTestRule.finishActivity();
    }
}