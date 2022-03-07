package com.example.ulendoapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.databinding.ItemContainerVehicleBinding;
import com.example.ulendoapp.models.Vehicles;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>{

    List<Vehicles> vehiclesList;

    public VehicleAdapter(List<Vehicles> vehiclesList) {

        this.vehiclesList = vehiclesList;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerVehicleBinding itemContainerVehicleBinding = ItemContainerVehicleBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new VehicleViewHolder(itemContainerVehicleBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        holder.setVehicleData(vehiclesList.get(position));
    }

    @Override
    public int getItemCount() {
        return vehiclesList.size();
    }

    public static class VehicleViewHolder extends RecyclerView.ViewHolder{

        ItemContainerVehicleBinding binding;

       public VehicleViewHolder(ItemContainerVehicleBinding itemContainerVehicleBinding){
           super(itemContainerVehicleBinding.getRoot());
           binding = itemContainerVehicleBinding;
       }
        void setVehicleData(Vehicles vehicles) {
            binding.textVehicleName.setText(vehicles.vehicleName);
            binding.textLicencePlate.setText(vehicles.licensePlate);
        }
    }

}
