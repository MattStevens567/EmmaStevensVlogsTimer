package com.example.emmastevensvlogstimer;

import android.os.Parcel;
import android.os.Parcelable;

public class TimerData implements Parcelable {
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

    protected TimerData(Parcel in) {
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

    public int getExerciseAmount() {
        return mExerciseAmount;
    }

    public int getExerciseRestTime() {
        return mExerciseRestTime;
    }

    public int getExerciseDuration() {
        return mExerciseDuration;
    }

}
