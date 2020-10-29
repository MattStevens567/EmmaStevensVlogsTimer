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


// Notes - Things to be added later
// Add service so that timer can run in the background and be stopped in notification bar
// Add workout description for each workout, e.g. tell the user they are doing pushups this round
// Update how play and pause work, assign to the circle progress bar

public class TimerActivity extends AppCompatActivity {

    private static final String TAG = "TimerActivity";

    // A bit of a jank fix but stops timer displaying 6 seconds sometimes on start
    private static final long SETUP_TIME_IN_MILLIS = 5000;
    private static final long ANIMATION_DURATION_MAIN = 500;
    private static final long ANIMATION_DURATION_QUICK = 100;


    // Recorded in ms e.g. 5s = 5000
    private static long sMainTimerDuration, sSetupTimerDuration;
    private static int sProgressBarDuration, sProgressBarSetup, sProgressBarRestTimeExercise, sProgressBarRestTimeRound, sProgressBarRestTimeCircuit;
    // Time before timer starts

    private TimerData mTimerData;

    private FloatingActionButton mfabPlay, mfabPause, mfabReset;
    private ProgressBar mProgressbarTimer;
    private TextView mTextViewTimeLeft, mTextViewPbStatus;
    private ImageButton mButtonBack;
    private TextView mTextViewWorkoutName;
    private TextView mTextViewCountExercise, mTextViewCountRound, mTextViewCountCircuit;

    private CountDownTimer mCountDownTimer;

    // mSetupTimerRunning determines if the setup timer is being used first
    // mResting determines if timer should run the exercise rest time or normal time
    // mQuickAnimate determines if the animation for the progress bar should use ANIMATION_DURATION_QUICK
    private boolean mTimerRunning;
    private boolean mSetupTimerRunning;
    private boolean mResting;
    private boolean mNextRoundStart;
    private boolean mTimerFirstRun;
    private boolean mTimerFinished;
    private boolean mQuickAnimate;

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

        // Assigning data from mTimerData to variables
        mTotalCircuit = mTimerData.getCircuitAmount();
        mTotalRound = mTimerData.getRoundAmount();
        mTotalExercise = mTimerData.getExerciseAmount();

        mRestTimeCircuit = mTimerData.getCircuitRestTime()*1000;
        mRestTimeRound = mTimerData.getRoundRestTime()*1000;
        mRestTimeExercise = mTimerData.getExerciseRestTime()*1000;

        // Creating progress bar max values
        sSetupTimerDuration = SETUP_TIME_IN_MILLIS;
        sMainTimerDuration = mTimerData.getExerciseDuration()*1000;
        sProgressBarDuration = (int) sMainTimerDuration;
        sProgressBarSetup = (int) sSetupTimerDuration;
        sProgressBarRestTimeCircuit = (int) mRestTimeCircuit;
        sProgressBarRestTimeRound = (int) mRestTimeRound;
        sProgressBarRestTimeExercise = (int) mRestTimeExercise;
        mTimeLeftInMillis = sSetupTimerDuration;

        // Assigning ids
        mfabPlay = findViewById(R.id.fab_play);
        mfabPause = findViewById(R.id.fab_pause);
        mfabReset = findViewById(R.id.fab_reset);
        mProgressbarTimer = findViewById(R.id.progressbar_timer);
        mTextViewTimeLeft = findViewById(R.id.textview_time_left);
        mTextViewPbStatus = findViewById(R.id.textview_pb_status);
        mButtonBack = findViewById(R.id.ib_back);
        mTextViewWorkoutName = findViewById(R.id.textview_workout_title);
        mTextViewCountCircuit = findViewById(R.id.textview_circuit_count);
        mTextViewCountRound = findViewById(R.id.textview_round_count);
        mTextViewCountExercise = findViewById(R.id.textview_exercise_count);



        mfabPlay.setOnClickListener(view -> {
            mfabPlay.setVisibility(View.INVISIBLE);
            mfabPause.setVisibility(View.VISIBLE);
            if(!mTimerRunning) {
                mainTimer();
            }
            mTimerRunning = true;
        });
        mfabPause.setOnClickListener(view -> {
            mfabPause.setVisibility(View.INVISIBLE);
            mfabPlay.setVisibility(View.VISIBLE);
            pauseTimer();
        });
        mfabReset.setOnClickListener(view -> {
            resetTimer();
        });

        mButtonBack.setOnClickListener(view -> {
            finish();

        });
        mTextViewWorkoutName.setText(mTimerData.getWorkoutName());

