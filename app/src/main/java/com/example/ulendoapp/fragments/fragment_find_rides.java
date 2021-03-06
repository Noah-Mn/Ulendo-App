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
import java.util.Objects;

/**
 * The type Fragment find rides.
 */
public class fragment_find_rides extends Fragment {
    /**
     * The Get count.
     */
    MaterialSpinner getCount;
    /**
     * The Pickup point.
     */
    public TextInputEditText pickupPoint, /**
     * The Destination.
     */
    destination, /**
     * The Pickup date.
     */
    pickupDate;
    /**
     * The P point.
     */
    public String pPoint, /**
     * The Dest.
     */
    dest, /**
     * The P date.
     */
    pDate, /**
     * The Status.
     */
    status, /**
     * The People.
     */
    people, /**
     * The Lug.
     */
    lug;
    /**
     * The No people.
     */
    public long noPeople;
    /**
     * The Number of people.
     */
    public MaterialSpinner   numberOfPeople, /**
     * The Luggage.
     */
    luggage;
    /**
     * The Tag.
     */
    public final String TAG = "tag";

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private LatLng latLng;
    private Button findTripBtn;
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
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        pickupPoint = view.findViewById(R.id.trip_pickup_point);
        destination = view.findViewById(R.id.trip_destination);
        numberOfPeople = view.findViewById(R.id.trip_number_of_passengers);
        pickupDate = view.findViewById(R.id.user_trip_pickup_date);
        luggage = view.findViewById(R.id.trip_luggage);
        findTripBtn = view.findViewById(R.id.trip_find_rides_btn);
        latLng = new LatLng(latitude, longitude);


        findTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTripInfo();
                intent = new Intent(getContext(), BookingActivity.class);

                intent.putExtra("destination", dest);
                intent.putExtra("number of people", noPeople);
                intent.putExtra("pickup date", pDate);
                intent.putExtra("pickup point", pPoint);
                intent.putExtra("luggage", String.valueOf(luggage));
//                addTrip();
                startActivity(intent);

                Log.d(TAG, dest + " " + pPoint);
                Log.d(TAG, latitude + " " + longitude);
            }
        });

        setPassengerSpinner(view);
        setLuggageSpinner(view);
        return view;
    }

    /**
     * Get trip info.
     */
    public void getTripInfo(){
        dest = Objects.requireNonNull(destination.getText()).toString();
        pDate = Objects.requireNonNull(pickupDate.getText()).toString();
        people = numberOfPeople.getText().toString();
        noPeople = Integer.parseInt(people);
        pPoint = Objects.requireNonNull(pickupPoint.getText()).toString();
        lug = luggage.getText().toString();

    }

//    private void addTrip(){
//        getTripInfo();
//        db = FirebaseFirestore.getInstance();
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        Map<String, Object> findRide = new HashMap<>();
//
//        findRide.put("Email Address", getEmail());
//        findRide.put("Latitude", latitude);
//        findRide.put("Longitude", longitude);
//        findRide.put("Destination", dest);
//        findRide.put("Pickup Point", pPoint);
//        findRide.put("Luggage", lug);
//        findRide.put("Booked Seats", "N/A");
//        findRide.put("Complaint", "N/A");
//        findRide.put("Pickup Date", pDate);
//        findRide.put("Status", "N/A");
//        findRide.put("Date", "N/A");
//        findRide.put("Current Date", "N/A");
//
//
//        db.collection("Find Ride")
//                .add(findRide)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "inserted successfully");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "error! failed");
//                    }
//                });
//
//    }

    /**
     * Set passenger spinner.
     *
     * @param view the view
     */
    public void setPassengerSpinner(View view){
        getCount = view.findViewById(R.id.trip_number_of_passengers);
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

    /**
     * Set luggage spinner.
     *
     * @param view the view
     */
    public void setLuggageSpinner(View view){
        getCount = (MaterialSpinner)view.findViewById(R.id.trip_luggage);
        ArrayList<String> count = new ArrayList<String>();
        count.add("Small");
        count.add("Medium");
        count.add("Large");
        ArrayAdapter<String> countAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,count);
        countAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getCount.setAdapter(countAdapter);

    }

    /**
     * Get email string.
     *
     * @return the string
     */
    public String getEmail(){
        String emailAddress;
        emailAddress = user.getEmail();
        return emailAddress;
    }

}