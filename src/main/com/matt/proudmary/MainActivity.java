package com.matt.proudmary;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.matt.proudmary.listener.StartButtonListener;
import com.matt.proudmary.listener.StopButtonListener;
import main.proudmary.R;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {
    private Intent serviceIntent;
    private TextView textView;
    private EditText recipientNumberView;
    private EditText destinationElement;

    private EditText frequencyElement;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textView = (TextView) findViewById(R.id.text);

        serviceIntent = new Intent(this, UpdaterService.class);

        recipientNumberView = (EditText) findViewById(R.id.recipientNumber);

        destinationElement = (EditText) findViewById(R.id.destination);

        frequencyElement = (EditText) findViewById(R.id.frequency);


        addListeners();
    }

    public void addListeners() {

        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new StartButtonListener(this, textView, recipientNumberView, destinationElement, frequencyElement, serviceIntent));

        Button stopButton = (Button) findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new StopButtonListener(this, textView, serviceIntent));
    }

}
