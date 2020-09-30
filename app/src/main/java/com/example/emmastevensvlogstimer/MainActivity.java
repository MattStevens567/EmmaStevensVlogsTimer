package com.example.emmastevensvlogstimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private Button mButtonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mButtonTest = findViewById(R.id.buttonTest);
        mButtonTest.setOnClickListener(view -> {
            loadTimer();
        });
    }

    public void loadTimer() {
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
    }
}