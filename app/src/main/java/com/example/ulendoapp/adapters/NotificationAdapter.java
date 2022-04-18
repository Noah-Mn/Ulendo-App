package com.example.ulendoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.databinding.NotificationLayoutBinding;
import com.example.ulendoapp.databinding.RideRequestLayoutBinding;
import com.example.ulendoapp.models.BookingModel;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.example.ulendoapp.viewHolders.UserRideViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    public List<BookingModel> request;

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

        BookingModel bookingModel = request.get(position);
        holder.binding.passengerName.setText(bookingModel.getPassengerName());
        holder.binding.pickUp.setText(bookingModel.getOrigin());
        holder.binding.dropOff.setText(bookingModel.getDest());
        holder.binding.passengerNumber.setText(new StringBuilder().append("With").append(" ").append((int) bookingModel.getNoPassengers()).append(" ").append("Other passengers").toString());

        holder.binding.closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Accept();
            }
        });
        holder.binding.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reject();
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
    public void Accept(){
        db.collection("Booking Ride")
                .whereEqualTo("Driver Email Address", getEmail())
                .whereEqualTo("Booking Status", "pending")
                .get()
                .addOnCompleteListener(task -> {
//                        progressDialog.dismiss();

                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            String documentID = documentSnapshot.getId();
                            db.collection("Booking Ride")
                                    .document(documentID)
                                    .update("Booking Status", "Accepted");
                            Toast.makeText(context, "Ride request Accepted", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context, "Failed to process", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void Reject(){
        db.collection("Booking Ride")
                .whereEqualTo("Driver Email Address", getEmail())
                .whereEqualTo("Booking Status", "pending")
                .get()
                .addOnCompleteListener(task -> {
//                        progressDialog.dismiss();

                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            String documentID = documentSnapshot.getId();
                            db.collection("Booking Ride")
                                    .document(documentID)
                                    .update("Booking Status", "Rejected");
                            Toast.makeText(context, "Ride request Rejected", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context, "Failed to process", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }
}
