package com.chillbox.app.injection.components;

import com.chillbox.app.injection.modules.PresenterModule;
import com.chillbox.app.injection.scope.PerActivity;
import com.chillbox.app.trendingnews.TrendingFragment;

import dagger.Component;

/**
 * Created by aman1 on 01/01/2018.
 */

@PerActivity
@Component(modules = PresenterModule.class)
public interface TrendingComponent {

    void inject(TrendingFragment fragment);
}
