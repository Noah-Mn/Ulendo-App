package com.example.ulendoapp.activityClasses;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulendoapp.databinding.ActivityAddNewRideBinding;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddNewRide extends AppCompatActivity {
    private ActivityAddNewRideBinding binding;
    String startingPoint, destination, date, time, status;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth auth;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewRideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        listeners();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

    }

    private void listeners() {
        binding.btnBack.setOnClickListener(view -> onBackPressed());
        binding.add.setOnClickListener(view -> addData());
    }

    public void addData() {
        startingPoint = Objects.requireNonNull(binding.textStartingPoint.getText()).toString().trim();
        destination = Objects.requireNonNull(binding.textDestination.getText()).toString().trim();
        date = Objects.requireNonNull(binding.textDate.getText()).toString().trim();
        time = Objects.requireNonNull(binding.textTime.getText()).toString().trim();

        db = FirebaseFirestore.getInstance();
        Map<String, Object> myTrips = new HashMap<>();

        myTrips.put("Starting Point", startingPoint);
        myTrips.put("Destination", destination);
        myTrips.put("Date", date);
        myTrips.put("Time", time);
        myTrips.put("Status", "Current");
        myTrips.put("Email Address", getEmail());

        db.collection("MyTrips")
                .add(myTrips)
                .addOnSuccessListener(documentReference -> {
                    String tripID = documentReference.getId();
                    preferenceManager.putString(Constants.KEY_TRIP_ID, tripID);
                    Toast.makeText(AddNewRide.this, "Trip has been added", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                })
                .addOnFailureListener(e -> Toast.makeText(AddNewRide.this, "Failed to add vehicle", Toast.LENGTH_LONG).show());
    }

    private String getEmail() {
        String emailAddress;
        emailAddress = user.getEmail();
        return emailAddress;
    }

}