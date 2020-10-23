package com.example.emmastevensvlogstimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageButton mButtonBaseline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    public void init() {
        mButtonBaseline = findViewById(R.id.imagebutton_baseline);
        mButtonBaseline.setOnClickListener(view -> {
//            loadTimer();
            openDialog();
        });
    }

    public void loadTimer() {
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
    }

    public void openDialog() {
        TimerDataCustomDialog timerDataCustomDialog = new TimerDataCustomDialog();
        timerDataCustomDialog.show(getSupportFragmentManager(),"example dialog");
//        Activity activity = getActivity();
//        AlertDialog dialog = ...;
//
//// retrieve display dimensions
//        Rect displayRectangle = new Rect();
//        Window window = activity.getWindow();
//        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
//
//// inflate and adjust layout
//        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View layout = inflater.inflate(R.layout.your_dialog_layout, null);
//        layout.setMinimumWidth((int)(displayRectangle.width() * 0.9f));
//        layout.setMinimumHeight((int)(displayRectangle.height() * 0.9f));


    }
}