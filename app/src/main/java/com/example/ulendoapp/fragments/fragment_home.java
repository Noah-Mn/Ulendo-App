package com.example.ulendoapp.fragments;

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

/**
 * The type Fragment home.
 */
public class fragment_home extends Fragment {
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
    pickupDate, /**
     * The Drop point.
     */
    dropPoint, /**
     * The Special instructions.
     */
    specialInstructions;
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
     * The No people.
     */
    noPeople, /**
     * The Status.
     */
    status, /**
     * The S instructions.
     */
    sInstructions, /**
     * The D point.
     */
    dPoint;
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        pickupPoint = view.findViewById(R.id.user_trip_start_point);
        destination = view.findViewById(R.id.user_trip_detination);
        numberOfPeople = view.findViewById(R.id.user_trip_number_of_passengers);
        luggage = view.findViewById(R.id.user_trip_luggage);
        findTripBtn = view.findViewById(R.id.user_trip_find_btn);
        pickupDate =  view.findViewById(R.id.user_trip_pickup_date);

        latLng = new LatLng(latitude, longitude);

        findTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTripInfo();
                intent = new Intent(getContext(), BookingActivity.class);
                intent.putExtra("destination", dest);
                intent.putExtra("pickup time", pDate);
                intent.putExtra("number of people", noPeople);
                intent.putExtra("pickup point", pPoint);
                intent.putExtra("luggage", String.valueOf(luggage));
                startActivity(intent);
                Log.d(TAG, dest + " " + pPoint);
                Log.d(TAG, latitude + " " + longitude);

            }
        });

        setPassengerSpinner(view);
        setLuggageSpinner(view);
//        loadTimePicker();
       return view;
    }

    /**
     * Get trip info.
     */
    public void getTripInfo(){
        dest = destination.getText().toString();
        noPeople = numberOfPeople.getText().toString();
//        sInstructions = specialInstructions.getText().toString();
        pDate = pickupDate.getText().toString();
        pPoint = pickupPoint.getText().toString();
        dPoint = "your choice";
        luggage = null;
    }

    /*public void loadTimePicker() {
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
    }*/

   /* private void addTrip(){
//        getTripInfo();
        db = FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Map<String, Object> trip = new HashMap<>();

        trip.put("Email Address", getEmail());
        trip.put("Location", latLng);
        trip.put("Destination", dest);
        trip.put("Pickup Point", pPoint);
        trip.put("Pickup Date", pDate);
        trip.put("Luggage", luggage);
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

    }*/

    /**
     * Set passenger spinner.
     *
     * @param view the view
     */
    public void setPassengerSpinner(View view){
        getCount = (MaterialSpinner)view.findViewById(R.id.user_trip_number_of_passengers);
        ArrayList<String> count = new ArrayList<String>();
        count.add("1");
        count.add("2");
        count.add("3");
        count.add("4");
        count.add("5");
        count.add("6");
        ArrayAdapter<String> countAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,count);
        countAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getCount.setAdapter(countAdapter);
    }

    /**
     * Set luggage spinner.
     *
     * @param view the view
     */
    public void setLuggageSpinner(View view){
        getCount = (MaterialSpinner)view.findViewById(R.id.user_trip_luggage);
        ArrayList<String> count = new ArrayList<String>();
        count.add("None");
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
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String emailAddress;
        emailAddress = user.getEmail();
        return emailAddress;
    }
    
}