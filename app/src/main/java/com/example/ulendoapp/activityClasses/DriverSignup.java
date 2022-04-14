package com.example.ulendoapp.activityClasses;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ulendoapp.R;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DriverSignup extends AppCompatActivity {
    private TextInputEditText textVehicleBrand, textVehicleModel, textModelYear, textVehicleColor, textBookingType, textLicensePlate, textLicenseId;
    private TextInputLayout materialVehicleBrand, materialVehicleModel, materialModelYear, materialVehicleColor,
            materialBookingType, materialLicensePlate, materialLicenseId;
    private String vBrand, vModel, vModelYr, vColor, vBookingType, vLicensePlate, vLicenseId;
    private Button submitBtn;
    private Boolean success;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        preferenceManager = new PreferenceManager(getApplicationContext());

        textVehicleBrand = findViewById(R.id.text_vehicle_brand);
        textVehicleModel = findViewById(R.id.text_vehicle_model);
        textModelYear = findViewById(R.id.text_model_year);
        textVehicleColor = findViewById(R.id.text_vehicle_color);
        textBookingType =  findViewById(R.id.text_vehicle_booking_type);
        textLicensePlate = findViewById(R.id.text_vehicle_licence_plate);
        textLicenseId = findViewById(R.id.text_licence_id);

        materialVehicleBrand = findViewById(R.id.material_signup_vehicle_brand);
        materialVehicleModel = findViewById(R.id.material_signup_vehicle_model);
        materialModelYear = findViewById(R.id.material_signup_model_year);
        materialVehicleColor = findViewById(R.id.material_signup_vehicle_color);
        materialBookingType  = findViewById(R.id.material_signup_booking_type);
        materialLicensePlate = findViewById(R.id.material_signup_licence_plate);
        materialLicenseId = findViewById(R.id.material_license_id);

        submitBtn = findViewById(R.id.driver_register_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateDriver()){
                    addDriver();
                }
            }
        });
    }

    private boolean validateDriver(){
        boolean valid = false;
        vBrand = Objects.requireNonNull(textVehicleBrand.getText()).toString();
        vModel = Objects.requireNonNull(textVehicleModel.getText()).toString();
        vModelYr = Objects.requireNonNull(textModelYear.getText()).toString();
        vColor = Objects.requireNonNull(textVehicleColor.getText()).toString();
        vBookingType = Objects.requireNonNull(textBookingType.getText()).toString();
        vLicensePlate = Objects.requireNonNull(textLicensePlate.getText()).toString();
        vLicenseId = Objects.requireNonNull(textLicenseId.getText()).toString();


        if (vBrand.isEmpty()){
            materialVehicleBrand.setError("Please enter your car brand");
        }else if (vModel.isEmpty()){
            materialVehicleModel.setError("Please enter car model");
        } else if (vModelYr.isEmpty()) {
            materialModelYear.setError("Please enter model year");
        } else if (vColor.isEmpty()) {
            materialVehicleColor.setError("Please enter vehicle color");
        } else if (vBookingType.isEmpty()) {
            materialBookingType.setError("Please select your booking type");
        }else if (vLicensePlate.isEmpty()) {
            materialLicensePlate.setError("Please enter license number");
        } else if (vLicenseId.isEmpty()) {
            materialLicenseId.setError("Please enter license ID");
        }else{
            valid = true;
        }

        return valid;
    }

    private void addDriver(){
            db = FirebaseFirestore.getInstance();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            Map<String, Object> driver = new HashMap<>();

            driver.put("Vehicle Brand", vBrand);
            driver.put("Vehicle Model", vModel);
            driver.put("Model Year", vModelYr);
            driver.put("Vehicle Color", vColor);
            driver.put("Booking Type", vBookingType);
            driver.put("License Plate", vLicensePlate);
            driver.put("License ID", vLicenseId);
            driver.put("Driver Status", "N/A");
            driver.put("Rides as Passenger", "N/A");
            driver.put("Rides as Driver", "N/A");
            driver.put("Rating", "N/A");
            driver.put("Email Address", getEmail());

            db.collection("Driver Vehicles")
                    .add(driver)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "inserted successfully");
                            changeStatus();
                            DriverSignup.this.startActivity(new Intent(DriverSignup.this, HomeDriver.class));
                            preferenceManager.putString(Constants.KEY_T_RECEIVER_ID, documentReference.getId());
//                            Toast.makeText(DriverSignup.this, "Successfully inserted", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "error! failed");
                            Toast.makeText(DriverSignup.this, "Unsuccessfully inserted", Toast.LENGTH_LONG).show();
                        }
                    });
    }

    private String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

    private void changeStatus(){
        db.collection("Users")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "Email already exist!");
                                String userId = document.getId();
                                db.collection("Users")
                                        .document(userId)
                                        .update("Status","driver" );
//                                Toast.makeText(DriverSignup.this, "successfully changed status", Toast.LENGTH_LONG).show();
                            }
                        } else {
//                            Log.d(TAG, "Email does not exist ", task.getException());
                            Toast.makeText(DriverSignup.this, "change failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
























