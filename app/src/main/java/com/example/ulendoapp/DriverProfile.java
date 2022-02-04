package com.example.ulendoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class DriverProfile extends AppCompatActivity {

    RecyclerView where_car_model_goes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        where_car_model_goes = findViewById(R.id.where_car_model_goes);
       LinearLayoutManager layoutManager = new LinearLayoutManager(this);
       layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
       where_car_model_goes.setLayoutManager(layoutManager);
    }
}