package com.chillbox.app.injection.components;

import com.chillbox.app.cinemas.LocationFragment;
import com.chillbox.app.injection.modules.PresenterModule;
import com.chillbox.app.injection.scope.PerActivity;

import dagger.Component;

/**
 * Created by aman1 on 04/01/2018.
 */

@PerActivity
@Component(modules = PresenterModule.class)
public interface LocationComponent {
    void inject(LocationFragment locationFragment);
}
