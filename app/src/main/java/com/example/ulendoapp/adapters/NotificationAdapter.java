package com.example.ulendoapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.models.BookingModel;
import com.example.ulendoapp.viewHolders.UserRideViewHolder;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> implements View.OnClickListener {
    public ArrayList<BookingModel> request;
    public String driverEmail, passengerEmail, tripId, origin, dest, date, currDate;
    public long noPassengers;
    private TextView notification;

    public NotificationAdapter(View userView, ArrayList<BookingModel> request) {
        this.request = request;
    }

    @Override
    public void onClick(View view) {

    }

    public NotificationAdapter(ArrayList<BookingModel> request) {
        this.request = request;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View userView = inflater.inflate(R.layout.notification_layout, parent, false);

        NotificationViewHolder userHolder = new NotificationViewHolder(userView);
        return userHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        driverEmail = request.get(position).getDriverEmail();
        passengerEmail = request.get(position).getPassengerEmail();
        tripId = request.get(position).getTripId();
        origin = request.get(position).getOrigin();
        dest = request.get(position).getDest();
        date = request.get(position).getDate();
        currDate = request.get(position).getCurrDate();
        noPassengers = request.get(position).getNoPassengers();

        notification.setText("home");

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notification = itemView.findViewById(R.id.notification_text);

        }

        @Override
        public void onClick(View view) {

        }

    }
}
