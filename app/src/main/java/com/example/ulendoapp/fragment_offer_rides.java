package com.example.ulendoapp;


import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.okhttp.Address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class fragment_offer_rides extends Fragment{
    MaterialSpinner getCount;
    private final String TAG =  "tag";
    public TextInputEditText pickupPoint, destination, pickupTime, carModel, specialInstructions;
    public String pPoint, dest, pTime, seats, car, sInstructions, dPoint;
    public MaterialSpinner   numberOfSeats;
    static double latitude, longitude;
    static String placeName;
    private Button offerBtn;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser user;


    public fragment_offer_rides() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_rides, container, false);

        pickupPoint = view.findViewById(R.id.driver_start_point);
        destination = view.findViewById(R.id.driver_destination);
        pickupTime = view.findViewById(R.id.driver_pickup_time);
        numberOfSeats = view.findViewById(R.id.number_of_seats);
        carModel = view.findViewById(R.id.driver_car_model);
        specialInstructions = view.findViewById(R.id.driver_special_instructions);
        offerBtn = view.findViewById(R.id.offer_rides_btn);
        offerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offerRide();
                Log.d(TAG, dest + " " + pPoint);
                Log.d(TAG, latitude + " " + longitude);
            }
        });
        setSpinner(view);
        return view;
    }

    public void setSpinner(View view){
        getCount = (MaterialSpinner)view.findViewById(R.id.number_of_seats);
        ArrayList<String> count = new ArrayList<String>();
        count.add("1");
        count.add("2");
        count.add("3");
        count.add("4");
        count.add("5");
        count.add("6");
        count.add("7");
        count.add("8");
        ArrayAdapter<String> countAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,count);
        countAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getCount.setAdapter(countAdapter);
    }

    public void getTripInfo(){
      pPoint = pickupPoint.getText().toString();
      dest = destination.getText().toString();
      pTime = pickupTime.getText().toString();
      seats = numberOfSeats.getText().toString();
      car = carModel.getText().toString();
      sInstructions = pickupPoint.getText().toString();
      dPoint = "your choice";

    }
    public String getEmail(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String emailAddress;
        emailAddress = user.getEmail();
        return emailAddress;
    }

    private void offerRide(){
        getTripInfo();
        db = FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Map<String, Object> ride = new HashMap<>();

        ride.put("Email Address", getEmail());
        ride.put("Location", "N/A");
        ride.put("Destination", dest);
        ride.put("Number of seats", seats);
        ride.put("Pickup Point", pPoint);
        ride.put("Drop Point", dPoint);
        ride.put("Pickup Time", pTime);
        ride.put("Car Model", car);

        db.collection("Ride")
                .add(ride)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "inserted successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "error! failed");
                    }
                });

    }

}