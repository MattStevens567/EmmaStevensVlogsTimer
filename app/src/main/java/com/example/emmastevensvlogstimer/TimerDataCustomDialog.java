package com.example.emmastevensvlogstimer;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class TimerDataCustomDialog extends AppCompatDialogFragment {

    private static final String TAG = "TimerDataCustomDialog";

    private DialogListener listener;

    private Button mButtonDone;
    private SeekBar mSeekbarCircuitAmount;
    private SeekBar mSeekbarCircuitRest;
    private SeekBar mSeekbarRoundAmount;
    private SeekBar mSeekbarRoundRest;
    private SeekBar mSeekbarExerciseAmount;
    private SeekBar mSeekbarExerciseRest;
    private SeekBar mSeekbarExerciseDuration;

    private TextView mTextViewCircuitAmount;
    private TextView mTextViewCircuitRest;
    private TextView mTextViewRoundAmount;
    private TextView mTextViewRoundRest;
    private TextView mTextViewExerciseAmount;
    private TextView mTextViewExerciseRest;
    private TextView mTextViewExerciseDuration;

    private int mExerciseDurationProg, mExerciseAmountProg;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mExerciseDurationProg = 10;
        mExerciseAmountProg = 1;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_timer_custom, null);
        builder.setView(view);

        initCircuitAmount(view);
        initCircuitRest(view);
        initRoundAmount(view);
        initRoundRest(view);
        initExerciseAmount(view);
        initExerciseRest(view);
        initExerciseDuration(view);

        mButtonDone = view.findViewById(R.id.button_done);
        mButtonDone.setOnClickListener(v -> {
           Log.d(TAG, "mButtonTest Pressed");
            int circuitAmount = mSeekbarCircuitAmount.getProgress();
            int circuitRest = mSeekbarCircuitRest.getProgress();
            int roundAmount = mSeekbarRoundAmount.getProgress();
            int roundRest = mSeekbarRoundRest.getProgress();
            int exerciseAmount = mExerciseAmountProg;
            int exerciseRest = mSeekbarExerciseRest.getProgress();
            int exerciseDuration = mExerciseDurationProg;
            listener.getSeekBarData(circuitAmount, circuitRest, roundAmount, roundRest, exerciseAmount, exerciseRest, exerciseDuration);

        });


        return builder.create();




    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }
    }

    public interface DialogListener {
        void getSeekBarData(int circuitAmount, int circuitRest, int roundAmount, int roundRest, int exerciseAmount, int exerciseRest, int exerciseDuration);

    }


    // Initialise views
    public void initCircuitAmount(View view) {
        mTextViewCircuitAmount = view.findViewById(R.id.textView_circuit_amount);

        mSeekbarCircuitAmount = view.findViewById(R.id.seekbar_circuit_amount);
        mSeekbarCircuitAmount.setMax(5);
        mSeekbarCircuitAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mTextViewCircuitAmount.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void initCircuitRest(View view) {
        mTextViewCircuitRest = view.findViewById(R.id.textView_circuit_rest);

        mSeekbarCircuitRest = view.findViewById(R.id.seekbar_circuit_rest);
        mSeekbarCircuitRest.setMax(180);
        mSeekbarCircuitRest.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int stepSize = 10;

                progress = (progress/stepSize)*stepSize;
                seekBar.setProgress(progress);
                Log.d(TAG, "progress: " + progress);
                mTextViewCircuitRest.setText(String.valueOf(progress + "s"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void initRoundAmount(View view) {
        mTextViewRoundAmount = view.findViewById(R.id.textview_round_amount);

        mSeekbarRoundAmount = view.findViewById(R.id.seekbar_round_amount);
        mSeekbarRoundAmount.setMax(10);
        mSeekbarRoundAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mTextViewRoundAmount.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void initRoundRest(View view) {
        mTextViewRoundRest = view.findViewById(R.id.textview_round_rest);

        mSeekbarRoundRest = view.findViewById(R.id.seekbar_round_rest);
        mSeekbarRoundRest.setMax(60);
        mSeekbarRoundRest.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int stepSize = 5;

                progress = (progress/stepSize)*stepSize;
                seekBar.setProgress(progress);
                Log.d(TAG, "progress: " + progress);
                mTextViewRoundRest.setText(String.valueOf(progress + "s"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void initExerciseAmount(View view) {
        int max = 10;
        int min = 1;

        mTextViewExerciseAmount = view.findViewById(R.id.textView_exercise_amount);
        mSeekbarExerciseAmount = view.findViewById(R.id.seekbar_exercise_amount);
        mSeekbarExerciseAmount.setMax(max);
        mSeekbarExerciseAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if(progress < min) {
                    progress = min;
                }

                mTextViewExerciseAmount.setText(String.valueOf(progress));
                mExerciseAmountProg = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void initExerciseRest(View view) {
        mTextViewExerciseRest = view.findViewById(R.id.textview_exercise_rest);

        mSeekbarExerciseRest = view.findViewById(R.id.seekbar_exercise_rest);
        mSeekbarExerciseRest.setMax(60);
        mSeekbarExerciseRest.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int stepSize = 5;

                progress = (progress/stepSize)*stepSize;
                seekBar.setProgress(progress);

                Log.d(TAG, "progress: " + progress);
                mTextViewExerciseRest.setText(String.valueOf(progress + "s"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void initExerciseDuration(View view) {
        int min = 10;
        int max = 60;
        int stepSize = 5;

        mTextViewExerciseDuration = view.findViewById(R.id.textview_exercise_duration);
        mSeekbarExerciseDuration = view.findViewById(R.id.seekbar_exercise_duration);
        mSeekbarExerciseDuration.setMax(max);
        mSeekbarExerciseDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {


                progress = (progress/stepSize)*stepSize;
                seekBar.setProgress(progress);
                if(progress < min) {
                    progress = min;
                }
                Log.d(TAG, "progress: " + progress);
                mTextViewExerciseDuration.setText(String.valueOf(progress + "s"));
                mExerciseDurationProg = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

}
