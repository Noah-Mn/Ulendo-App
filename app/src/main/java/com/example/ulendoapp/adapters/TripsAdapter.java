package com.example.ulendoapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.databinding.UserRideLayoutBinding;
import com.example.ulendoapp.fragments.rideHistory;
import com.example.ulendoapp.models.OfferRideModel;
import com.example.ulendoapp.models.TripModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.TripViewHolder> {
    private ArrayList<TripModel> tripModelArrayList;
    private BookRideAdapter.OnTripClickListener onTripClickListener;
    String startingPoint, destination, time, date, status, email;

    public TripsAdapter(ArrayList<TripModel> tripModelArrayList, BookRideAdapter.OnTripClickListener onTripClickListener, String startingPoint, String destination, String time, String date, String status, String email) {
        this.tripModelArrayList = tripModelArrayList;
        this.onTripClickListener = onTripClickListener;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.time = time;
        this.date = date;
        this.status = status;
        this.email = email;
    }

    public TripsAdapter(List<TripModel> tripModelList, rideHistory rideHistory) {
    }

    public TripsAdapter(List<TripModel> tripModelList) {
    }

    public void setTrips(ArrayList<TripModel> tripModelArrayList) {
        this.tripModelArrayList = new ArrayList<>();
        this.tripModelArrayList = tripModelArrayList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        UserRideLayoutBinding userRideLayoutBinding = UserRideLayoutBinding.inflate(LayoutInflater.from(
                parent.getContext()),
                parent,
                false);
        return new TripViewHolder(userRideLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        holder.setTripData(tripModelArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return tripModelArrayList.size();
    }


    public class TripViewHolder extends RecyclerView.ViewHolder{

        UserRideLayoutBinding binding;
        TripViewHolder(@NonNull UserRideLayoutBinding userRideLayoutBinding) {
            super(userRideLayoutBinding.getRoot());
            binding = userRideLayoutBinding;
        }
        void setTripData(TripModel tripModel){
            binding.dateNtime.setText(tripModel.getDate());
            binding.destination.setText(tripModel.getDestination());
            binding.origin.setText(tripModel.getStartPoint());
            binding.tripState.setText(tripModel.getStatus());
            binding.driverInitials.setVisibility(View.GONE);
            binding.getRoot();
        }

    }

}
