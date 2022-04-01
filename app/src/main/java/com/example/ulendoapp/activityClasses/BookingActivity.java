package com.example.ulendoapp.activityClasses;

import static com.example.ulendoapp.activityClasses.HomeUser.userModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.adapters.BookRideAdapter;
import com.example.ulendoapp.listeners.RideListener;
import com.example.ulendoapp.models.OfferRideModel;
import com.example.ulendoapp.utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity implements RideListener {

    private TextView displayText;
    private FirebaseFirestore db;
    public RecyclerView recyclerViewTrip;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    public List<String> driverEmail;
    private BookRideAdapter adapter;
    private final String TAG = "tag";
    public String dest, pTime, noPeople, pDate, pPoint, luggage;
    public ArrayList<OfferRideModel> filteredOffers;
    private List<OfferRideModel> offerRideModelList;
//    private List<FindRideModel> findRideModelList;
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
        driverEmail = new ArrayList<>();
        offerRideModelList = new ArrayList<>();
//        findRideModelList = new ArrayList<>();

        getFindRideExtras();
        getOfferRideData();
        bookingActivity();

    }

    public void setDisplayText(){
        displayText = findViewById(R.id.display_name);
        displayText.setTextSize(17);
        int z1,z,z3,z4,z5;
        z = offerRideModelList.size();

        String diz = "list " + z ;

        if(filteredOffers.size() != 0){
            displayText.setText(new StringBuilder().append("Hey").append(" ").append(userModel.getFirstName()).append(", ")
                    .append(diz).append("we found these rides for you").toString());

        } else{
            displayText.setText(new StringBuilder().append("Hey").append(" ").append(userModel.getFirstName())
                    .append(", ").append(diz).append("No ride match your search").toString());
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

//        Toast.makeText(BookingActivity.this, dest + " => " + pPoint, Toast.LENGTH_SHORT).show();

    }

    public void bookingActivity(){

//        Toast.makeText(BookingActivity.this, "dead brooo", Toast.LENGTH_SHORT).show();
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
                            String bookedSeats = documentSnapshot.getString("Booked Seats");
                            double latitude = documentSnapshot.getDouble("Latitude");
                            double longitude = documentSnapshot.getDouble("Longitude");
                            String numberOfSeats = documentSnapshot.getString("Number of seats");
                            String state = documentSnapshot.getString("State");
                            String date = documentSnapshot.getString("Date");
                            String currDate = documentSnapshot.getString("Current Date");
                            String email = documentSnapshot.getString("Email Address");


                            OfferRideModel offeredRide = new OfferRideModel(latitude, longitude, pickupPoint, destination, pickupTime,
                                    numberOfSeats, bookedSeats, luggage, state, date, currDate, email);

                            Toast.makeText(BookingActivity.this, email, Toast.LENGTH_SHORT).show();

                            offerRideModelList.add(offeredRide);
                            driverEmail.add(email);
                            for(int i = 0; i < driverEmail.size(); i++){
                                if(driverEmail.get(i).equals(email)){
                                    driverEmail.remove(i);
                                }
                            }

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
        OfferRideModel offerRideModel = new OfferRideModel();
        filteredOffers = new ArrayList<>();
        for(int i = 0; i < offerRideModelList.size(); i++){
            if(offerRideModelList.get(i).getPickupPoint().matches(pPoint)){
                offerRideModel.pickupPoint = offerRideModelList.get(i).getPickupPoint();
                offerRideModel.destination = offerRideModelList.get(i).getDestination();
                offerRideModel.emailAddress = offerRideModelList.get(i).getEmailAddress();
                offerRideModel.pickupTime = offerRideModelList.get(i).getPickupTime();
                offerRideModel.pickupDate = offerRideModelList.get(i).getPickupDate();
                filteredOffers.add(offerRideModel);
//                String pickupPoint = offerRideModelList.get(i).getPickupPoint();
//                String destination = offerRideModelList.get(i).getDestination();
//                String email = offerRideModelList.get(i).getEmailAddress();
//                String pickupTime = offerRideModelList.get(i).getPickupTime();
//                String pickupDate = offerRideModelList.get(i).getPickupDate();
//                OfferRideModel newOffer = new OfferRideModel(pickupPoint, destination, pickupTime, pickupDate, email);
//                filteredOffers.add(newOffer);
            }

        }
        adapter = new BookRideAdapter(filteredOffers, this);
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
    public void onRideClick(OfferRideModel offerRideModel) {
        Intent intent = new Intent(getApplicationContext(), BookingRide.class);
        intent.putExtra(Constants.KEY_TRIP_ID, offerRideModel);
        startActivity(intent);
        finish();
    }
}