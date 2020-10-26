package com.example.emmastevensvlogstimer;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;


public class TimerActivity extends AppCompatActivity {

    private static final String TAG = "TimerActivity";

    private static final long SETUP_TIME_IN_MILLIS = 5000;

    // Recorded in ms e.g. 5s = 5000
    private static long sMainTimerDuration, sSetupTimerDuration;
    private static int sProgressBarMax, sProgressBarMaxSetup;
    // Time before timer starts

    private TimerData mTimerData;

    private FloatingActionButton mfabPlay, mfabPause, mfabReset;
    private ProgressBar mProgressbarTimer;
    private TextView mTextViewTimeLeft;
    private ImageButton mButtonBack;

    private CountDownTimer mCountDownTimer;

    private boolean mMainTimerRunning;
    private boolean mSetupTimerRunning;
    private boolean mResting;

    private long mTimeLeftInMillis, mTimeleftInMillisSetup;

    private int mCountExercise, mCountRound, mCountCircuit;
    private int mTotalExercise, mTotalRound, mTotalCircuit;
    private int mRestTimeExercise, mRestTimeRound, mRestTimeCircuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Intent intent = getIntent();
        mTimerData = intent.getParcelableExtra("TimerData");

        Log.d(TAG, "mRoundRestTime: " + mTimerData.getRoundRestTime());

