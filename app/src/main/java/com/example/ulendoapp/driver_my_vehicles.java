package com.example.ulendoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ulendoapp.adapters.VehicleAdapter;
import com.example.ulendoapp.databinding.FragmentDriverMyVehiclesBinding;
import com.example.ulendoapp.models.Vehicles;
import com.example.ulendoapp.utilities.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class driver_my_vehicles extends Fragment {
    FloatingActionButton floatingActionButton;
    private View vehicleView;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    MaterialTextView ifNoVehicles;

    public driver_my_vehicles() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vehicleView = inflater.inflate(R.layout.fragment_driver_my_vehicles,container,false);

        ifNoVehicles = (MaterialTextView) vehicleView.findViewById(R.id.if_no_vehicles);
        progressBar = (ProgressBar)vehicleView.findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) vehicleView.findViewById(R.id.vehicle_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return vehicleView;
    }



    private void loading(Boolean isLoading){
        if (isLoading){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void textVisible(Boolean isVisible){
        if (isVisible){
            ifNoVehicles.setVisibility(View.VISIBLE);
        }else {
            ifNoVehicles.setVisibility(View.INVISIBLE);
        }
    }
    private void getVehicles(){

        loading(true);
        textVisible(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("Driver Vehicles")
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    textVisible(false);
                    String currentVehicleID = Constants.KEY_VEHICLE_ID ;
                    if (task.isSuccessful() && task.getResult() != null){
                        ArrayList<Vehicles> vehicles = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if (currentVehicleID.equals((queryDocumentSnapshot.getId()))){
                                continue;
                            }
                            Vehicles vehicle = new Vehicles();
                            String name = queryDocumentSnapshot.getString("Vehicle Brand");
                            String numberPlate = queryDocumentSnapshot.getString("License Plate");
                            vehicle.vehicleName = name;
                            vehicle.licensePlate = numberPlate;
                            vehicles.add(vehicle);
                        }
                        if (vehicles.size() > 0){
                            VehicleAdapter vehiclesAdapter = new VehicleAdapter(getContext(), vehicles);
//                            binding.vehicleList.setAdapter(vehiclesAdapter);
//                            binding.vehicleList.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}