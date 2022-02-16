package com.example.ulendoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AddVehicle extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        imageView = findViewById(R.id.btn_back);

        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
              Intent intent = new Intent(AddVehicle.this, driver_my_vehicles.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              finish();
            }
        });
    }

}