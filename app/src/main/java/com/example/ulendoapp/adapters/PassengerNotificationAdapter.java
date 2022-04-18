package com.example.ulendoapp.adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.models.NotificationModel;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PassengerNotificationAdapter extends RecyclerView.Adapter<PassengerNotificationAdapter.PassengerNotificationViewHolder> {
    public List<NotificationModel> passengerNotifications;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;

    Context context;

    public int getItemViewType(int position){
        NotificationModel notificationModel = passengerNotifications.get(position);
        if (notificationModel.getType() == NotificationModel.ItemType.ONE_ITEM){
            return TYPE_ONE;
        }else if (notificationModel.getType() == NotificationModel.ItemType.TWO_ITEM){
            return TYPE_TWO;
        }else if (notificationModel.getType() == NotificationModel.ItemType.THREE_ITEM){
            return TYPE_THREE;
        }else {
            return -1;
        }
    }

    public PassengerNotificationAdapter(List<NotificationModel> passengerNotifications, Context context) {
        this.passengerNotifications = passengerNotifications;
        this.context = context;
    }

    @NonNull
    @Override
    public PassengerNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificatin_accepted, parent, false);
            return new PassengerNotificationViewHolder(view);
        }else if (viewType == TYPE_TWO){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_rejected, parent, false);
            return new PassengerNotificationViewHolder(view);
        }else if (viewType == TYPE_THREE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_ignored, parent, false);
            return new PassengerNotificationViewHolder(view);
        }else {
            throw new RuntimeException("The type has to be one or two or three");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerNotificationViewHolder holder, int position) {
        passengerNotifications.get(position);
    }

    @Override
    public int getItemCount() {
        return passengerNotifications.size();
    }

    static class PassengerNotificationViewHolder extends RecyclerView.ViewHolder {
        public PassengerNotificationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
