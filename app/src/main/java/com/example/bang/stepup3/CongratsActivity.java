package com.example.bang.stepup3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by haiphan on 10/13/16.
 */
public class CongratsActivity extends AppCompatActivity {
    TextView congratsTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congrats);

        String foodName = getIntent().getExtras().getString("foodName");
        congratsTextView.setText("Congratulations, you have burnt off " + foodName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