        timerSetup();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
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
                mCountDownTimer.cancel();
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
                            mTextViewPbStatus.setText("Rest");
                            mTimeLeftInMillis = mRestTimeExercise;
                            mProgressbarTimer.setMax(sProgressBarRestTimeExercise);
                        } else {
                            mTextViewPbStatus.setText("Exercise " + mCountExercise);
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
                            mTextViewPbStatus.setText("Rest");
                            mTextViewCountExercise.setText( mCountExercise + "/" + mTimerData.getExerciseAmount());
                            mTextViewCountRound.setText( mCountRound + "/" + mTimerData.getRoundAmount());
                            mTimeLeftInMillis = mRestTimeRound;
                            mProgressbarTimer.setMax(sProgressBarRestTimeRound);

                        } else {
                            mCountRound = 1;
                            if(mCountCircuit < mTotalCircuit) {
                                mCountCircuit++;
                                mTextViewPbStatus.setText("Rest");
                                mTextViewCountExercise.setText( mCountExercise + "/" + mTimerData.getExerciseAmount());
                                mTextViewCountRound.setText( mCountRound + "/" + mTimerData.getRoundAmount());
                                mTextViewCountCircuit.setText( mCountCircuit + "/" + mTimerData.getCircuitAmount());
                                mTimeLeftInMillis = mRestTimeCircuit;
                                mProgressbarTimer.setMax(sProgressBarRestTimeCircuit);
                            } else {
                                timerFinished();
                            }


                        }
                        mResting = false;
                    }
                } else {
                    mSetupTimerRunning = false;
                    mTextViewPbStatus.setText("Exercise " + mCountExercise);
                    mTimeLeftInMillis = sMainTimerDuration;
                    mProgressbarTimer.setMax(sProgressBarDuration);
                }

                Log.d(TAG, "mTimeLeftInMillis After: " + mTimeLeftInMillis);
                if(!mTimerFinished) {
                    mainTimer();
                }

            }
        };
        mCountDownTimer.start();
        mfabReset.setVisibility(View.INVISIBLE);
    }

    public void timerFinished() {
        mTextViewPbStatus.setText("Finished!");
        mTimerFinished = true;
        mTimerFirstRun = true;
        mTimeLeftInMillis = 0;
        updateCountDownText(mTimeLeftInMillis);
        updateProgressBar(mTimeLeftInMillis);
        mfabPlay.setVisibility(View.INVISIBLE);
        mfabPause.setVisibility(View.INVISIBLE);
        mfabReset.setVisibility(View.VISIBLE);
    }

    public void timerSetup() {
        // Setting up Counts and rest times (Might have to change location if a a service is used)

        mCountCircuit = 1;
        mCountRound = 1;
        mCountExercise = 1;

        // Setting up bools
        mSetupTimerRunning = true;
        mTimerRunning = false;
        mResting = true;
        mNextRoundStart = false;
        mTimerFirstRun = true;
        mTimerFinished = false;

        mTimeLeftInMillis = sSetupTimerDuration;
        sProgressBarDuration = (int) sMainTimerDuration;
        sProgressBarSetup = (int) sSetupTimerDuration;

        mProgressbarTimer.setMax(sProgressBarSetup);
        updateCountDownText(mTimeLeftInMillis);
        updateProgressBar(mTimeLeftInMillis);


        mfabReset.setVisibility(View.INVISIBLE);
        mfabPlay.setVisibility(View.VISIBLE);
        mfabPause.setVisibility(View.VISIBLE);

        mTextViewPbStatus.setText("Get Ready");
        mTextViewCountCircuit.setText( mCountCircuit + "/" + mTimerData.getCircuitAmount());
        mTextViewCountRound.setText( mCountRound + "/" + mTimerData.getRoundAmount());
        mTextViewCountExercise.setText( mCountExercise + "/" + mTimerData.getExerciseAmount());
    }


    public void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mfabReset.setVisibility(View.VISIBLE);
    }

    public void resetTimer() {
        Log.d(TAG, "ResetTimer Clicked");
        timerSetup();

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
        setProgressAnimate(mProgressbarTimer, percentage, ANIMATION_DURATION_MAIN);
        //mProgressbarTimer.setProgress((percentage));

//        Log.d(TAG, "mTimeLeftInMillis: " + mTimeLeftInMillis);
//       Log.d(TAG, "START_TIME_LEFT_IN_MILLIS: " + START_TIME_IN_MILLIS);
//        Log.d(TAG, "percentage: " + percentage);
//        Log.d(TAG, "mProgressBarTimer: " + mProgressbarTimer.getProgress());
    }

    // Add another animate function for when timer resets back
    // atm 500 is to slow and looks weird
    public void setProgressAnimate(ProgressBar pb, int progressTo, long duration) {
//        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        animation.setDuration(duration);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }






}
