package com.example.ulendoapp.fragments;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ulendoapp.R;
import com.example.ulendoapp.adapters.VehicleAdapter;
import com.example.ulendoapp.models.Vehicles;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class fragment_offer_rides extends Fragment{
    MaterialSpinner getCount;
    private final String TAG =  "tag";
    public TextInputEditText pickupPoint, destination, pickupTime, pickupDate,  specialInstructions;
    public String pPoint, dest, pTime, pDate, car, sInstructions;
    public MaterialSpinner  numberOfSeats, carModel;
    public long seats;
    public static double latitude;
    public static double longitude;
    static String placeName;
    private Button offerBtn;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private MaterialSpinner getCarModel;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private TimePickerDialog timePickerDialog;
    private String amPm;
    private LatLng latLng;
    private FirebaseFirestore database;

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
        View view = inflater.inflate(R.layout.fragment_offer_rides, container, false);
        database = FirebaseFirestore.getInstance();
        pickupPoint = view.findViewById(R.id.ride_start_point);
        destination = view.findViewById(R.id.ride_destination);
        pickupTime = view.findViewById(R.id.ride_pickup_time);
        pickupDate = view.findViewById(R.id.ride_pickup_date);
        numberOfSeats = view.findViewById(R.id.ride_number_of_seats);
        carModel = view.findViewById(R.id.ride_car_model);
        specialInstructions = view.findViewById(R.id.ride_special_instructions);
        offerBtn = view.findViewById(R.id.ride_offer_btn);

        offerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRide();
                addGroup();
                Log.d(TAG, dest + " " + pPoint);
                Log.d(TAG, latitude + " " + longitude);
            }
        });
        setSpinner(view);
        setCarModelSpinner(view);
        loadTimePicker();
        return view;
    }

    public void loadTimePicker() {
        disableSoftInputFromAppearing(pickupTime);
        pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        pickupTime.setText(String.format("%02d : %02d", hourOfDay, minutes) + " " + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });
    }

    public static void disableSoftInputFromAppearing(TextInputEditText pTime) {
        if (Build.VERSION.SDK_INT >= 11) {
            pTime.setRawInputType(InputType.TYPE_NULL);
            pTime.setTextIsSelectable(true);
        } else {
            pTime.setRawInputType(InputType.TYPE_NULL);
            pTime.setFocusable(true);
        }
    }

    public void setCarModelSpinner(View view){
        getCarModel = (MaterialSpinner)view.findViewById(R.id.ride_car_model);
        ArrayList<String> count = new ArrayList<String>();

        database.collection("Driver Vehicles")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null){

                        for (QueryDocumentSnapshot document : task.getResult()){

                            String name = document.getString("Vehicle Brand");
                            count.add(name);
                            ArrayAdapter<String> countAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,count);
                            countAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            getCarModel.setAdapter(countAdapter);
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Failed to get Vehicles", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public void setSpinner(View view){
        getCount = (MaterialSpinner)view.findViewById(R.id.ride_number_of_seats);
        ArrayList<Long> count = new ArrayList<>();
        count.add(1l);
        count.add(2l);
        count.add(3l);
        count.add(4l);
        count.add(5l);
        count.add(6l);
        count.add(7l);
        count.add(8l);
        ArrayAdapter<Long> countAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item,count);
        countAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getCount.setAdapter(countAdapter);
    }

    public void getRideInfo(){
        pPoint = pickupPoint.getText().toString();
        dest = destination.getText().toString();
        pTime = pickupTime.getText().toString();
        pDate = pickupDate.getText().toString();
        seats = Integer.parseInt(numberOfSeats.getText().toString());
        car = carModel.getText().toString();
        sInstructions = specialInstructions.getText().toString();
//      dPoint = "your choice";

    }
    public String getEmail(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String emailAddress;
        emailAddress = user.getEmail();
        return emailAddress;
    }

    private void addRide(){
        getRideInfo();
        db = FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Map<String, Object> offerRide= new HashMap<>();
        offerRide.put("Email Address", getEmail());
        offerRide.put("Latitude", latitude);
        offerRide.put("Longitude", longitude);
        offerRide.put("Pickup Point", pPoint);
        offerRide.put("Destination", dest);
        offerRide.put("Pickup Time", pTime);
        offerRide.put("Date", pDate);
        offerRide.put("Number of seats", (long) seats);
        offerRide.put("Remaining Seats", (long) seats);
        offerRide.put("Luggage", "N/A");
        offerRide.put("Car Model", car);
        offerRide.put("State", "Available");
        offerRide.put("Special Instruction", sInstructions);
        offerRide.put("Current Date", "N/A");


        db.collection("Offer Ride")
                .add(offerRide)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Ride has been offered", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error! Failed to offer ride", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void addGroup(){
        db = FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Map<String, Object> group= new HashMap<>();

        group.put("Email Address", getEmail());
        group.put("Pickup Point", pPoint);
        group.put("Destination", dest);

        db.collection("Groups")
                .add(group)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "A group for your ride has been created", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to create group", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}