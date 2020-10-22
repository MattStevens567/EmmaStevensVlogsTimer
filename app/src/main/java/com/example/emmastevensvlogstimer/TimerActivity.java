package com.example.emmastevensvlogstimer;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;


public class TimerActivity extends AppCompatActivity {
    // 60 Seconds
    private static final long START_TIME_IN_MILLIS = 10000;
    private static final String TAG = "TimerActivity";
    private static final int PROGRESS_BAR_MAX = 10000;

    private FloatingActionButton mfabPlay, mfabPause, mfabReset;
    private ProgressBar mProgressbarTimer;
    private TextView mTextViewTimeLeft;

    private CountDownTimer mCountDownTimer;

    private Boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        init();


    }

    public void init() {

        mfabPlay = findViewById(R.id.fab_play);
        mfabPause = findViewById(R.id.fab_pause);
        mfabReset = findViewById(R.id.fab_reset);
        mProgressbarTimer = findViewById(R.id.progressbar_timer);
        mTextViewTimeLeft = findViewById(R.id.textview_time_left);



        mfabPlay.setOnClickListener(view -> {
            startTimer();
        });
        mfabPause.setOnClickListener(view -> {
            pauseTimer();
        });
        mfabReset.setOnClickListener(view -> {
            resetTimer();
        });

        updateCountDownText();
        mProgressbarTimer.setMax(PROGRESS_BAR_MAX);
        mProgressbarTimer.setProgress(PROGRESS_BAR_MAX);
        //updateProgressBar();

    }

    public void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 200) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
                updateProgressBar();
            }

            @Override
            public void onFinish() {
                // Make play and pause buttons invisible
                // User can only push reset button now
                mfabPlay.setVisibility(View.INVISIBLE);
                mfabPause.setVisibility(View.INVISIBLE);
                mfabReset.setVisibility(View.VISIBLE);
                updateCountDownText();
                mProgressbarTimer.setProgress(0);
            }
        }.start();

        mTimerRunning = true;
        mfabReset.setVisibility(View.INVISIBLE);
    }

    public void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mfabReset.setVisibility(View.VISIBLE);
    }

    public void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        mProgressbarTimer.setProgress(PROGRESS_BAR_MAX);
        updateCountDownText();
        mfabReset.setVisibility(View.INVISIBLE);
        mfabPlay.setVisibility(View.VISIBLE);
        mfabPause.setVisibility(View.VISIBLE);
    }

    public void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);


        mTextViewTimeLeft.setText(timeLeftFormatted);
    }

    public void updateProgressBar() {
        long percentageTemp = mTimeLeftInMillis % START_TIME_IN_MILLIS;
        int percentage = (int)percentageTemp / 100;
       // int test2 = (int) START_TIME_IN_MILLIS / test;
        setProgressAnimate(mProgressbarTimer, percentage);
        //mProgressbarTimer.setProgress((percentage));

        Log.d(TAG, "mTimeLeftInMillis" + mTimeLeftInMillis);
        Log.d(TAG, "percentage: " + percentage);
        Log.d(TAG, "mProgressBarTimer: " + mProgressbarTimer.getProgress());
    }

    public void updateProgressBar(int timeLeft) {
        mProgressbarTimer.setProgress(0);
    }

    public void setProgressAnimate(ProgressBar pb, int progressTo) {
//        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo*100);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }




}
