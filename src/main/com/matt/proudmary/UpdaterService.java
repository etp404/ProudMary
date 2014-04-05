package com.matt.proudmary;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.TextView;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: mouldm02
 * Date: 22/02/2014
 * Time: 15:55
 * To change this template use File | Settings | File Templates.
 */
public class UpdaterService extends IntentService {

    private ScheduledThreadPoolExecutor executor;

    public UpdaterService() {
        super("required?");
    }

    public UpdaterService(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int id) {
        PowerManager mgr = (PowerManager)getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
        wakeLock.acquire();

        String recipient = intent.getStringExtra("recipient");
        String destinationString = intent.getStringExtra("destination");
        try {
            Updater updater = new Updater(getApplicationContext(),
                                          (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE),
                                          recipient, URLEncoder.encode(destinationString, "utf-8"));
            executor = new ScheduledThreadPoolExecutor(1);
            executor.scheduleAtFixedRate(updater, 0, 20, TimeUnit.MINUTES);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override

    public void onDestroy() {
        executor.shutdown();
    }


}
