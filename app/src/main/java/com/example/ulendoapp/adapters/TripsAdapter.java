package com.example.ulendoapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.databinding.UserRideLayoutBinding;
import com.example.ulendoapp.fragments.rideHistory;
import com.example.ulendoapp.models.OfferRideModel;
import com.example.ulendoapp.models.TripModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.TripViewHolder> {
    private List<TripModel> tripModelList;
    Context context;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser currentUser;

    public TripsAdapter(List<TripModel> tripModelList) {
        this.tripModelList = tripModelList;

    }

    public TripsAdapter(List<TripModel> tripModelList, Context context) {
        this.tripModelList = tripModelList;
        this.context = context;
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
        holder.setTripData(tripModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return tripModelList.size();
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
            binding.userCardViewMenuBtn.setOnClickListener(view -> onClick());
            binding.getRoot();
        }
        void  onClick(){
            PopupMenu popupMenu = new PopupMenu(context, binding.userCardViewMenuBtn);
            popupMenu.inflate(R.menu.my_ride_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.menu_finished:
                            Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.menu_favourite:
                            Toast.makeText(context, "Yes there", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.menu_remove_from_itinerary:
                            Toast.makeText(context, "yeeeee", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }

    }

}
