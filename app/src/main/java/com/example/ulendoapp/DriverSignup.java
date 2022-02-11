package com.example.ulendoapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private TextInputEditText textCarModel, textPhysicalAddress, textNumberPlate;
    private TextInputLayout materialCarModel, materialPhysicalAddress, materialNumberPlate;
    private String carModel, plateNumber, physicalAddress, email;
    private Button submitBtn;
    private Boolean success;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        materialCarModel = findViewById(R.id.materialCarModel);
        materialNumberPlate = findViewById(R.id.materialNumberPlate);
        materialPhysicalAddress = findViewById(R.id.materialPhysicalAddress);
        textCarModel = findViewById(R.id.textCarModel);
        textNumberPlate = findViewById(R.id.textNumberPlate);
        textPhysicalAddress = findViewById(R.id.textPhysicalAddress);

        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDriver();
            }
        });
    }

    private boolean validateDriver(){
        boolean valid = false;

        carModel = Objects.requireNonNull(textCarModel.getText()).toString();
        plateNumber = Objects.requireNonNull(textNumberPlate.getText()).toString();
        physicalAddress = Objects.requireNonNull(textPhysicalAddress.getText()).toString();

        if (carModel.isEmpty()){
            materialCarModel.setError("Please enter your car model");
        }else if (plateNumber.isEmpty()){
            materialNumberPlate.setError("Please enter plate number");
        } else if (physicalAddress.isEmpty()) {
            materialPhysicalAddress.setError("Please enter your address");
        } else{
            email = getEmail();
            valid = true;
        }

        return valid;
    }

    private void addDriver(){
        if(validateDriver()){
            db = FirebaseFirestore.getInstance();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            Map<String, Object> driver = new HashMap<>();

            driver.put("Car Model", carModel);
            driver.put("Plate Number", plateNumber);
            driver.put("Physical Address", physicalAddress);
            driver.put("Email Address", email);
            driver.put("Nation ID", "N/A");
            driver.put("Drivers License ID", "N/A");
            driver.put("Driver Status", "N/A");
            driver.put("Current Location", "N/A");
            driver.put("Destination", "N/A");
            driver.put("Pickup Time", "N/A");
            driver.put("ETA", "N/A");
            driver.put("Ride as Passenger", "N/A");
            driver.put("Ride as Driver", "N/A");
            driver.put("Rating", "N/A");

            db.collection("Drivers")
                    .add(driver)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "inserted successfully");
                            changeStatus();
                            DriverSignup.this.startActivity(new Intent(DriverSignup.this, HomeDriver.class));
//                            Toast.makeText(DriverSignup.this, "Successfully inserted", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "error! failed");
//                            Toast.makeText(DriverSignup.this, "Unsuccessfully inserted", Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

    private String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

    private void changeStatus(){
        db.collection("Users")
                .whereEqualTo("Email Address", email)
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

                                Toast.makeText(DriverSignup.this, "successfully changed status", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "Email does not exist ", task.getException());
                            Toast.makeText(DriverSignup.this, "change failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
























