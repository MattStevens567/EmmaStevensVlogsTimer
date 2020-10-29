package com.example.emmastevensvlogstimer;

import android.os.Parcel;
import android.os.Parcelable;

public class TimerData implements Parcelable {
    private String mWorkoutName;

    private int mCircuitAmount;
    private int mCircuitRestTime;

    private int mRoundAmount;
    private int mRoundRestTime;

    private int mExerciseAmount;
    private int mExerciseRestTime;
    private int mExerciseDuration;

    public TimerData(String workoutName, int circuitAmount, int circuitRestTime, int roundAmount, int roundRestTime, int exerciseAmount, int exerciseRestTime, int exerciseDuration) {
        mWorkoutName = workoutName;
        mCircuitAmount = circuitAmount;
        mCircuitRestTime = circuitRestTime;
        mRoundAmount = roundAmount;
        mRoundRestTime = roundRestTime;
        mExerciseAmount = exerciseAmount;
        mExerciseRestTime = exerciseRestTime;
        mExerciseDuration = exerciseDuration;
    }

    protected TimerData(Parcel in) {
        mWorkoutName = in.readString();
        mCircuitAmount = in.readInt();
        mCircuitRestTime = in.readInt();
        mRoundAmount = in.readInt();
        mRoundRestTime = in.readInt();
        mExerciseAmount = in.readInt();
        mExerciseRestTime = in.readInt();
        mExerciseDuration = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mWorkoutName);
        dest.writeInt(mCircuitAmount);
        dest.writeInt(mCircuitRestTime);
        dest.writeInt(mRoundAmount);
        dest.writeInt(mRoundRestTime);
        dest.writeInt(mExerciseAmount);
        dest.writeInt(mExerciseRestTime);
        dest.writeInt(mExerciseDuration);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimerData> CREATOR = new Creator<TimerData>() {
        @Override
        public TimerData createFromParcel(Parcel in) {
            return new TimerData(in);
        }

        @Override
        public TimerData[] newArray(int size) {
            return new TimerData[size];
        }
    };

    // Getters used in TimerActivity


    public String getWorkoutName() {
        return mWorkoutName;
    }

    public int getCircuitAmount() {
        return mCircuitAmount;
    }

    public void setmCircuitAmount(int mCircuitAmount) {
        this.mCircuitAmount = mCircuitAmount;
    }

    public int getCircuitRestTime() {
        return mCircuitRestTime;
    }

    public void setmCircuitRestTime(int mCircuitRestTime) {
        this.mCircuitRestTime = mCircuitRestTime;
    }

    public int getRoundAmount() {
        return mRoundAmount;
    }

    public void setmRoundAmount(int mRoundAmount) {
        this.mRoundAmount = mRoundAmount;
    }

    public int getRoundRestTime() {
        return mRoundRestTime;
    }

    public void setmRoundRestTime(int mRoundRestTime) {
        this.mRoundRestTime = mRoundRestTime;
    }

    public int getExerciseAmount() {
        return mExerciseAmount;
    }

    public void setmExerciseAmount(int mExerciseAmount) {
        this.mExerciseAmount = mExerciseAmount;
    }

    public int getExerciseRestTime() {
        return mExerciseRestTime;
    }

    public void setmExerciseRestTime(int mExerciseRestTime) {
        this.mExerciseRestTime = mExerciseRestTime;
    }

    public int getExerciseDuration() {
        return mExerciseDuration;
    }

    public void setmExerciseDuration(int mExerciseDuration) {
        this.mExerciseDuration = mExerciseDuration;
    }
}
