package com.example.ulendoapp.activityClasses;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulendoapp.R;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddVehicle extends AppCompatActivity {
    PreferenceManager preferenceManager;
    String vBrand, vModel, vModelYr, vColor, vBookingType, vLicensePlate;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    TextInputEditText textBookingType, textVehicleBrand, textColor, textModel, textLicencePlate, textYear;
    ImageView btnBack;
    MaterialButton complete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityAddVehicleBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_add_vehicle);
        preferenceManager = new PreferenceManager(getApplicationContext());
        db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        textBookingType = findViewById(R.id.text_booking_type);
        textColor = findViewById(R.id.text_color);
        textLicencePlate = findViewById(R.id.text_licence_plate);
        textModel = findViewById(R.id.text_model);
        textVehicleBrand = findViewById(R.id.text_vehicle_brand);
        textYear = findViewById(R.id.text_year);
        btnBack = findViewById(R.id.btn_back);
        complete = findViewById(R.id.complete);

        listeners();

    }
    private void listeners(){
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(AddVehicle.this, DriverMyVehicles.class);
            startActivity(intent);
        });
        complete.setOnClickListener(view -> addVehicle());
    }
    private void addVehicle(){
        vBookingType = Objects.requireNonNull(textBookingType.getText()).toString().trim();
        vBrand = Objects.requireNonNull(textVehicleBrand.getText()).toString().trim();
        vColor = Objects.requireNonNull(textColor.getText()).toString().trim();
        vModel = Objects.requireNonNull(textModel.getText()).toString().trim();
        vLicensePlate = Objects.requireNonNull(textLicencePlate.getText()).toString().trim();
        vModelYr = Objects.requireNonNull(textYear.getText()).toString().trim();

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
                .addOnSuccessListener(documentReference -> {
                    String ID = documentReference.getId();
                    preferenceManager.putString(Constants.KEY_VEHICLE_ID, ID);
                    Toast.makeText(AddVehicle.this, "Vehicle added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddVehicle.this, DriverMyVehicles.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> Toast.makeText(AddVehicle.this, "Failed to add vehicle", Toast.LENGTH_LONG).show());
    }

    private String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

}