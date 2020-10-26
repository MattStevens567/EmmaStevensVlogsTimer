package com.example.emmastevensvlogstimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements TimerDataCustomDialog.DialogListener {

    private static final String TAG = "MainActivity";

    private ImageButton mButtonBaseline;
    private TimerData mTimerData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate Called");
        init();

    }

    public void init() {
        mButtonBaseline = findViewById(R.id.imagebutton_baseline);
        mButtonBaseline.setOnClickListener(view -> {
            openDialog();
        });
    }

    public void loadTimer(TimerData timerData) {

        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("TimerData", timerData);
        startActivity(intent);
    }

    public void openDialog() {
        TimerDataCustomDialog timerDataCustomDialog = new TimerDataCustomDialog();
        timerDataCustomDialog.show(getSupportFragmentManager(),"example dialog");

    }

    // Launches TimeActivity with custom data
    @Override
    public void getSeekBarData(int ca, int cr, int ra, int rr, int ea, int er, int ed) {
        TimerDataCustom timerDataCustom = new TimerDataCustom(ca, cr, ra, rr, ea, er, ed);
        mTimerData = timerDataCustom;
        loadTimer(mTimerData);
    }
}