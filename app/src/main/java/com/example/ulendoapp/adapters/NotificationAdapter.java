package com.example.ulendoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ulendoapp.databinding.RideRequestLayoutBinding;
import com.example.ulendoapp.models.BookingModel;
import com.example.ulendoapp.models.NotificationModel;
import com.example.ulendoapp.models.UserModel;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    public List<BookingModel> request;
    public List<NotificationModel> notificationModelList;

    Context context;
    PreferenceManager preferenceManager;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    UserModel userModel;

    public NotificationAdapter(List<BookingModel> request, Context context) {
        this.request = request;
        this.context = context;
    }

//   public int getItemViewType(int position){
//        NotificationModel notification = notificationModelList.get(position);
//        if (notification.getType() == NotificationModel.ItemType.ONE_ITEM){
//            return TYPE_ONE;
//        }else if (notification.getType() == NotificationModel.ItemType.TWO_ITEM){
//            return TYPE_TWO;
//        }else if (notification.getType() == NotificationModel.ItemType.THREE_ITEM){
//            return TYPE_THREE;
//        }else {
//            return -1;
//        }
//   }

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
        userModel = new UserModel();

        BookingModel bookingModel = request.get(position);
//        holder.binding.passengerName.setText(new StringBuilder().append(userModel.getFirstName()).append(" ").append(userModel.getLastName()).toString());
//        holder.binding.pickUp.setText(bookingModel.getOrigin());
//        holder.binding.dropOff.setText(bookingModel.getDest());
        holder.binding.passengerNumber.setText(new StringBuilder().append("With").append(" ").append((int) bookingModel.getNoPassengers()).append(" ").append("Other passengers").toString());

        holder.binding.closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ignored();
                removeAt(holder.getAdapterPosition());

            }
        });
        holder.binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Accept();
                removeAt(holder.getAdapterPosition());

            }
        });
        holder.binding.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reject();
                removeAt(holder.getAdapterPosition());

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
        db.collection("Booking Ride")
                .whereEqualTo("Driver Email Address", getEmail())
                .whereEqualTo("Booking Status", "pending")
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            String documentID = documentSnapshot.getId();
                            String name = documentSnapshot.getString(Constants.KEY_PASSENGER_NAME);
                            String destination = documentSnapshot.getString("Destination");
                            String origin = documentSnapshot.getString("Origin");
                            holder.binding.passengerName.setText(name);
                            holder.binding.pickUp.setText(origin);
                            holder.binding.dropOff.setText(destination);
                        }
                    }else {
                        Toast.makeText(context, "Failed to process", Toast.LENGTH_SHORT).show();
                    }
                });

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
    public void Ignored(){
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
                                    .update("Booking Status", "Ignored");
                            Toast.makeText(context, "Ride request ignored", Toast.LENGTH_SHORT).show();
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
    public void removeAt(int position){
        request.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, request.size());
    }
}
