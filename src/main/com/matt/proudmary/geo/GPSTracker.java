package com.matt.proudmary.geo;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.matt.proudmary.exception.LocationUnavailableException;

import java.util.Date;

public class GPSTracker implements LocationListener {
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    private final Context mContext;

    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
    }

    public Location getLocation() throws LocationUnavailableException {
        locationManager = (LocationManager) mContext
                .getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            try {
                Looper looper = Looper.getMainLooper();
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, looper);
//                locationManager.requestLocationUpdates(
//                        LocationManager.GPS_PROVIDER,
//                        MIN_TIME_BW_UPDATES,
//                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
//                        this,
//                        looper);
            } catch (Exception e) {
                e.getMessage();
            }
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d("ProudMary", "ProudMary: " + new Date(lastKnownLocation.getTime()));
            return lastKnownLocation;
        } else {
            throw new LocationUnavailableException("Is GPS enabled?");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderEnabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderDisabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
