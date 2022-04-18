package com.example.ulendoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.databinding.NotificationLayoutBinding;
import com.example.ulendoapp.databinding.RideRequestLayoutBinding;
import com.example.ulendoapp.models.BookingModel;
import com.example.ulendoapp.models.NotificationModel;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.example.ulendoapp.viewHolders.UserRideViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    public List<BookingModel> request;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    NotificationModel notificationModel;

    Context context;
    PreferenceManager preferenceManager;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser currentUser;

    public NotificationAdapter(List<BookingModel> request, Context context) {
        this.request = request;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RideRequestLayoutBinding rideRequestLayoutBinding = RideRequestLayoutBinding.inflate(LayoutInflater.from(
                parent.getContext()), parent, false);
        return new NotificationViewHolder(rideRequestLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        preferenceManager = new PreferenceManager(context.getApplicationContext());
        notificationModel = new NotificationModel();

        BookingModel bookingModel = request.get(position);
        holder.binding.passengerName.setText(bookingModel.getPassengerName());
        holder.binding.pickUp.setText(bookingModel.getOrigin());
        holder.binding.dropOff.setText(bookingModel.getDest());
        holder.binding.passengerNumber.setText(new StringBuilder().append("With").append(" ").append((int) bookingModel.getNoPassengers()).append(" ").append("Other passengers").toString());

        holder.binding.closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationModel.setType(NotificationModel.ItemType.THREE_ITEM);
            }
        });
        holder.binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Accept !!!", Toast.LENGTH_SHORT).show();
                notificationModel.setType(NotificationModel.ItemType.ONE_ITEM);
            }
        });
        holder.binding.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Reject!!!!", Toast.LENGTH_SHORT).show();
                notificationModel.setType(NotificationModel.ItemType.TWO_ITEM);
            }
        });
//        driverEmail = request.get(position).getDriverEmail();
//        passengerEmail = request.get(position).getPassengerEmail();
//        tripId = request.get(position).getTripId();
//        origin = request.get(position).getOrigin();
//        dest = request.get(position).getDest();
//        date = request.get(position).getDate();
//        currDate = request.get(position).getCurrDate();
//        noPassengers = request.get(position).getNoPassengers();
//        notification.setText("home");

    }

    @Override
    public int getItemCount() {
        return request.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder{

        RideRequestLayoutBinding binding;
        NotificationViewHolder(@NonNull RideRequestLayoutBinding rideRequestLayoutBinding) {
            super(rideRequestLayoutBinding.getRoot());
            binding = rideRequestLayoutBinding;
        }

    }
}
