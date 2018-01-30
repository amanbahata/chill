package com.chillbox.app.injection.components;

import com.chillbox.app.injection.modules.ApplicationModule;
import com.chillbox.app.injection.scope.ApplicationContext;

import dagger.Component;

/**
 * Created by aman1 on 01/01/2018.
 */

@ApplicationContext
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    //CompositeDisposable exposeCompositeDisposable();
}