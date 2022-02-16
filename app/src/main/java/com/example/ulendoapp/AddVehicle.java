package com.example.ulendoapp;

import androidx.appcompat.app.AppCompatActivity;

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
               startActivity(intent);
            }
        });
    }
}