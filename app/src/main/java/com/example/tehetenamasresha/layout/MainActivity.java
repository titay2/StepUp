package com.example.tehetenamasresha.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MeterView meter = (MeterView) this.findViewById(R.id.meter);
        meter.runAsync(false);
        meter.setOnFinishListener(new OnFinishListener() {
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
            }
        });

        final Button run = (Button) this.findViewById(R.id.run);
        run.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                run.setEnabled(false);

                new Thread(new Runnable() {
                    public void run() {
                        final int stop = 100;
                        for (int i = 1; i <= stop; i++) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) { }

                            final int n = i;
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    meter.setValue(n, stop);
                                }
                            });
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                run.setEnabled(true);
                            }
                        });
                    }
                }).start();
            }
        });
    }}