        init();


    }

    public void init() {

        // Setting up Counts and rest times (Might have to change location if a a service is used)
        mCountCircuit = 0;
        mCountRound = 0;
        mCountExercise = 0;

        mTotalCircuit = mTimerData.getCircuitAmount();
        mTotalRound = mTimerData.getRoundAmount();
        mTotalExercise = mTimerData.getExerciseAmount();

        mRestTimeCircuit = mTimerData.getCircuitRestTime();
        mRestTimeRound = mTimerData.getRoundRestTime();
        mRestTimeExercise = mTimerData.getExerciseRestTime();


        // Setting up values for setup timer
        sSetupTimerDuration = SETUP_TIME_IN_MILLIS;
        sProgressBarMaxSetup = (int) sSetupTimerDuration;
        mTimeleftInMillisSetup = sSetupTimerDuration;

        // Setting up timer values for main timer
        sMainTimerDuration = mTimerData.getExerciseDuration()*1000;
        sProgressBarMax = (int) sMainTimerDuration;
        mTimeLeftInMillis = sMainTimerDuration;

        // Setting up bools
        mSetupTimerRunning = true;
        mMainTimerRunning = false;
        mResting = false;

        // Assigning ids
        mfabPlay = findViewById(R.id.fab_play);
        mfabPause = findViewById(R.id.fab_pause);
        mfabReset = findViewById(R.id.fab_reset);
        mProgressbarTimer = findViewById(R.id.progressbar_timer);
        mTextViewTimeLeft = findViewById(R.id.textview_time_left);
        mButtonBack = findViewById(R.id.imagebutton_back);



        mfabPlay.setOnClickListener(view -> {

            if(mSetupTimerRunning) {
                setupTimer();
            } else {
                mainTimer();
            }
        });
        mfabPause.setOnClickListener(view -> {
            pauseTimer();
        });
        mfabReset.setOnClickListener(view -> {
            resetTimer();
        });

        mButtonBack.setOnClickListener(view -> {
            finish();

        });

        updateCountDownText(mTimeleftInMillisSetup);
        mProgressbarTimer.setMax(sProgressBarMaxSetup);
        mProgressbarTimer.setProgress(sProgressBarMaxSetup);

    }

    // Timer for countdown before workout begins
    // Kept separate from main timer logic as otherwise
    // there would be lots of spaghetti code to include it
    public void setupTimer() {
        mCountDownTimer = new CountDownTimer(mTimeleftInMillisSetup, 200) {
            @Override
            public void onTick(long l) {
                mTimeleftInMillisSetup = l;
                updateCountDownText(mTimeleftInMillisSetup);
                updateProgressBar(mTimeleftInMillisSetup);
            }

            // Initialise timer to run main exercises
            @Override
            public void onFinish() {
                mSetupTimerRunning = false;
                updateCountDownText(mTimeleftInMillisSetup);
                mProgressbarTimer.setMax(sProgressBarMax);
                //mProgressbarTimer.setProgress(sProgressBarMax);
                updateProgressBar(mTimeLeftInMillis);
                mainTimer();

            }
        }.start();

        mMainTimerRunning = true;
        mfabReset.setVisibility(View.INVISIBLE);
    }


    // Main timer that runs for exercise
    public void mainTimer() {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 200) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText(mTimeLeftInMillis);
                updateProgressBar(mTimeLeftInMillis);
            }

            @Override
            public void onFinish() {
                // Make play and pause buttons invisible
                // User can only push reset button now
//                mfabPlay.setVisibility(View.INVISIBLE);
//                mfabPause.setVisibility(View.INVISIBLE);
//                mfabReset.setVisibility(View.VISIBLE);
//                mTimeLeftInMillis = 0;
//                updateCountDownText(mTimeLeftInMillis);
//                updateProgressBar(mTimeLeftInMillis);
                Log.d(TAG, "mCountExercise: " + mCountExercise);
                Log.d(TAG, "mCountRound: " + mCountRound);
                Log.d(TAG, "mCountCircuit: " + mCountCircuit);
//                Log.d(TAG, "sMainTimerDuration" + sMainTimerDuration);
//                Log.d(TAG, "mExerciseRestTime: " + mRestTimeExercise);
//                Log.d(TAG, "mRoundRestTime: " + mRestTimeRound);
//                Log.d(TAG, "mCircuitRestTime: " + mRestTimeCircuit);
                if(mCountExercise < mTotalExercise) {
                    mCountExercise++;
                    if(mResting) {
                        mTimeLeftInMillis = mRestTimeExercise;
                    } else {
                        mTimeLeftInMillis = sMainTimerDuration;

                    }
                    // invert boolean value
                    mResting = !mResting;

                } else {
                    mCountExercise = 0;
                    if(mCountRound < mTotalRound) {
                        mCountRound++;
                        mTimeLeftInMillis = mRestTimeRound;

                    } else {
                        mCountRound = 0;
                        mCountCircuit++;
                        mTimeLeftInMillis = mTimerData.getCircuitRestTime();

                    }
                }
                mainTimer();
            }
        };
        mCountDownTimer.start();

        mMainTimerRunning = true;
        mfabReset.setVisibility(View.INVISIBLE);
    }

    public void pauseTimer() {
        mCountDownTimer.cancel();
        mMainTimerRunning = false;
        mfabReset.setVisibility(View.VISIBLE);
    }

    public void resetTimer() {
        Log.d(TAG, "ResetTimer Clicked");
        mTimeLeftInMillis = sMainTimerDuration;
        //mProgressbarTimer.setProgress(PROGRESS_BAR_MAX);
        updateCountDownText(mTimeLeftInMillis);
        updateProgressBar(mTimeLeftInMillis);

        mfabReset.setVisibility(View.INVISIBLE);
        mfabPlay.setVisibility(View.VISIBLE);
        mfabPause.setVisibility(View.VISIBLE);
    }

    public void updateCountDownText(long timeLeftInMillis) {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);


        mTextViewTimeLeft.setText(timeLeftFormatted);
    }

    public void updateProgressBar(long timeLeftInMillis) {
        long percentageTemp = timeLeftInMillis;
        int percentage = (int) percentageTemp;
       // int test2 = (int) START_TIME_IN_MILLIS / test;
        setProgressAnimate(mProgressbarTimer, percentage);
        //mProgressbarTimer.setProgress((percentage));

//        Log.d(TAG, "mTimeLeftInMillis: " + mTimeLeftInMillis);
//       Log.d(TAG, "START_TIME_LEFT_IN_MILLIS: " + START_TIME_IN_MILLIS);
//        Log.d(TAG, "percentage: " + percentage);
//        Log.d(TAG, "mProgressBarTimer: " + mProgressbarTimer.getProgress());
    }

    public void updateProgressBar(int timeLeft) {
        mProgressbarTimer.setProgress(0);
    }

    public void setProgressAnimate(ProgressBar pb, int progressTo) {
//        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }




}
