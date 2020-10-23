package com.example.emmastevensvlogstimer;

public abstract class TimerData {
    private int mCircuitAmount;
    private int mCircuitRestTime;

    private int mRoundAmount;
    private int mRoundRestTime;

    private int mExerciseAmount;
    private int mExerciseRestTime;
    private int mExerciseDuration;

    public TimerData(int circuitAmount, int circuitRestTime, int roundAmount, int roundRestTime, int exerciseAmount, int exerciseRestTime, int exerciseDuration) {
        mCircuitAmount = circuitAmount;
        mCircuitRestTime = circuitRestTime;
        mRoundAmount = roundAmount;
        mRoundRestTime = roundRestTime;
        mExerciseAmount = exerciseAmount;
        mExerciseRestTime = exerciseRestTime;
        mExerciseDuration = exerciseDuration;
    }

    // Getters used in TimerActivity
    public int getCircuitAmount() {
        return mCircuitAmount;
    }

    public int getCircuitRestTime() {
        return mCircuitRestTime;
    }

    public int getRoundAmount() {
        return mRoundAmount;
    }

    public int getRoundRestTime() {
        return mRoundRestTime;
    }

    public int getmExerciseAmount() {
        return mExerciseAmount;
    }

    public int getExerciseRestTime() {
        return mExerciseRestTime;
    }

    public int getExerciseDuration() {
        return mExerciseDuration;
    }

}
