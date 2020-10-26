package com.example.emmastevensvlogstimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements TimerDataCustomDialog.DialogListener {

    private static final String TAG = "MainActivity";

    private ImageButton mButtonTimerCustom;
    private ImageButton mButtonTimerArmsAndLegs;
    private TimerData mTimerData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate Called");
        init();

    }

    public void init() {
        mButtonTimerCustom = findViewById(R.id.ib_timer_custom);
        mButtonTimerCustom.setOnClickListener(view -> {
            openDialog();
        });

        mButtonTimerArmsAndLegs = findViewById(R.id.ib_timer_armsandlegs);
        mButtonTimerArmsAndLegs.setOnClickListener(view -> {
            mTimerData = new TimerDataArmsAndLegs();
            loadTimer(mTimerData);
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