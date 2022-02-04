package com.example.ulendoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DriverProfile extends AppCompatActivity {

    RecyclerView where_car_model_goes;
    ImageView profile_back, edit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        where_car_model_goes = findViewById(R.id.where_car_model_goes);
       LinearLayoutManager layoutManager = new LinearLayoutManager(this);
       layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
       where_car_model_goes.setLayoutManager(layoutManager);

       profile_back = findViewById(R.id.profile_back);
       edit_profile = findViewById(R.id.edit_profile);

       edit_profile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(DriverProfile.this, EditDriverProfile.class));
           }
       });

       profile_back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(DriverProfile.this, HomeDriver.class));
           }
       });
    }
}