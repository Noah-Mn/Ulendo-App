package com.example.ulendoapp.activityClasses;

import static com.example.ulendoapp.activityClasses.HomeUser.userModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ulendoapp.R;
import com.example.ulendoapp.adapters.NotificationAdapter;
import com.example.ulendoapp.databinding.ActivityNotificationsBinding;
import com.example.ulendoapp.listeners.NotificationListener;
import com.example.ulendoapp.models.BookingModel;
import com.example.ulendoapp.models.NotificationModel;
import com.example.ulendoapp.models.UserModel;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Notifications extends AppCompatActivity implements NotificationListener {

    ActivityNotificationsBinding binding;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        preferenceManager = new PreferenceManager(getApplicationContext());
        getNotifications();

    }

    @Override
    public void onNotificationClicked(BookingModel bookingModel) {

    }
    public void getNotifications(){
        db.collection("Booking Ride")
                .whereEqualTo("Driver Email Address", getEmail())
                .whereEqualTo("Booking Status", "pending")
                .get()
                .addOnCompleteListener(task -> {
//                        progressDialog.dismiss();

                    if (task.isSuccessful() && task.getResult() != null) {
                        List<BookingModel> request = new ArrayList<>();

                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            BookingModel rTrip = new BookingModel();
                            String email = documentSnapshot.getString("Driver Email Address");
                            long noPassengers = documentSnapshot.getLong("Number of Passengers");
                            String origin = documentSnapshot.getString("Origin");
                            String destination = documentSnapshot.getString("Destination");
                            String date = documentSnapshot.getString("Booked Date");
                            String passengerName = preferenceManager.getString(Constants.KEY_NAME);

                            rTrip.setOrigin(origin);
                            rTrip.setPassengerName(passengerName);
                            rTrip.setDest(destination);
                            rTrip.setDate(date);
                            rTrip.setNoPassengers(noPassengers);
                            rTrip.setDriverEmail(email);
                            request.add(rTrip);

                            if (request.size() > 0) {
                                NotificationAdapter adapter = new NotificationAdapter(request, getApplicationContext());
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

//    public String getUserModel(){
//        db.collection("Users")
//                .whereEqualTo("Email Address", getEmail())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//
//                                String f_name = document.getString("First Name");
//                                String surname = document.getString("Surname");
//                                String name = f_name+" "+surname;
//                            }
//                        } else {
//                            Toast.makeText(Notifications.this, "Failed to get Name", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//        return
//    }

    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }
}