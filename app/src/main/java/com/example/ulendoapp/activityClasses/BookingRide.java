package com.example.ulendoapp.activityClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ulendoapp.R;
import com.example.ulendoapp.models.DriverVehiclesModel;
import com.example.ulendoapp.models.OfferRideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookingRide extends AppCompatActivity {
    TextView dName, origin, destination, remainingSeats, date, pTime, sInstructions;
    String textName;
    String textOrigin;
    String textDestination;
    String textSeats;
    String textDate;
    String textTime;
    String textInst;
    TextView tName, tOrigin, tDestination, tSeats, tDate, tTime, tInst;
    MaterialButton btnBook;
    OfferRideModel tripDetails;
    String driverName;
    private OfferRideModel tDetails;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_ride);
        db = FirebaseFirestore.getInstance();

        dName = findViewById(R.id.booking_driver_name_text);
        origin = findViewById(R.id.booking_origin_text);
        destination = findViewById(R.id.booking_destination_text);
        remainingSeats = findViewById(R.id.booking_remaining_seats_texts);
        date = findViewById(R.id.booking_trip_date_text);
        pTime = findViewById(R.id.booking_pickup_time_text);
        sInstructions = findViewById(R.id.booking_special_instruction_text);

        btnBook = findViewById(R.id.trip_book_btn);

        setText();
    }

    public void setText(){
        getTripExtras();
        getDriverName();

        textOrigin =  tDetails.getPickupPoint();
        textDestination =  tDetails.getDestination();
        textSeats =  tDetails.getNumberOfSeats();
        textDate =  tDetails.getPickupDate();
        textTime =  tDetails.getPickupTime();
        textInst =  tDetails.getSpecialInstructions();

        origin.setText(textOrigin);
        destination.setText(textDestination);
        remainingSeats.setText(textSeats);
        date.setText(textDate);
        pTime.setText(textTime);
        sInstructions.setText(textInst);

//        Toast.makeText(this, tripDetails.getTripId(), Toast.LENGTH_LONG).show();
    }

    private void getDriverName() {
        db.collection("Users")
                .whereEqualTo("Email Address", tDetails.getEmailAddress())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        progressDialog.dismiss();
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {

                            String fName = documentSnapshot.getString("First Name");
                            String surname = documentSnapshot.getString("Surname");

                            driverName = fName + " " + surname;
                            dName.setText(driverName);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }


    public OfferRideModel getTripExtras() {
        Intent intent = getIntent();

        tDetails = (OfferRideModel) intent.getSerializableExtra("tripDetails");
        Toast.makeText(this, tDetails.getTripId(), Toast.LENGTH_LONG).show();

        return tDetails;
    }
}