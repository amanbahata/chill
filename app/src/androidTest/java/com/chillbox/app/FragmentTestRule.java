package com.chillbox.app;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.chillbox.app.MainActivity;
import com.example.aman1.myapplication.R;

import junit.framework.Assert;

/**
 * Created by aman1 on 29/12/2017.
 */

public class FragmentTestRule<F extends Fragment> extends ActivityTestRule<MainActivity> {

        private final Class<F> mFragmentClass;
        private F mFragment;

        public FragmentTestRule(final Class<F> fragmentClass) {
            super(MainActivity.class, true, false);
            mFragmentClass = fragmentClass;
        }

        @Override
        protected void afterActivityLaunched() {
            super.afterActivityLaunched();

            getActivity().runOnUiThread(() -> {
                try {
                    //Instantiate and insert the fragment into the container layout
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    mFragment = mFragmentClass.newInstance();
                    transaction.replace(R.id.fragment_container, mFragment);
                    transaction.commit();
                } catch (InstantiationException | IllegalAccessException e) {
                    Assert.fail(String.format("%s: Could not insert %s into TestActivity: %s",
                            getClass().getSimpleName(),
                            mFragmentClass.getSimpleName(),
                            e.getMessage()));
                }
            });
        }
        public F getFragment(){
            return mFragment;
        }
    }

