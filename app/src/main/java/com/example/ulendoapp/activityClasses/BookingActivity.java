package com.example.ulendoapp.activityClasses;

import static com.example.ulendoapp.activityClasses.HomeUser.userModel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.adapters.BookRideAdapter;
import com.example.ulendoapp.models.FindRideModel;
import com.example.ulendoapp.models.OfferRideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity implements BookRideAdapter.OnTripClickListener{

    private TextView displayText;
    private FirebaseFirestore db;
    public RecyclerView recyclerViewTrip;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private final String TAG = "tag";
    public String dest, pTime, noPeople, pDate, pPoint, luggage;
    public ArrayList<OfferRideModel> filteredOffers;
    public FindRideModel findRideDetails;
    private List<OfferRideModel> offerRideModelList;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        intent = getIntent();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        recyclerViewTrip = findViewById(R.id.booking_trip_lists);

        offerRideModelList = new ArrayList<>();
        getFindRideExtras();
        getOfferRideData();
    }

    public void setDisplayText(){
        displayText = findViewById(R.id.display_name);
        displayText.setTextSize(17);

        if(filteredOffers.size() != 0){
            displayText.setText(new StringBuilder().append("Hey").append(" ").append(userModel.getFirstName()).append(", ").append("we found these rides for you").toString());

        } else{
            displayText.setText(new StringBuilder().append("Hey").append(" ").append(userModel.getFirstName())
                    .append(", ").append("No ride match your search").toString());
        }

    }

    public void getFindRideExtras(){
        Intent intent = getIntent();

        dest = intent.getStringExtra("destination");
        pTime = intent.getStringExtra("pickup time");
        pDate = intent.getStringExtra("pickup date");
        noPeople = intent.getStringExtra("number of people");
        pPoint = intent.getStringExtra("pickup point");
        luggage = intent.getStringExtra("luggage");

        findRideDetails = new FindRideModel(dest, pTime, pDate, noPeople, pPoint, luggage);

//        Toast.makeText(BookingActivity.this, dest + " => " + pPoint, Toast.LENGTH_SHORT).show();

    }

    public void getOfferRideData(){
        db.collection("Offer Ride")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        progressDialog.dismiss();
                        offerRideModelList.clear();
                        for (DocumentSnapshot documentSnapshot: task.getResult()){

                            String destination = documentSnapshot.getString("Destination");
                            String pickupPoint = documentSnapshot.getString("Pickup Point");
                            String pickupTime = documentSnapshot.getString("Pickup Time");
                            String luggage = documentSnapshot.getString("Luggage");
                            long remainingSeats = documentSnapshot.getLong("Remaining Seats");
                            double latitude = documentSnapshot.getDouble("Latitude");
                            double longitude = documentSnapshot.getDouble("Longitude");
                            long numberOfSeats = documentSnapshot.getLong("Number of seats");
                            String state = documentSnapshot.getString("State");
                            String date = documentSnapshot.getString("Date");
                            String currDate = documentSnapshot.getString("Current Date");
                            String email = documentSnapshot.getString("Email Address");
                            String sInst = documentSnapshot.getString("Special Instruction");
                            String tripId = documentSnapshot.getId();


                            OfferRideModel offeredRide = new OfferRideModel(pickupPoint, destination, pickupTime, date, email, tripId, luggage,
                                    remainingSeats, latitude, longitude, numberOfSeats, state, date, currDate, sInst);

                            Toast.makeText(BookingActivity.this, tripId, Toast.LENGTH_SHORT).show();

                            offerRideModelList.add(offeredRide);
                            getFilteredRides();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void getFilteredRides(){
        getFindRideExtras();
        filteredOffers = new ArrayList<>();
        for(int i = 0; i < offerRideModelList.size(); i++){
            if(offerRideModelList.get(i).getPickupPoint().matches(pPoint)){
                String pickupPoint = offerRideModelList.get(i).getPickupPoint();
                String destination = offerRideModelList.get(i).getDestination();
                String email = offerRideModelList.get(i).getEmailAddress();
                String pickupTime = offerRideModelList.get(i).getPickupTime();
                String pickupDate = offerRideModelList.get(i).getPickupDate();
                String tripId = offerRideModelList.get(i).getTripId();
                String luggage =  offerRideModelList.get(i).getLuggage();
                long remainingSeats =  offerRideModelList.get(i).getRemainingSeats();
                double latitude =  offerRideModelList.get(i).getLatitude();
                double longitude =  offerRideModelList.get(i).getLongitude();
                long numberOfSeats =  offerRideModelList.get(i).getNumberOfSeats();
                String state =  offerRideModelList.get(i).getState();
                String date =  offerRideModelList.get(i).getPickupDate();
                String currDate =  offerRideModelList.get(i).getCurrDate();
                String sInst = offerRideModelList.get(i).getSpecialInstructions();

                OfferRideModel newOffer = new OfferRideModel(pickupPoint, destination, pickupTime, pickupDate, email, tripId, luggage,
                        remainingSeats, latitude, longitude, numberOfSeats, state, date, currDate, sInst);

                filteredOffers.add(newOffer);
            }

        }

        BookRideAdapter adapter = new BookRideAdapter(filteredOffers, BookingActivity.this, findRideDetails , this);
        recyclerViewTrip.setAdapter(adapter);
        recyclerViewTrip.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        setDisplayText();

        Toast.makeText(BookingActivity.this,  "  damn  "+ filteredOffers.size(), Toast.LENGTH_SHORT).show();
    }

    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

    @Override
    public void onTripClick(int position) {
        if (position >= 0){

        }
    }
}