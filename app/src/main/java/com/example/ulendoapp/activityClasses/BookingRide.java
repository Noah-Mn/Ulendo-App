package com.example.ulendoapp.activityClasses;

import static com.example.ulendoapp.fragments.fragment_offer_rides.latitude;
import static com.example.ulendoapp.fragments.fragment_offer_rides.longitude;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ulendoapp.R;
import com.example.ulendoapp.models.DriverVehiclesModel;
import com.example.ulendoapp.models.FindRideModel;
import com.example.ulendoapp.models.OfferRideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingRide extends AppCompatActivity {
    private static final String TAG = "tag";
    private TextView dName, origin, destination, remainingSeats, date, pTime, sInstructions, bookedText;
    private String textName, textOrigin, textDestination, textDate, textTime, textInst;
    public long textSeats;
    private MaterialButton btnBook;
    private String driverName;
    private OfferRideModel tDetails;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private Snackbar snackbar;
    private FindRideModel fRideDetails;
    private long prevValue;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_ride);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        handler = new Handler();

        dName = findViewById(R.id.booking_driver_name_text);
        origin = findViewById(R.id.booking_origin_text);
        destination = findViewById(R.id.booking_destination_text);
        remainingSeats = findViewById(R.id.booking_remaining_seats_texts);
        date = findViewById(R.id.booking_trip_date_text);
        pTime = findViewById(R.id.booking_pickup_time_text);
        sInstructions = findViewById(R.id.booking_special_instruction_text);
        bookedText = findViewById(R.id.booked_ride_text);

        btnBook = findViewById(R.id.trip_book_btn);

        setText();
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTripId(view);
            }
        });
    }

    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

    public void getTripId(View view){
        db.collection("Offer Ride")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        progressDialog.dismiss();
                        for (DocumentSnapshot documentSnapshot: task.getResult()){
                            String tripId = documentSnapshot.getId();
                            checkRemainingSeats(tripId, view);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }


    public void checkRemainingSeats(String tripId, View view){
        getTripExtras();
        db.collection("Offer Ride")
                .whereEqualTo(FieldPath.documentId(), tripId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            long rSeats = documentSnapshot.getLong("Remaining Seats");
                            prevValue = rSeats;
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        long bookedSeats = Integer.parseInt(fRideDetails.getBookedSeats());

        long newValue = prevValue - bookedSeats;

        if(newValue >= 0){
            db.collection("Offer Ride")
                  .document( tDetails.getTripId())
                  .update("Remaining Seats", newValue)
                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {
                          Log.d(TAG, "DocumentSnapshot successfully updated!");
                          remainingSeats.setText(String.valueOf(newValue));
                          snackbar = Snackbar.make(view, "You have successfully requested to book this ride", Snackbar.LENGTH_LONG);
                          snackbar.show();
                          addBookedTrip();

                          handler.postDelayed(new Runnable() {
                              @Override
                              public void run() {
                                  BookingRide.super.onBackPressed();
                              }
                          }, 3000);
                      }
                  })
                  .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Log.w(TAG, "Error updating document", e);
                      }
                  });
        }
    }


    private void addBookedTrip(){
        getTripExtras();
        db = FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Map<String, Object> bookedTrip = new HashMap<>();

        bookedTrip.put("Driver Email Address", tDetails.getEmailAddress());
        bookedTrip.put("Passenger Email Address", getEmail());
        bookedTrip.put("Number of Passengers", fRideDetails.getBookedSeats());
        bookedTrip.put("Trip Id", tDetails.getTripId());
        bookedTrip.put("Origin", tDetails.getPickupPoint());
        bookedTrip.put("Destination", tDetails.getDestination());
        bookedTrip.put("Booked Date", tDetails.getPickupDate());
        bookedTrip.put("Current Date", tDetails.getCurrDate());
        bookedTrip.put("Booking Status", "pending");

        db.collection("Booking Ride")
                .add(bookedTrip)
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


    public void setText(){
        getTripExtras();
        getDriverName();
//        getRemainingSeats();

        textOrigin =  tDetails.getPickupPoint();
        textDestination =  tDetails.getDestination();
        textDate =  tDetails.getPickupDate();
        textTime =  tDetails.getPickupTime();
        textInst =  tDetails.getSpecialInstructions();
        remainingSeats.setText(String.valueOf(tDetails.getRemainingSeats()));

        origin.setText(textOrigin);
        destination.setText(textDestination);
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
        fRideDetails = (FindRideModel) intent.getSerializableExtra("findRideDetails");

        return tDetails;
    }
}