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

/**
 * The type Add vehicle.
 */
public class AddVehicle extends AppCompatActivity {
    /**
     * The Preference manager.
     */
    PreferenceManager preferenceManager;
    /**
     * The V brand.
     */
    String vBrand,
    /**
     * The V model.
     */
    vModel,
    /**
     * The V model yr.
     */
    vModelYr,
    /**
     * The V color.
     */
    vColor,
    /**
     * The V booking type.
     */
    vBookingType,
    /**
     * The V license plate.
     */
    vLicensePlate;
    /**
     * The Db.
     */
    FirebaseFirestore db;
    /**
     * The Current user.
     */
    FirebaseUser currentUser;
    /**
     * The Text booking type.
     */
    TextInputEditText textBookingType,
    /**
     * The Text vehicle brand.
     */
    textVehicleBrand,
    /**
     * The Text color.
     */
    textColor,
    /**
     * The Text model.
     */
    textModel,
    /**
     * The Text licence plate.
     */
    textLicencePlate, /**
     * The Text year.
     */
    textYear;
    /**
     * The Btn back.
     */
    ImageView btnBack;
    /**
     * The Complete.
     */
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