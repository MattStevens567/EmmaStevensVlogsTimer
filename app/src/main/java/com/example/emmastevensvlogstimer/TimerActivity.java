package com.example.emmastevensvlogstimer;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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
    private static int sProgressBarDuration, sProgressBarSetup, sProgressBarRestTimeExercise, sProgressBarRestTimeRound, sProgressBarRestTimeCircuit;
    // Time before timer starts

    private TimerData mTimerData;

    private FloatingActionButton mfabPlay, mfabPause, mfabReset;
    private ProgressBar mProgressbarTimer;
    private TextView mTextViewTimeLeft;
    private ImageButton mButtonBack;
    private TextView mTextViewCountExercise, mTextViewCountRound, mTextViewCountCircuit;

    private CountDownTimer mCountDownTimer;

    private boolean mMainTimerRunning;
    private boolean mSetupTimerRunning;
    private boolean mResting;
    private boolean mNextRoundStart;
    private boolean mTimerFirstRun;

    private long mTimeLeftInMillis, mTimeLeftInMillisSetup;

    private int mCountExercise, mCountRound, mCountCircuit;
    private int mTotalExercise, mTotalRound, mTotalCircuit;
    private long mRestTimeExercise, mRestTimeRound, mRestTimeCircuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Intent intent = getIntent();
        mTimerData = intent.getParcelableExtra("TimerData");

        Log.d(TAG, "mRoundRestTime: " + mTimerData.getRoundRestTime());

        init();


    }

    @SuppressLint("SetTextI18n")
    public void init() {

        // Setting up Counts and rest times (Might have to change location if a a service is used)
        mCountCircuit = 1;
        mCountRound = 1;
        mCountExercise = 1;

        mTotalCircuit = mTimerData.getCircuitAmount();
        mTotalRound = mTimerData.getRoundAmount();
        mTotalExercise = mTimerData.getExerciseAmount();

        mRestTimeCircuit = mTimerData.getCircuitRestTime()*1000;
        mRestTimeRound = mTimerData.getRoundRestTime()*1000;
        mRestTimeExercise = mTimerData.getExerciseRestTime()*1000;

        sProgressBarRestTimeCircuit = (int) mRestTimeCircuit;
        sProgressBarRestTimeRound = (int) mRestTimeRound;
        sProgressBarRestTimeExercise = (int) mRestTimeExercise;


        // Setting up values for setup timer
//        sSetupTimerDuration = SETUP_TIME_IN_MILLIS;
//        sProgressBarSetup = (int) sSetupTimerDuration;
//        mTimeleftInMillisSetup = sSetupTimerDuration;

        // Setting up timer values for main timer
//        sMainTimerDuration = mTimerData.getExerciseDuration()*1000;
//        sProgressBarMax = (int) sMainTimerDuration;
//        mTimeLeftInMillis = sMainTimerDuration;

        sSetupTimerDuration = SETUP_TIME_IN_MILLIS;
        sMainTimerDuration = mTimerData.getExerciseDuration()*1000;
        sProgressBarDuration = (int) sMainTimerDuration;
        sProgressBarSetup = (int) sSetupTimerDuration;
        mTimeLeftInMillis = sSetupTimerDuration;

        // Setting up bools
        mSetupTimerRunning = true;
        mMainTimerRunning = false;
        mResting = true;
        mNextRoundStart = false;
        mTimerFirstRun = true;

        // Assigning ids
        mfabPlay = findViewById(R.id.fab_play);
        mfabPause = findViewById(R.id.fab_pause);
        mfabReset = findViewById(R.id.fab_reset);
        mProgressbarTimer = findViewById(R.id.progressbar_timer);
        mTextViewTimeLeft = findViewById(R.id.textview_time_left);
        mButtonBack = findViewById(R.id.imagebutton_back);
        mTextViewCountCircuit = findViewById(R.id.textview_circuit_count);
        mTextViewCountRound = findViewById(R.id.textview_round_count);
        mTextViewCountExercise = findViewById(R.id.textview_exercise_count);



        mfabPlay.setOnClickListener(view -> {
            mainTimer();
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

        mTextViewCountCircuit.setText( mCountCircuit + "/" + mTimerData.getCircuitAmount());
        mTextViewCountRound.setText( mCountRound + "/" + mTimerData.getRoundAmount());
        mTextViewCountExercise.setText( mCountExercise + "/" + mTimerData.getExerciseAmount());

        updateCountDownText(mTimeLeftInMillis);
        mProgressbarTimer.setMax(sProgressBarSetup);
        mProgressbarTimer.setProgress(sProgressBarSetup);

    }

    // Timer for countdown before workout begins
    // Kept separate from main timer logic as otherwise
    // there would be lots of spaghetti code to include it


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
                mCountDownTimer.cancel();
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
                Log.d(TAG, "mTimeLeftInMillis Before: " + mTimeLeftInMillis);
//                Log.d(TAG, "sMainTimerDuration" + sMainTimerDuration);
//                Log.d(TAG, "mExerciseRestTime: " + mRestTimeExercise);
//                Log.d(TAG, "mRoundRestTime: " + mRestTimeRound);
//                Log.d(TAG, "mCircuitRestTime: " + mRestTimeCircuit);
                if(!mSetupTimerRunning) {
                    if(mCountExercise < mTotalExercise) {
                        Log.d(TAG, "mResting: " + mResting);
                        if(mResting) {

                            mTimeLeftInMillis = mRestTimeExercise;
                            mProgressbarTimer.setMax(sProgressBarRestTimeExercise);
                        } else {
                            if(mNextRoundStart) {
                                mNextRoundStart = false;
                            } else {
                                mCountExercise++;
                            }

                            mTextViewCountExercise.setText( mCountExercise + "/" + mTimerData.getExerciseAmount());
                            mTimeLeftInMillis = sMainTimerDuration;
                            mProgressbarTimer.setMax(sProgressBarDuration);

                        }
                        // invert boolean value
                        mResting = !mResting;

                    } else {
                        mCountExercise = 1;
                        mNextRoundStart = true;
                        if(mCountRound < mTotalRound) {
                            mCountRound++;
                            mTextViewCountExercise.setText( mCountExercise + "/" + mTimerData.getExerciseAmount());
                            mTextViewCountRound.setText( mCountRound + "/" + mTimerData.getRoundAmount());
                            mTimeLeftInMillis = mRestTimeRound;
                            mProgressbarTimer.setMax(sProgressBarRestTimeRound);

                        } else {
                            mCountRound = 1;
                            if(mCountCircuit < mTotalCircuit) {
                                mCountCircuit++;
                                mTextViewCountExercise.setText( mCountExercise + "/" + mTimerData.getExerciseAmount());
                                mTextViewCountRound.setText( mCountRound + "/" + mTimerData.getRoundAmount());
                                mTextViewCountCircuit.setText( mCountCircuit + "/" + mTimerData.getCircuitAmount());
                                mTimeLeftInMillis = mRestTimeCircuit;
                                mProgressbarTimer.setMax(sProgressBarRestTimeCircuit);
                            } else {
                                mTimeLeftInMillis = 0;
                                updateCountDownText(mTimeLeftInMillis);
                                updateProgressBar(mTimeLeftInMillis);
                            }


                        }
                        mResting = false;
                    }
                } else {
                    mSetupTimerRunning = false;
                    mTimeLeftInMillis = sMainTimerDuration;
                    mProgressbarTimer.setMax(sProgressBarDuration);
                }

                Log.d(TAG, "mTimeLeftInMillis After: " + mTimeLeftInMillis);
                mainTimer();
            }
        }.start();
        //mCountDownTimer.start();

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
        mTimerFirstRun = true;
        mTimeLeftInMillis = sMainTimerDuration;

        updateCountDownText(mTimeLeftInMillis);
        updateProgressBar(mTimeLeftInMillis);

        mfabReset.setVisibility(View.INVISIBLE);
        mfabPlay.setVisibility(View.VISIBLE);
        mfabPause.setVisibility(View.VISIBLE);
    }

    public void updateCountDownText(long timeLeftInMillis) {

        // Little bit of logic to make
        if(mTimerFirstRun) {
            mTimerFirstRun = false;
        } else {
            timeLeftInMillis = timeLeftInMillis + 1000;
        }


        int minutes = (int) ((timeLeftInMillis) / 1000) / 60;
        int seconds = (int) ((timeLeftInMillis) / 1000) % 60;

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


    // Add another animate function for when timer resets back
    // atm 500 is to slow and looks weird
    public void setProgressAnimate(ProgressBar pb, int progressTo) {
//        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }




}
