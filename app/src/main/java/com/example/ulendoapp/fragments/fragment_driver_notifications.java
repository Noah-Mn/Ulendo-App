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
import com.example.ulendoapp.adapters.TripsAdapter;
import com.example.ulendoapp.models.BookingModel;
import com.example.ulendoapp.models.OfferRideModel;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
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
import java.util.Objects;

/**
 * The type Fragment driver notifications.
 */
public class fragment_driver_notifications extends Fragment{
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    /**
     * The Preference manager.
     */
    PreferenceManager preferenceManager;

    /**
     * Instantiates a new Fragment driver notifications.
     */
    public fragment_driver_notifications() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        preferenceManager = new PreferenceManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_notifications, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        recyclerView = view.findViewById(R.id.notification_recyclerview);
        preferenceManager = new PreferenceManager(getContext());


        // Inflate the layout for this fragment
        db.collection("Booking Ride")
                .whereEqualTo("Passenger Email Address", getEmail())
                .whereEqualTo(Constants.KEY_T_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .get()
                .addOnCompleteListener(task -> {
//                        progressDialog.dismiss();

                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            List<BookingModel> request = new ArrayList<>();
                            String email = documentSnapshot.getString("Driver Email Address");
                            long noPassengers = documentSnapshot.getLong("Number of Passengers");
                            String origin = documentSnapshot.getString("Origin");
                            String destination = documentSnapshot.getString("Destination");
                            String date = documentSnapshot.getString("Booked Date");
                            String passengerName = documentSnapshot.getString(Constants.KEY_PASSENGER_NAME);

                            BookingModel rTrip = new BookingModel();
                            rTrip.setOrigin(origin);
                            rTrip.setPassengerName(passengerName);
                            rTrip.setDest(destination);
                            rTrip.setDate(date);
                            rTrip.setNoPassengers(noPassengers);
                            rTrip.setDriverEmail(email);
                            request.add(rTrip);

                            if (request.size() > 0) {
                                NotificationAdapter adapter = new NotificationAdapter(request, getContext());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.setAdapter(adapter);
                                Toast.makeText(getContext(), "Hey you", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), "You don't have any notifications", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }else {
                        Toast.makeText(getContext(), "Failed to get notifications", Toast.LENGTH_SHORT).show();
                    }
                });

        return view;

    }

    /**
     * Get email string.
     *
     * @return the string
     */
    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

}