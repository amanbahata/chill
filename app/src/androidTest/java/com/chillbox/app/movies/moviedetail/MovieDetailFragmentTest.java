package com.chillbox.app.movies.moviedetail;

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
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by aman1 on 29/12/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MovieDetailFragmentTest{
    @Rule
    public FragmentTestRule<MovieDetailFragment> mFragmentTestRule =
            new FragmentTestRule<>(MovieDetailFragment.class);


    @Before
    public void setUp(){
        mFragmentTestRule.launchActivity(null);
    }

    @Test
    public void fragmentCanBeInstantiated() {
        onView(withId(R.id.ll_movie_detail)).check(matches(isDisplayed()));
    }


    @Test
    public void testMovieTitleTextViewThenDisplay() throws InterruptedException {
        onView(withId(R.id.movie_detail_title)).check(matches(isDisplayed()));
    }


    @Test
    public void testMovieReleaseYearTextViewThenDisplay(){
        onView(withId(R.id.movie_detail_release_year)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_detail_release_year)).check(matches(withText("Fetching movie information ......")));

    }

    @Test
    public void testMovieRunTimeTextViewThenDisplay(){
        onView(withId(R.id.movie_detail_runtime)).check(matches(isDisplayed()));

    }

    @Test
    public void testMovieRatingTextViewThenDisplay(){
        onView(withId(R.id.movie_detail_rating)).check(matches(isDisplayed()));

    }

    @Test
    public void testMovieOverviewTextViewThenDisplay(){
        onView(withId(R.id.movie_detail_overview)).check(matches(isDisplayed()));

    }

    @Test
    public void testPlayTrailerButtonThenClick(){
        onView(withId(R.id.play_video)).perform(scrollTo());
        onView(withId(R.id.play_video)).check(matches(isClickable()));
        onView(withId(R.id.play_video)).check(matches(withText("Play Trailer")));
    }


    @After
    public void tearDown(){
        mFragmentTestRule.finishActivity();
    }
}