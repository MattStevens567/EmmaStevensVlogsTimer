package com.example.emmastevensvlogstimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton mButtonBaseline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mButtonBaseline = findViewById(R.id.imageButton_Baseline);
        mButtonBaseline.setOnClickListener(view -> {
            loadTimer();
        });
    }

    public void loadTimer() {
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
    }
}