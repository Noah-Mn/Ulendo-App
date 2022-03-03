package com.example.ulendoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.models.Vehicles;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>{

    Context context;
    ArrayList<Vehicles> vehiclesArrayList;

    public VehicleAdapter(Context context, ArrayList<Vehicles> vehiclesArrayList) {
        this.context = context;
        this.vehiclesArrayList = vehiclesArrayList;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_container_vehicle,parent,false);
        return new VehicleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        Vehicles vehicles = vehiclesArrayList.get(position);
        holder.textVehicleName.setText(vehicles.vehicleName);
        holder.textLicencePlate.setText(vehicles.licensePlate);

    }

    @Override
    public int getItemCount() {
        return vehiclesArrayList.size();
    }

    public static class VehicleViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView textVehicleName, textLicencePlate;

       public VehicleViewHolder(View itemView){
           super(itemView);

           textVehicleName = (MaterialTextView) itemView.findViewById(R.id.text_vehicle_name);
           textLicencePlate = (MaterialTextView) itemView.findViewById(R.id.text_licence_plate);

       }
    }

}
