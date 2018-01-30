package com.chillbox.app.injection.components;

import com.chillbox.app.injection.modules.PresenterModule;
import com.chillbox.app.injection.scope.PerActivity;
import com.chillbox.app.movies.mostplayedmovies.MostPlayedMoviesFragment;

import dagger.Component;

/**
 * Created by aman1 on 02/01/2018.
 */
@PerActivity
@Component(modules = PresenterModule.class)
public interface MostPlayedMoviesComponent {

        void inject (MostPlayedMoviesFragment fragment);


 }
