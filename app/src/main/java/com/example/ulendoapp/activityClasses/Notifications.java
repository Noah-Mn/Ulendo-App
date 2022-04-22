package com.example.ulendoapp.activityClasses;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ulendoapp.adapters.NotificationAdapter;
import com.example.ulendoapp.adapters.RecentConversationsAdapter;
import com.example.ulendoapp.databinding.ActivityNotificationsBinding;
import com.example.ulendoapp.listeners.NotificationListener;
import com.example.ulendoapp.models.BookRequest;
import com.example.ulendoapp.models.BookingModel;
import com.example.ulendoapp.models.ChatMessage;
import com.example.ulendoapp.models.NotificationModel;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Notifications.
 */
public class Notifications extends AppCompatActivity implements NotificationListener {

    /**
     * The Binding.
     */
    ActivityNotificationsBinding binding;
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
    /**
     * The Preference manager.
     */
    PreferenceManager preferenceManager;
    private List<BookRequest> bookRequestList;
    NotificationAdapter adapter;
    List<BookingModel> request = new ArrayList<>();

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
        init();


    }

    @Override
    public void onNotificationClicked(BookingModel bookingModel) {

    }
    private void init(){
        bookRequestList = new ArrayList<>();
        adapter = new NotificationAdapter(request, getApplicationContext());
//        binding.conversationsRecyclerView.setAdapter(conversationsAdapter);
//        database = FirebaseFirestore.getInstance();
    }

    /**
     * Get notifications.
     */
    public void getNotifications(){
        db.collection("Booking Ride")
                .whereEqualTo("Driver Email Address", getEmail())
                .whereEqualTo("Booking Status", "pending")
                .get()
                .addOnCompleteListener(task -> {
//                        progressDialog.dismiss();

                    if (task.isSuccessful() && task.getResult() != null) {

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
    private void listenNotifications(){
        db.collection("Booking Ride")
                .whereEqualTo(Constants.KEY_T_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
        db.collection("Booking Ride")
                .whereEqualTo(Constants.KEY_T_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }
    private final EventListener<QuerySnapshot> eventListener = ((value, error) -> {
        if (error != null){
            return;
        }if (value != null){
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    String senderId = documentChange.getDocument().getString(Constants.KEY_T_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_T_RECEIVER_ID);

                    BookRequest bookRequest = new BookRequest();
                    bookRequest.tSenderId = senderId;
                    bookRequest.tReceiverId = receiverId;
                    if (preferenceManager.getString(Constants.KEY_USER_ID).equals(senderId)){
                        bookRequest.passengerName = documentChange.getDocument().getString(Constants.KEY_PASSENGER_NAME);
                        bookRequest.bookingID = documentChange.getDocument().getString(Constants.KEY_T_RECEIVER_ID);
                    }else {
                        bookRequest.passengerName = documentChange.getDocument().getString("Driver Email Address");
                        bookRequest.bookingID = documentChange.getDocument().getString(Constants.KEY_T_SENDER_ID);
                    }
                    bookRequest.message = "Booking Request";
                    bookRequest.dateObject = documentChange.getDocument().getDate("Booked Date");
                    bookRequestList.add(bookRequest);
                }else if (documentChange.getType() == DocumentChange.Type.MODIFIED){
                    for (int i = 0; i < bookRequestList.size(); i++){
                        String senderId = documentChange.getDocument().getString(Constants.KEY_T_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_T_RECEIVER_ID);
                        if (bookRequestList.get(i).tSenderId.equals(senderId) && bookRequestList.get(i).tReceiverId.equals(receiverId)){
                            bookRequestList.get(i).dateObject = documentChange.getDocument().getDate("Booked Date");
                            break;
                        }
                    }
                }
            }
            Collections.sort(bookRequestList, (obj1, obj2) -> obj2.dateObject.compareTo(obj1.dateObject));
            adapter.notifyDataSetChanged();
            binding.notificationList.smoothScrollToPosition(0);
            binding.notificationList.setVisibility(View.VISIBLE);
        }
    });
}