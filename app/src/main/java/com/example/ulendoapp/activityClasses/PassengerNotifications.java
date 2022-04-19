package com.example.ulendoapp.activityClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ulendoapp.R;
import com.example.ulendoapp.adapters.NotificationAdapter;
import com.example.ulendoapp.adapters.PassengerNotificationAdapter;
import com.example.ulendoapp.databinding.ActivityPassengerNotificationsBinding;
import com.example.ulendoapp.models.BookingModel;
import com.example.ulendoapp.models.NotificationModel;
import com.example.ulendoapp.utilities.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PassengerNotifications extends AppCompatActivity {
    ActivityPassengerNotificationsBinding binding;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPassengerNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        getAcceptedNotifications();
        getIgnoredNotifications();
        getRejectedNotifications();
    }
    public void getAcceptedNotifications(){
        db.collection("Booking Ride")
                .whereEqualTo("Driver Email Address", getEmail())
                .whereEqualTo("Booking Status", "Accepted")
                .get()
                .addOnCompleteListener(task -> {
//                        progressDialog.dismiss();

                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            List<NotificationModel> notification = new ArrayList<>();
                            String email = documentSnapshot.getString("Driver Email Address");
                            long noPassengers = documentSnapshot.getLong("Number of Passengers");
                            String origin = documentSnapshot.getString("Origin");
                            String destination = documentSnapshot.getString("Destination");
                            String date = documentSnapshot.getString("Booked Date");

                            NotificationModel passengerNotifications = new NotificationModel();
//                            passengerNotifications.setOrigin(origin);

//                            rTrip.setDest(destination);
//                            rTrip.setDate(date);
//                            rTrip.setNoPassengers(noPassengers);
//                            rTrip.setDriverEmail(email);
                            notification.add(passengerNotifications);

                            if (notification.size() > 0) {
                                PassengerNotificationAdapter adapter = new PassengerNotificationAdapter(notification, getApplicationContext());
                                binding.notificationList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                binding.notificationList.setAdapter(adapter);
//                                Toast.makeText(getApplicationContext(), "Hey you", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "You don't have any notifications", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Failed to get notifications", Toast.LENGTH_SHORT).show();
                    }
                });
    }public void getRejectedNotifications(){
        db.collection("Booking Ride")
                .whereEqualTo("Driver Email Address", getEmail())
                .whereEqualTo("Booking Status", "Rejected")
                .get()
                .addOnCompleteListener(task -> {
//                        progressDialog.dismiss();

                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            List<NotificationModel> notification = new ArrayList<>();
                            String email = documentSnapshot.getString("Driver Email Address");
                            long noPassengers = documentSnapshot.getLong("Number of Passengers");
                            String origin = documentSnapshot.getString("Origin");
                            String destination = documentSnapshot.getString("Destination");
                            String date = documentSnapshot.getString("Booked Date");

                            NotificationModel passengerNotifications = new NotificationModel();
//                            passengerNotifications.setOrigin(origin);

//                            rTrip.setDest(destination);
//                            rTrip.setDate(date);
//                            rTrip.setNoPassengers(noPassengers);
//                            rTrip.setDriverEmail(email);
                            notification.add(passengerNotifications);

                            if (notification.size() > 0) {
                                PassengerNotificationAdapter adapter = new PassengerNotificationAdapter(notification, getApplicationContext());
                                binding.notificationList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                binding.notificationList.setAdapter(adapter);
//                                Toast.makeText(getApplicationContext(), "Hey you", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "You don't have any notifications", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Failed to get notifications", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void getIgnoredNotifications(){
        db.collection("Booking Ride")
                .whereEqualTo("Driver Email Address", getEmail())
                .whereEqualTo("Booking Status", "Ignored")
                .get()
                .addOnCompleteListener(task -> {
//                        progressDialog.dismiss();

                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            List<NotificationModel> notification = new ArrayList<>();
                            String email = documentSnapshot.getString("Driver Email Address");
                            long noPassengers = documentSnapshot.getLong("Number of Passengers");
                            String origin = documentSnapshot.getString("Origin");
                            String destination = documentSnapshot.getString("Destination");
                            String date = documentSnapshot.getString("Booked Date");

                            NotificationModel passengerNotifications = new NotificationModel();
//                            passengerNotifications.setOrigin(origin);

//                            rTrip.setDest(destination);
//                            rTrip.setDate(date);
//                            rTrip.setNoPassengers(noPassengers);
//                            rTrip.setDriverEmail(email);
                            notification.add(passengerNotifications);

                            if (notification.size() > 0) {
                                PassengerNotificationAdapter adapter = new PassengerNotificationAdapter(notification, getApplicationContext());
                                binding.notificationList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                binding.notificationList.setAdapter(adapter);
//                                Toast.makeText(getApplicationContext(), "Hey you", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "You don't have any notifications", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Failed to get notifications", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }
}