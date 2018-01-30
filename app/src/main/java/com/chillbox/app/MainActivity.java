package com.chillbox.app;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import com.chillbox.app.anticipatedmovies.AnticipatedFragment;
import com.chillbox.app.anticipatedmovies.anticipatedmoviesdetail.AnticipateDetailFragment;
import com.chillbox.app.cinemas.LocationFragment;
import com.chillbox.app.network.model.TrackingData;
import com.chillbox.app.movies.mostplayedmovies.MostPlayedMoviesFragment;
import com.chillbox.app.trendingnews.TrendingFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAnalytics mFirebaseAnalytics;
    private Map<Integer, Fragment> fragmentMap = new HashMap<>();
    private Map<Integer, TrackingData> trackingDataMap = new HashMap<>();
    private Locale locale;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLanguagePref();  // set the language preferences

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // user interaction logging for analysis
        trackingDataMap.put(R.id.nav_trending, TrackingData.create("1", "trending", "image"));
        trackingDataMap.put(R.id.nav_anticipated, TrackingData.create("2", "anticipated", "image"));
        trackingDataMap.put(R.id.nav_movies, TrackingData.create("3", "movies", "image"));
        trackingDataMap.put(R.id.nav_cinemas, TrackingData.create("4", "cinemas", "image"));

        if (savedInstanceState == null){

            fragmentMap.put(R.id.nav_trending, TrendingFragment.newInstance());
            fragmentMap.put(R.id.nav_anticipated, AnticipatedFragment.newInstance());
            fragmentMap.put(R.id.nav_movies, MostPlayedMoviesFragment.newInstance());
            fragmentMap.put(R.id.nav_cinemas, LocationFragment.newInstance());

            replaceFragment(R.id.nav_trending);
        }
    }

    private void replaceFragment(int fragmentId) {
        Fragment fragment = fragmentMap.get(fragmentId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                //.addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify OnFragmentInteractionListener parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showChangeLangDialog();  // start the language settings dialog
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        TrackingData trackingData = trackingDataMap.get(id);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, trackingData.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, trackingData.getName());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, trackingData.getType());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        replaceFragment(id);
        return true;

        }


    public void addFragmentAnticipated(Fragment fragment){
        AnticipateDetailFragment myFragment = (AnticipateDetailFragment) getSupportFragmentManager()
                .findFragmentByTag("DETAIL_FRAGMENT");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (myFragment != null && myFragment.isVisible()){
            ft.remove(myFragment);
            ft.commit();
        }else{
            ft.add(R.id.fragment_container, fragment, "DETAIL_FRAGMENT");
            ft.commit();
        }
    }

    public void addFragmentMovieDetail(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getResources().getConfiguration().orientation  == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    /**
     * Changes the application language when a language is chosen via the setting menu
     */
    private void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.spinner_dialog, null);
        dialogBuilder.setView(dialogView);

        final Spinner spinner1 = (Spinner) dialogView.findViewById(R.id.spinner1);

        dialogBuilder.setTitle(getResources().getString(R.string.spinner_dialog_title));
        dialogBuilder.setMessage(getResources().getString(R.string.spinner_dialog_message));
        dialogBuilder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int langpos = spinner1.getSelectedItemPosition();
                switch(langpos) {
                    case 0: //English
                        Log.i("POS", "English");
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                                .edit().putString("LANG", "en").apply();
                        setLangRecreate("en");
                        return;
                    case 1: //Italian
                        Log.i("POS", "Italian");
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                                .edit().putString("LANG", "it").apply();
                        setLangRecreate("it");
                        return;
                    default: //By default set to english
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                                .edit().putString("LANG", "en").apply();
                        setLangRecreate("en");
                        return;
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    /**
     * Recreates the activity after changing the language setting
     * @param lang the language code chosen by the user  "it" for italian "en" for english
     */
    public void setLangRecreate(String lang) {

        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang);
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    /**
     * Save the chosen language in the settings preferences until is changed back again
     */
    private void setLanguagePref(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration config = getBaseContext().getResources().getConfiguration();

        String lang = settings.getString("LANG", "");
        if (! "".equals(lang) && ! config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
    }
}
