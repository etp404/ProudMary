package com.matt.proudmary.geo;

import android.location.Location;
import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mouldm02
 * Date: 29/12/2013
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class GoogleAPIAdapter {

    public static String getEstimatedJourneyDuration(Location curent, Location destination) throws Exception {
        String urlString = "http://maps.googleapis.com/maps/api/directions/json?";

        List<NameValuePair> params = new LinkedList<NameValuePair>();
        params.add(new BasicNameValuePair("origin", curent.getLatitude() + "," + curent.getLongitude()));
        params.add(new BasicNameValuePair("destination", destination.getLatitude() + "," + destination.getLongitude()));
        params.add(new BasicNameValuePair("sensor", "false"));

        String paramString = URLEncodedUtils.format(params, "utf-8");
        urlString += paramString;

        JSONObject json = new JSONObject(getResponse(urlString));
        String duration = json.getJSONArray("routes")
                .getJSONObject(0)
                .getJSONArray("legs")
                .getJSONObject(0)
                .getJSONObject("duration")
                .get("text").toString();
        return duration;
    }

    private static String getResponse(String urlString) throws IOException {
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
}
