package com.matt.proudmary;

import android.app.NotificationManager;
import android.content.Context;
import android.location.Location;
import android.telephony.SmsManager;
import android.util.Log;
import com.matt.proudmary.exception.LocationUnavailableException;
import com.matt.proudmary.geo.GPSTracker;
import com.matt.proudmary.geo.GoogleAPIAdapter;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: mouldm02
 * Date: 22/02/2014
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class Updater implements Runnable {
    private static String GOOGLE_MAP_FORMAT = "http://maps.google.com/maps?q=loc:%s+%s";
    private static String UPDATE_FORMAT = "I should be about %s. Current location %s";
    private static String NOTIFICATION_MESSAGE = "%s until arrival. Update sent.";
    private final String destinationString;
    private final String recipient;
    private final Context context;

    private Notifier notifier;
    private GPSTracker tracker;

    public Updater(Context context, NotificationManager notificationManager, String recipient, String destinationString) throws LocationUnavailableException {
        this.recipient = recipient;
        this.destinationString = destinationString;
        this.context = context;
        notifier = new Notifier(context, notificationManager);
        tracker = new GPSTracker(context);
    }

    public void run() {
        try{
            Location currentLocation;
            try {
                currentLocation = tracker.getLocation();
            }
            catch (LocationUnavailableException e) {
                notifier.provideNotification(e.getMessage());
                return;
            }

            String googlemapLink = String.format(GOOGLE_MAP_FORMAT,
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude());

            //TODO: This shouldn't happen every time this method is run, it should be done at the start, once.
            Location destination = getLocationFromAddress(destinationString);

            String eta = GoogleAPIAdapter.getEstimatedJourneyDuration(currentLocation, destination);

            String updateMessage = String.format(UPDATE_FORMAT, eta, googlemapLink);

            sendSMS(recipient, updateMessage);

            notifier.provideNotification(String.format(NOTIFICATION_MESSAGE, eta));
        }
        catch (Exception e) {
            Log.d("ProudMary", "ProudMary: " + e.getMessage());
        }
    }

    //TODO: put in own class
    private Location getLocationFromAddress(String address) throws Exception {
        String addressGet = "http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false";

        JSONObject response = new JSONObject(getResponse(String.format(addressGet, address)));
        JSONObject locationJson = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
        Location location = new Location("dummyprovider");
        location.setLatitude((Double)locationJson.get("lat"));
        location.setLongitude((Double)locationJson.get("lng"));
        return location;
    }

    //TODO: put in own class
    private String getResponse(String urlString) throws IOException {
        String result = "";
        String line;
        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = rd.readLine()) != null) {
            result += line;
        }
        rd.close();
        return result;
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
