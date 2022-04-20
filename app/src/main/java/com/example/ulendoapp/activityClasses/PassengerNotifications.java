package com.example.ulendoapp.activityClasses;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ulendoapp.adapters.PassengerNotificationAdapter;
import com.example.ulendoapp.databinding.ActivityPassengerNotificationsBinding;
import com.example.ulendoapp.models.NotificationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Passenger notifications.
 */
public class PassengerNotifications extends AppCompatActivity {
    /**
     * The Binding.
     */
    ActivityPassengerNotificationsBinding binding;
    /**
     * The Db.
     */
    FirebaseFirestore db;
    /**
     * The Auth.
     */
    FirebaseAuth auth;
    /**
     * The Current user.
     */
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPassengerNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        getNotifications();
    }

    /**
     * Get notifications.
     */
    public void getNotifications(){
        db.collection("Booking Ride")
                .whereEqualTo("Passenger Email Address", getEmail())
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful() && task.getResult() != null) {
                        List<NotificationModel> notification = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {

                            NotificationModel passengerNotifications = new NotificationModel();
                            String status = documentSnapshot.getString("Booking Status");
                            passengerNotifications.setStatus(status);
                            notification.add(passengerNotifications);

                            if (notification.size() > 0) {
                                PassengerNotificationAdapter adapter = new PassengerNotificationAdapter(notification, getApplicationContext());
                                binding.notificationList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                binding.notificationList.setAdapter(adapter);

                            } else {
                                Toast.makeText(getApplicationContext(), "You don't have any notifications", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Failed to get notifications", Toast.LENGTH_SHORT).show();
                    }
                });
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