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
import com.example.ulendoapp.models.BookRequest;
import com.example.ulendoapp.models.BookingModel;
import com.example.ulendoapp.models.DriverVehiclesModel;
import com.example.ulendoapp.models.FindRideModel;
import com.example.ulendoapp.models.OfferRideModel;
import com.example.ulendoapp.models.User;
import com.example.ulendoapp.network.ApiClient;
import com.example.ulendoapp.network.ApiService;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingRide extends AppCompatActivity {
    private static final String TAG = "tag";
    private TextView dName, origin, destination, remainingSeats, date, pTime, sInstructions, bookedText;
    private String passengerName, textOrigin, textDestination, textDate, textTime, textInst;
    public long textSeats;
    private MaterialButton btnBook;
    private String driverName;
    private OfferRideModel tDetails;
    private List<BookRequest> bookRequests;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private Snackbar snackbar;
    private FindRideModel fRideDetails;
    private long prevValue;
    private Handler handler;
    private long newValue;
    private long bookedSeats;
    private String userEmail;
    private String bookingId;
    private String tripId;
    private boolean accept;
    User receiver;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_ride);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        handler = new Handler();
        preferenceManager = new PreferenceManager(getApplicationContext());

        bookRequests = new ArrayList<>();
        receiver = new User();
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
                getBookingDetails();
                checkRemainingSeats(view);
                sendBookingRequest();
                getPassengerName();
            }
        });
    }

    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

    public void setIntent(){

    }

    public void checkRemainingSeats(View view){
        getTripExtras();
        db.collection("Offer Ride")
                .whereEqualTo(FieldPath.documentId(), tDetails.getTripId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            prevValue = documentSnapshot.getLong("Remaining Seats");
                            updateSeats(view, prevValue);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    public void getBookingDetails(){
        getTripExtras();
        db.collection("Booking Ride")
                .whereEqualTo(FieldPath.documentId(), tDetails.getTripId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            userEmail = documentSnapshot.getString("Passenger Email Address");
//                            bookingId = documentSnapshot.getId();
                            tripId = documentSnapshot.getString("Trip Id");

                            checkBookedRide(userEmail, tripId);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    public  void checkBookedRide(String email, String id){
        if(email == getEmail() && id == tDetails.getTripId()){
            btnBook.setVisibility(View.INVISIBLE);

        } else{
            accept = true;
        }
    }

    private void updateSeats(View view, long prevValue) {
        bookedSeats = Integer.parseInt(fRideDetails.getBookedSeats());
        newValue = prevValue - bookedSeats;

        if(newValue >= 0){
            db.collection("Offer Ride")
                    .document( tDetails.getTripId())
                    .update("Remaining Seats", newValue)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");
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
        } else{
            snackbar = Snackbar.make(view, "The ride is out of seats", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void setSeatText(){
        getTripExtras();
        db.collection("Offer Ride")
                .whereEqualTo(FieldPath.documentId(), tDetails.getTripId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            long rSeats = documentSnapshot.getLong("Remaining Seats");
                            remainingSeats.setText(String.valueOf(rSeats));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

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
        bookedTrip.put(Constants.KEY_PASSENGER_NAME, preferenceManager.getString(Constants.KEY_PASSENGER_NAME));
        bookedTrip.put("Booking Status", "pending");
        bookedTrip.put(Constants.KEY_T_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        bookedTrip.put(Constants.KEY_T_RECEIVER_ID, preferenceManager.getString(Constants.KEY_T_RECEIVER_ID));

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

        textOrigin =  tDetails.getPickupPoint();
        textDestination =  tDetails.getDestination();
        textDate =  tDetails.getPickupDate();
        textTime =  tDetails.getPickupTime();
        textInst =  tDetails.getSpecialInstructions();
        setSeatText();

        origin.setText(textOrigin);
        destination.setText(textDestination);
        date.setText(textDate);
        pTime.setText(textTime);
        sInstructions.setText(textInst);
//        Toast.makeText(this, tripDetails.getTripId(), Toast.LENGTH_LONG).show();

        BookingModel bookingModel = new BookingModel();
        bookingModel.setOrigin(textOrigin);
        bookingModel.setDest(textDestination);
        bookingModel.setPassengerName(preferenceManager.getString(Constants.KEY_PASSENGER_NAME));

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
    private void getPassengerName() {
        db.collection("Users")
                .whereEqualTo("Email Address", getEmail())
                .whereEqualTo(Constants.KEY_T_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        progressDialog.dismiss();
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {

                            String fName = documentSnapshot.getString("First Name");
                            String surname = documentSnapshot.getString("Surname");

                            passengerName = fName + " " + surname;
                            preferenceManager.putString(Constants.KEY_PASSENGER_NAME, passengerName);
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
    private void sendBookingRequest(){
        db.collection("Booking Ride")
                .whereEqualTo(Constants.KEY_T_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .whereEqualTo(Constants.KEY_T_RECEIVER_ID, preferenceManager.getString(Constants.KEY_T_RECEIVER_ID))
                .addSnapshotListener(eventListener);
        db.collection("Booking Ride")
                .whereEqualTo(Constants.KEY_T_SENDER_ID, preferenceManager.getString(Constants.KEY_T_RECEIVER_ID))
                .whereEqualTo(Constants.KEY_T_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }
    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }if (value != null){
           for(DocumentChange documentChange : value.getDocumentChanges()){
               if (documentChange.getType() ==  DocumentChange.Type.ADDED){
                   BookRequest bookRequest = new BookRequest();
                   bookRequest.senderId = documentChange.getDocument().getString(Constants.KEY_T_SENDER_ID);
                   bookRequest.receiverId = documentChange.getDocument().getString(Constants.KEY_T_RECEIVER_ID);
                   bookRequest.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
                   bookRequests.add(bookRequest);
               }
           }
        }
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(receiver.token);

            JSONObject data = new JSONObject();
            data.put(Constants.KEY_T_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
            data.put(Constants.KEY_FCM_TOKEN, preferenceManager.getString(Constants.KEY_FCM_TOKEN));
            data.put(Constants.KEY_MESSAGE, "Booking Request");

            JSONObject body = new JSONObject();
            body.put(Constants.REMOTE_MSG_DATA, data);
            body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);

            sendNotification(body.toString());
        }catch (Exception exception){
            showToast(exception.getMessage());
        }
    };
private void sendNotification(String messageBody){
    ApiClient.getClient().create(ApiService.class).sendMessage(
            Constants.getRemoteMsgHeaders(),
            messageBody
    ).enqueue(new Callback<String>() {
        @Override
        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
            if (response.isSuccessful()){
                try {
                    if (response.body() != null){
                        JSONObject responseJson = new JSONObject(response.body());
                        JSONArray results = responseJson.getJSONArray("results");
                        if (responseJson.getInt("failure") == 1){
                            JSONObject error = (JSONObject) results.get(0);
                            showToast(error.getString("error"));
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                showToast("Notification sent successfully");
            }else {
                showToast("Error: " + response.code());
            }
        }

        @Override
        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
            showToast(t.getMessage());
        }
    });

}
    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}