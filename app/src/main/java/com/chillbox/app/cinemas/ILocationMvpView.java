package com.chillbox.app.cinemas;

import com.chillbox.app.network.model.cinema.CinemaWrapper;
import com.chillbox.app.view.base.MvpView;

/**
 * Created by aman1 on 22/12/2017.
 */

public interface ILocationMvpView extends MvpView {

    void onFetchCinemaListSuccess(CinemaWrapper cinemaWrapperList);
    void onFetchDataError(String message);
}
