package com.chillbox.app.cinemas;

import com.chillbox.app.view.base.MvpPresenter;

/**
 * Created by aman1 on 22/12/2017.
 */

public interface ILocationMvpPresenter <V extends ILocationMvpView> extends MvpPresenter<V> {

    void onCallCinemaList(String latitude, String longitude, String radius, String type, String key );
}
