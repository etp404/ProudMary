package com.matt.proudmary.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class StopButtonListener implements View.OnClickListener {
    private static final String SERVICE_STARTED = "ProudMary has stopped";

    private Context context;
    private TextView textView;
    private Intent serviceIntent;

    public StopButtonListener(Context context, TextView textView, Intent serviceIntent) {
        this.context = context;
        this.textView = textView;
        this.serviceIntent = serviceIntent;
    }

    @Override
    public void onClick(View arg0) {
        textView.setText(SERVICE_STARTED);
        context.stopService(serviceIntent);
    }
}
