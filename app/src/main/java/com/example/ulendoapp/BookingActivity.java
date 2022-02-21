package com.example.ulendoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity implements BookRideAdapter.OnTripClickListener{

    private List<TripModel> tripModelList;
    private TripModel trip;
    private MaterialTextView displayName;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        bookingActivity();
        displayName = findViewById(R.id.display_name);

        displayName.setText(new StringBuilder().append("Hey").append(" ").append("Name").append(",").append(" ").append("we found these rides for you").toString());
    }

    public boolean bookingActivity(){

        BookRideAdapter adapter = new BookRideAdapter(tripModelList, BookingActivity.this);

        tripModelList = new ArrayList<>();

        String[] latlong = "30.4343,34.7684".split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        LatLng location = new LatLng(latitude,longitude);

        trip = new TripModel("noahmgamba@gmail.com", "Blantyre", "Chikanda", "2:00PM", "No luggage","nothing", "N/A", location);
        tripModelList.add(trip);
        recyclerView = findViewById(R.id.trip_lists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        return true;
    }

    @Override
    public void onTripClick(int position) {

    }
}