package com.example.ulendoapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ulendoapp.databinding.ActivityAddVehicleBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddVehicle extends AppCompatActivity {
    ActivityAddVehicleBinding binding;
    String vBrand, vModel, vModelYr, vColor, vBookingType, vLicensePlate, vLicenseId;
    FirebaseFirestore db;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVehicleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        vBookingType = binding.textBookingType.getText().toString();
        vBrand = binding.textVehicleBrand.getText().toString();
        vColor = binding.textColor.getText().toString();
        vModel = binding.textModel.getText().toString();
        vLicenseId = binding.textLicencePlate.getText().toString();
        vModelYr = binding.textYear.getText().toString();

        listeners();

    }
    private void listeners(){
        binding.btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(AddVehicle.this, driver_my_vehicles.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
        });
        binding.complete.setOnClickListener( view ->  addDriver());
    }
    private void addDriver(){
        db = FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Map<String, Object> vehicle = new HashMap<>();

        vehicle.put("Vehicle Brand", vBrand);
        vehicle.put("Vehicle Model", vModel);
        vehicle.put("Model Year", vModelYr);
        vehicle.put("Vehicle Color", vColor);
        vehicle.put("Booking Type", vBookingType);
        vehicle.put("License Plate", vLicensePlate);
        vehicle.put("Email Address", getEmail());

        db.collection("Driver Vehicles")
                .add(vehicle)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddVehicle.this, "Vehicle added successfully", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(AddVehicle.this, driver_my_vehicles.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddVehicle.this, "Failed to add vehicle", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

}