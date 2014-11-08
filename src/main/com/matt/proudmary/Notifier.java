package com.matt.proudmary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.matt.proudmary.MainActivity;
import main.proudmary.R;

import java.util.Date;

public class Notifier {
    private final NotificationManager notificationManager;
    private Context context;
    int count = 0;

    public Notifier(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationManager = notificationManager;
    }

    public void provideNotification(String contentText) {
        PendingIntent launchNotification = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

        Date date = new Date();
        Notification noti = new Notification.Builder(context)
                .setContentTitle("Proud Mary")
                .setContentText(contentText)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setSmallIcon(R.drawable.pm_logo)
                .setContentIntent(launchNotification)
                .build();


        notificationManager.notify(count++, noti);
    }
}
