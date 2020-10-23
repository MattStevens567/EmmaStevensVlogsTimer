package com.example.emmastevensvlogstimer;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class TimerDataCustomDialog extends AppCompatDialogFragment {

    private static final String TAG = "TimerDataCustomDialog";
    private Button mButtonTest;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_timer_custom, null);
        builder.setView(view);



//        mButtonTest = view.findViewById(R.id.button_test);
//        mButtonTest.setOnClickListener(v -> {
//           Log.d(TAG, "mButtonTest Pressed");
//        });


        return builder.create();

//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setContentView(R.layout.layout_dialog_timer_custom_old);
//        return dialog;



    }
}
