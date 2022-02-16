package com.example.ulendoapp;

import static com.example.ulendoapp.fragment_offer_rides.latitude;
import static com.example.ulendoapp.fragment_offer_rides.longitude;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fragment_find_rides extends Fragment {
    MaterialSpinner getCount;
    public TextInputEditText pickupPoint, destination, pickupTime, dropPoint, complaint;
    public String pPoint, dest, pTime, seats, status, sInstructions, dPoint;
    public MaterialSpinner   numberOfSeats, luggage;
    public final String TAG = "tag";

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private LatLng latLng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_rides, container, false);

//        pickupPoint = view.findViewById(R.id.driver_start_point);
//        destination = view.findViewById(R.id.driver_destination);
//        pickupTime = view.findViewById(R.id.driver_pickup_time);
//        dropPoint = view.findViewById(R.id.drop_point);
//        numberOfSeats = view.findViewById(R.id.number_of_seats);
////        luggage = view.findViewById(R.id.driver_car_model);
//        complaint = view.findViewById(R.id.driver_special_instructions);

        latLng = new LatLng(latitude, longitude);


        setSpinner(view);
        return view;
    }
    private void addTrip(){
        db = FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Map<String, Object> trip = new HashMap<>();

        trip.put("Email Address", getEmail());
        trip.put("Location", latLng);
        trip.put("Destination", "N/A");
        trip.put("Pickup Point", "N/A");
        trip.put("Pickup Time", "N/A");
        trip.put("Luggage", "N/A");
        trip.put("Complaint", "N/A");
        trip.put("Status", "N/A");


        db.collection("Trip")
                .add(trip)
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

    public String getEmail(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String emailAddress;
        emailAddress = user.getEmail();
        return emailAddress;
    }

}