package com.example.ulendoapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.models.NotificationModel;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PassengerNotificationAdapter extends RecyclerView.Adapter<PassengerNotificationAdapter.PassengerNotificationViewHolder> {
    public List<NotificationModel> passengerNotifications;

    Context context;
    PreferenceManager preferenceManager;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser currentUser;

    @NonNull
    @Override
    public PassengerNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerNotificationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class PassengerNotificationViewHolder extends RecyclerView.ViewHolder {
        public PassengerNotificationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
