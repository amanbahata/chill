package com.chillbox.app.anticipatedmovies.anticipatedmoviesdetail;

import android.support.test.runner.AndroidJUnit4;

import com.chillbox.app.FragmentTestRule;
import com.example.aman1.myapplication.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by aman1 on 29/12/2017.
 */
@RunWith(AndroidJUnit4.class)
public class AnticipateDetailFragmentTest {

    @Rule
    public FragmentTestRule<AnticipateDetailFragment> mFragmentTestRule =
            new FragmentTestRule<>(AnticipateDetailFragment.class);

    @Before
    public void setUp(){
        mFragmentTestRule.launchActivity(null);
    }


    @Test
    public void fragmentCanBeInstantiated() {
        onView(withId(R.id.fl_anticipated_detail)).check(matches(isDisplayed()));
    }


    @Test
    public void testMovieDetailTitleTextViewThenDisplay(){
        onView(withId(R.id.detail_title)).check(matches(isDisplayed()));
    }


    @Test
    public void testMovieDetailOverviewTextViewThenDisplay(){
        onView(withId(R.id.movie_overview)).perform(scrollTo());
        onView(withId(R.id.movie_overview)).check(matches(isDisplayed()));
    }
    @Test
    public void testMovieReleaseDateTextViewThenDisplay(){
        onView(withId(R.id.movie_release_date)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown(){
        mFragmentTestRule.finishActivity();
    }
}