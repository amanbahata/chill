package com.chillbox.app.cinemas;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.chillbox.app.MainActivity;
import com.chillbox.app.R;
import com.chillbox.app.network.model.cinema.CinemaWrapper;
import com.chillbox.app.network.model.cinema.Result;
import com.chillbox.app.network.services.Api_List;
import com.chillbox.app.injection.components.DaggerLocationComponent;
import com.chillbox.app.injection.modules.PresenterModule;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;


/**
 * Fragment for displaying the list of cinemas closer to the current location of the user
 *
 * @author Aman Bahata
 * @version 2018/01/10
 */
public class LocationFragment extends SupportMapFragment implements ILocationMvpView {

    private static final String DEBUG_TAG = "LocationFragment";

    private static final int REQUEST_LOCATION_PERMISSIONS = 0;
    private static final String[] LOCATION_PERMISSIONS =new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private GoogleApiClient mClient;
    private Location mCurrentLocation;
    private GoogleMap mMap;

    @Inject protected LocationPresenter<LocationFragment> mLocationPresenter;


    public static LocationFragment newInstance(){
        return new LocationFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.cinemas_title));
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF2D2C2C")));
        }

        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();

        getMapAsync(googleMap -> {
            mMap = googleMap;
            moveCamera(new LatLng( 51.509865,-0.118092));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        });

        DaggerLocationComponent.builder()
                .presenterModule(new PresenterModule(this))
                .build()
                .inject(this);
        mLocationPresenter.onAttach(this);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()){
                    findCurrentLocation();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Find the users current location
     */
    private void findCurrentLocation(){
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(10000);
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        new SearchTask().execute(location);
                        mCurrentLocation = location;
                        updateUI();
                    }
                });
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_locator, menu);

        MenuItem searchItem = menu.findItem(R.id.action_locate);
        searchItem.setEnabled(mClient.isConnected());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_locate:
                if (hasLocationPermission() && locationControl()){
                    Toast.makeText(getActivity(), getString(R.string.location_fetching), Toast.LENGTH_SHORT).show();
                    findCurrentLocation();
                }else{
                    Toast.makeText(getActivity(), getString(R.string.location_request), Toast.LENGTH_SHORT).show();
                    requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Check if the user has location services turned on
     * @return true if location services is on and false if otherwise
     */
    private boolean hasLocationPermission(){
        int result = ContextCompat.checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().invalidateOptionsMenu();
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();

    }

    @Override
    public void onFetchCinemaListSuccess(CinemaWrapper cinemaWrapperList) {
        if (cinemaWrapperList.getStatus().equals("OK")) {
            for (Result result : cinemaWrapperList.getResults()) {
                addCinemaMarkers(result);
            }
        }
    }


    @Override
    public void onFetchDataError(String message) {
        Log.i(DEBUG_TAG, message);

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }

    /**
     * Adds markers to the map that each represents a single cinema location
     * @param result the latitude and longitude
     */
    private void addCinemaMarkers(Result result){
        Double lat = result.getGeometry().getLocation().getLat();
        Double lng = result.getGeometry().getLocation().getLng();

        LatLng cinemaPoint = new LatLng(lat, lng);
        MarkerOptions cinemaPositionMarker = new MarkerOptions()
                .title(result.getName())
                .snippet(result.getVicinity())
                .position(cinemaPoint)
                .anchor(0.0f, 1.0f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(cinemaPositionMarker);
    }

    /**
     * Updates the user interface by placing a marker on of the users current location
     */
    private void updateUI(){
        if (mMap == null){
            return;
        }
        LatLng myPoint = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        MarkerOptions myPositionMarker = new MarkerOptions()
                .position(myPoint)
                .title(getString(R.string.current_location))
                .anchor(0.0f, 1.0f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(myPositionMarker);

        moveCamera(myPoint);

    }

    /**
     * Moves map camera to the position of the users location
     * @param latLng the latitude and longitude of the user
     */
    private void moveCamera(LatLng latLng){
        CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }


    /**
     * Checks that the user position can be located via the network provided or by gps
     * @return true if both gps and provider are enabled, false otherwise
     */
    private boolean locationControl(){
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = locationManager != null && locationManager.isProviderEnabled(GPS_PROVIDER);
        boolean network_provider = locationManager != null && locationManager.isProviderEnabled(NETWORK_PROVIDER);
        return gps_enabled && network_provider;
    }

    /**
     * Provides asynchronous processing to retrieve local cinemas from
     * google places api
     */
    private class SearchTask extends AsyncTask<Location, Void, Void> {

        private String radius = "10000";  // search radius
        private String type = "movie_theater"; // type of establishment
        private String key = Api_List.API_KEY_GOOGLE_PLACES;

        @Override
        protected Void doInBackground(Location... locations) {
            for (Location location : locations) {
                String latitude = String.valueOf(location.getLatitude());
                String longitude = String.valueOf(location.getLongitude());
                mLocationPresenter.onCallCinemaList(latitude , longitude, radius, type, key );
            }
            return null;
        }
    }


}
