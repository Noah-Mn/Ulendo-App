package com.example.ulendoapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ulendoapp.R;
import com.example.ulendoapp.activityClasses.BookingActivity;
import com.example.ulendoapp.activityClasses.BookingRide;
import com.example.ulendoapp.adapters.BookRideAdapter;
import com.example.ulendoapp.adapters.NotificationAdapter;
import com.example.ulendoapp.models.BookingModel;
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

public class fragment_driver_notifications extends Fragment{
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    public fragment_driver_notifications() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_notifications, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        recyclerView = view.findViewById(R.id.notification_recyclerview);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_notifications, container, false);
    }
    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

    public  void getRequestInfo(){
        db.collection("Booking Ride")
                .whereEqualTo("Passenger Email Address", getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        progressDialog.dismiss();
                        for (DocumentSnapshot documentSnapshot: task.getResult()){
                            String email = documentSnapshot.getString("Driver Email Address");
                            long noPassengers = documentSnapshot.getLong("Number of Passengers");
                            String origin = documentSnapshot.getString("Origin");
                            String destination = documentSnapshot.getString("Destination");
                            String date = documentSnapshot.getString("Booked Date");

                            BookingModel rTrip = new BookingModel(email, noPassengers, origin, destination, date);
                            ArrayList<BookingModel> request = new ArrayList<>();
                            request.add(rTrip);


                            NotificationAdapter adapter = new NotificationAdapter(request);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(BookingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}