package com.chillbox.app.cinemas;

import android.support.test.runner.AndroidJUnit4;

import com.chillbox.app.FragmentTestRule;
import com.example.aman1.myapplication.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by aman1 on 29/12/2017.
 */

@RunWith(AndroidJUnit4.class)
public class LocationFragmentTest {

    FragmentTestRule<LocationFragment> mFragmentTestRule =
            new FragmentTestRule<>(LocationFragment.class);

    @Before
    public void setUp(){
        mFragmentTestRule.launchActivity(null);
    }

    @Test
    public void fragmentCanBeInstantiated(){
        onView(withId(R.id.action_locate)).check(matches(isClickable()));
    }

    @Test
    public void showCurrentLocationWhenButtonIsClicked() throws InterruptedException {
        Thread.sleep(5000);
        onView(withId(R.id.action_locate)).perform(click());
        Thread.sleep(5000);
    }

    @After
    public void tearDown(){
        mFragmentTestRule.finishActivity();
    }
}