package com.example.ulendoapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.models.NotificationModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

/**
 * The type Passenger notification adapter.
 */
public class PassengerNotificationAdapter extends RecyclerView.Adapter{
    /**
     * The Passenger notifications.
     */
    public List<NotificationModel> passengerNotifications;

    /**
     * The Context.
     */
    Context context;

    @Override
    public int getItemViewType(int position) {
        NotificationModel notificationModel = passengerNotifications.get(position);
        if (notificationModel.getStatus().toLowerCase().contains("accepted")){
            return 1;
        }else if (notificationModel.getStatus().toLowerCase().contains("rejected")){
            return 2;
        }else if (notificationModel.getStatus().toLowerCase().contains("ignored")){
            return 3;
        }else {
            return -1;
        }
    }

    /**
     * Instantiates a new Passenger notification adapter.
     *
     * @param passengerNotifications the passenger notifications
     * @param context                the context
     */
    public PassengerNotificationAdapter(List<NotificationModel> passengerNotifications, Context context) {
        this.passengerNotifications = passengerNotifications;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificatin_accepted, parent, false);
            return new PassengerNotificationViewHolderOne(view);
        }else if (viewType == 2){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_rejected, parent, false);
            return new PassengerNotificationViewHolderTwo(view);
        }else if (viewType == 3){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_ignored, parent, false);
            return new PassengerNotificationViewHolderThree(view);
        }else {
            throw new RuntimeException("The type has to be one or two or three");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (passengerNotifications.get(position).getStatus().toLowerCase().contains("accepted")){
            PassengerNotificationViewHolderOne viewHolderOne = (PassengerNotificationViewHolderOne) holder;
            viewHolderOne.textView.setText("Booking was successful");
        }else if (passengerNotifications.get(position).getStatus().contains("rejected")){
            PassengerNotificationViewHolderTwo viewHolderTwo = (PassengerNotificationViewHolderTwo) holder;
            viewHolderTwo.textView.setText("Booking was rejected");
        }else if (passengerNotifications.get(position).getStatus().contains("ignored")){
            PassengerNotificationViewHolderThree viewHolderThree = (PassengerNotificationViewHolderThree) holder;
            viewHolderThree.textView.setText("Booking was ignored");
        }
    }

    @Override
    public int getItemCount() {
        return passengerNotifications.size();
    }

    /**
     * The type Passenger notification view holder one.
     */
    static class PassengerNotificationViewHolderOne extends RecyclerView.ViewHolder {
        /**
         * The Text view.
         */
        MaterialTextView textView;

        /**
         * Instantiates a new Passenger notification view holder one.
         *
         * @param itemView the item view
         */
        public PassengerNotificationViewHolderOne(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.status_1);
        }
    }

    /**
     * The type Passenger notification view holder two.
     */
    static class PassengerNotificationViewHolderTwo extends RecyclerView.ViewHolder {
        /**
         * The Text view.
         */
        MaterialTextView textView;

        /**
         * Instantiates a new Passenger notification view holder two.
         *
         * @param itemView the item view
         */
        public PassengerNotificationViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.status_2);
        }
    }

    /**
     * The type Passenger notification view holder three.
     */
    static class PassengerNotificationViewHolderThree extends RecyclerView.ViewHolder {
        /**
         * The Text view.
         */
        MaterialTextView textView;

        /**
         * Instantiates a new Passenger notification view holder three.
         *
         * @param itemView the item view
         */
        public PassengerNotificationViewHolderThree(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.status_3);
        }
    }
}
