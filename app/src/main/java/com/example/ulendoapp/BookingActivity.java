package com.example.ulendoapp;

import static com.example.ulendoapp.HomeUser.userModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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


//    private List<> carDetailModelList;
    public OfferRideModel trip;
    public DriverVehiclesModel carDetail;
    private TextView displayText;
    private FirebaseFirestore db;
    public RecyclerView recyclerViewTrip;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    public String tripPickupTime;
    public List<String> driverEmail;

    private final String TAG = "tag";
    public String dest, pTime, noPeople, sInstructions, pPoint, luggage;
    public List<OfferRideModel> filteredOffers;
    private List<OfferRideModel> offerRideModelList;
//    private List<OfferRideModel> offerRide;
    List<DriverVehiclesModel> carDetailModelList;
    List<FindRideModel> findRideModelList;
    public Intent intent;
    private ArrayList<DriverVehiclesModel> filteredCarDetail;

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
        carDetailModelList = new ArrayList<>();
        findRideModelList = new ArrayList<>();

        getFindRideExtras();
        getOfferRideData();
        getFindRideData();
        bookingActivity();
//        setDisplayText();
//        getFilteredRides();
    }

    public void setDisplayText(){
        displayText = findViewById(R.id.display_name);
        displayText.setTextSize(19);
        int z1,z,z3,z4,z5;
        z = offerRideModelList.size();
        z1 = driverEmail.size();
        z3 = carDetailModelList.size();

        String diz = z + ", "+ z1 + ", " + z3;

        if(filteredOffers.size() != 0){
            displayText.setText(new StringBuilder().append("Hey").append(" ").append(userModel.getFirstName()).append(", ")
                    .append(diz).append("  :we found these rides for you").toString());

        } else{
            displayText.setText(new StringBuilder().append("Hey").append(" ").append(userModel.getFirstName())
                    .append(", ").append(diz).append("  :No ride match your search").toString());
        }


//        Toast.makeText(BookingActivity.this, offerRideModelList.size() + " damn!!", Toast.LENGTH_SHORT).show();
//        Toast.makeText(BookingActivity.this, carDetailModelList.size() + " damn!!", Toast.LENGTH_SHORT).show();

    }

    public void getFindRideExtras(){
        Intent intent = getIntent();

        dest = intent.getStringExtra("destination");
        pTime = intent.getStringExtra("pickup time");
        noPeople = intent.getStringExtra("number of people");
        sInstructions = intent.getStringExtra("special instruction");
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
                            String email = documentSnapshot.getString("Email");


                            OfferRideModel offeredRide = new OfferRideModel(latitude, longitude, pickupPoint, destination, pickupTime,
                                    numberOfSeats, bookedSeats, luggage, state, date, currDate, email);

                            offerRideModelList.add(offeredRide);

                            getFilteredRides();
//                            setDisplayText();
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

    public void getFindRideData() {
            db.collection("Driver Vehicles")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        progressDialog.dismiss();
                            carDetailModelList.clear();
                            for (DocumentSnapshot documentSnapshot: task.getResult()){

                                String vBrand = documentSnapshot.getString("Vehicle Brand");
                                String vModel = documentSnapshot.getString("Vehicle Model");
                                String vModelYr = documentSnapshot.getString("Model Year");
                                String vColor = documentSnapshot.getString("Vehicle Color");

                                DriverVehiclesModel carDetail = new DriverVehiclesModel(vBrand, vModel, vModelYr, vColor);

                                carDetailModelList.add(carDetail);
                                getFilteredRides();
//                                setDisplayText();
//                                Toast.makeText(BookingActivity.this, carDetailModelList.get(0).getVehicleBrand(), Toast.LENGTH_SHORT).show();
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
//        filteredCarDetail = new ArrayList<>();
        filteredOffers = new ArrayList<>();
        for(int i = 0; i < offerRideModelList.size(); i++){
            if(offerRideModelList.get(i).getPickupPoint().matches(pPoint)){
                String pickupPoint = offerRideModelList.get(i).getPickupPoint();
                String destination = offerRideModelList.get(i).getPickupPoint();
                String email = offerRideModelList.get(i).getEmailAddress();
                String pickupTime = offerRideModelList.get(i).getPickupTime();

                OfferRideModel newOffer = new OfferRideModel(pickupPoint, destination, pickupTime, email);

                filteredOffers.add(newOffer);
            }

            /************ not uziful for nou****************/
//                driverEmail.clear();
//                for(int j = 0; j < filteredOffers.size(); j++){
//                    driverEmail.add(filteredOffers.get(j).getEmailAddress());
//                }

        }

        BookRideAdapter adapter = new BookRideAdapter(filteredOffers, carDetailModelList, BookingActivity.this);
        recyclerViewTrip.setAdapter(adapter);
        recyclerViewTrip.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        setDisplayText();

        Toast.makeText(BookingActivity.this, carDetailModelList.size() + "  damn prt 4  "+ filteredOffers.size(), Toast.LENGTH_SHORT).show();
    }

   /* public void getCarDetailData() {
        db.collection("Find Ride")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        progressDialog.dismiss();
                        carDetailModelList.clear();
                        for (DocumentSnapshot documentSnapshot: task.getResult()){

                            String destination = documentSnapshot.getString("Destination");
                            String pickupPoint = documentSnapshot.getString("Pickup Point");
                            String pickupTime = documentSnapshot.getString("Pickup Time");
                            String luggage = documentSnapshot.getString("Luggage");
                            String bookedSeats = documentSnapshot.getString("Booked Seats");
                            String latLng = String.valueOf(documentSnapshot.getString("Location"));

                            DriverVehiclesModel carDetail = new DriverVehiclesModel();

                            carDetailModelList.add(carDetail);

                            getFilteredRides();
                            Toast.makeText(BookingActivity.this, carDetailModelList.get(0).getVehicleBrand(), Toast.LENGTH_SHORT).show();

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
*/

    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

    @Override
    public void onTripClick(int position) {

    }
}