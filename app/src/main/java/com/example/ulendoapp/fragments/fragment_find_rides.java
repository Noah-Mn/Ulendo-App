package com.example.ulendoapp.fragments;

import static com.example.ulendoapp.fragments.fragment_offer_rides.disableSoftInputFromAppearing;
import static com.example.ulendoapp.fragments.fragment_offer_rides.latitude;
import static com.example.ulendoapp.fragments.fragment_offer_rides.longitude;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.ulendoapp.R;
import com.example.ulendoapp.activityClasses.BookingActivity;
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
    public TextInputEditText pickupPoint, destination, pickupTime, pickupDate;
    public String pPoint, dest, pTime, pDate, noPeople, status;
    public MaterialSpinner   numberOfPeople, luggage;
    public final String TAG = "tag";

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private LatLng latLng;
    private Button findTripBtn;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private TimePickerDialog timePickerDialog;
    private String amPm;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_rides, container, false);

        pickupPoint = view.findViewById(R.id.trip_pickup_point);
        destination = view.findViewById(R.id.trip_destination);
        pickupTime = view.findViewById(R.id.trip_pickup_time);
        pickupDate = view.findViewById(R.id.trip_pickup_date);
        numberOfPeople = view.findViewById(R.id.trip_number_of_passengers);
        luggage = view.findViewById(R.id.trip_luggage);
        findTripBtn = view.findViewById(R.id.trip_find_rides_btn);
        latLng = new LatLng(latitude, longitude);


        findTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTripInfo();
                intent = new Intent(getContext(), BookingActivity.class);

                intent.putExtra("destination", dest);
                intent.putExtra("pickup time", pTime);
                intent.putExtra("number of people", noPeople);
                intent.putExtra("pickup date", pDate);
                intent.putExtra("pickup point", pPoint);
                intent.putExtra("luggage", String.valueOf(luggage));
                addTrip();
                startActivity(intent);

                Log.d(TAG, dest + " " + pPoint);
                Log.d(TAG, latitude + " " + longitude);
            }
        });

        setSpinner(view);
        loadTimePicker();
        return view;
    }

    public void getTripInfo(){
        dest = destination.getText().toString();
        pTime = pickupTime.getText().toString();
        pDate = pickupDate.getText().toString();
        noPeople = numberOfPeople.getText().toString();
        pPoint = pickupPoint.getText().toString();
        luggage = null;

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


    private void addTrip(){
        getTripInfo();
        db = FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Map<String, Object> findRide = new HashMap<>();

        findRide.put("Email Address", getEmail());
        findRide.put("Latitude", latitude);
        findRide.put("Longitude", longitude);
        findRide.put("Destination", dest);
        findRide.put("Pickup Point", pPoint);
        findRide.put("Pickup Time", pTime);
        findRide.put("Luggage", luggage);
        findRide.put("Booked Seats", "N/A");
        findRide.put("Complaint", "N/A");
        findRide.put("Pickup Date", pDate);
        findRide.put("Status", "N/A");
        findRide.put("Date", "N/A");
        findRide.put("Current Date", "N/A");


        db.collection("Find Ride")
                .add(findRide)
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
        getCount = (MaterialSpinner)view.findViewById(R.id.trip_number_of_passengers);
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