package com.matt.proudmary.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.matt.proudmary.UpdaterService;
import main.proudmary.R;

public class StartButtonListener implements View.OnClickListener {
    private static final String SERVICE_STARTED = "ProudMary has started";
    private final EditText destinationElement;
    private final EditText recipientNumberView;

    private Context context;
    private TextView textView;
    private Intent serviceIntent;


    public StartButtonListener(Context context, TextView textView, EditText recipientNumberView, EditText destinationElement, Intent serviceIntent) {
        this.context = context;
        this.textView = textView;
        this.serviceIntent = serviceIntent;
        this.recipientNumberView = recipientNumberView;
        this.destinationElement = destinationElement;
    }

    @Override
    public void onClick(View arg0) {
        String recipient = recipientNumberView.getText().toString();
        String destination = destinationElement.getText().toString();

        serviceIntent.putExtra("recipient", recipient);
        serviceIntent.putExtra("destination", destination);
        try {
            context.startService(serviceIntent);
            textView.setText(SERVICE_STARTED);
        }
        catch (Exception e) {
            textView.setText(e.getMessage());
        }
    }
}
