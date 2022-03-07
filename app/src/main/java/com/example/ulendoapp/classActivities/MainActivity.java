package com.example.ulendoapp.classActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ulendoapp.R;

public class MainActivity extends AppCompatActivity {
    final int splashTime = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(() -> startActivity(new Intent(MainActivity.this, Login.class)),splashTime);
    }
}