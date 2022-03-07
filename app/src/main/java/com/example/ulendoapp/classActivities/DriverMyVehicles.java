package com.example.ulendoapp.classActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ulendoapp.adapters.VehicleAdapter;
import com.example.ulendoapp.databinding.ActivityDriverMyVehiclesBinding;
import com.example.ulendoapp.models.Vehicles;
import com.example.ulendoapp.utilities.Constants;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DriverMyVehicles extends AppCompatActivity {
    ActivityDriverMyVehiclesBinding binding;
    private List<Vehicles> vehiclesList;
    FirebaseFirestore database;
    private VehicleAdapter vehicleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverMyVehiclesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Listeners();
        loading(true);
        textVisible(true);
        init();
        listenChanges();
    }
    private void init(){
        vehiclesList = new ArrayList<>();
        database = FirebaseFirestore.getInstance();
        vehicleAdapter = new VehicleAdapter(vehiclesList);
        binding.vehicleList.setAdapter(vehicleAdapter);
    }

    private void Listeners(){
        binding.floatingActionButton.setOnClickListener(view -> startActivity(new Intent(DriverMyVehicles.this, AddVehicle.class)));
    }

    private void loading(Boolean isLoading){
        if (isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void textVisible(Boolean isVisible){
        if (isVisible){
            binding.ifNoVehicles.setVisibility(View.VISIBLE);
        }else {
            binding.ifNoVehicles.setVisibility(View.INVISIBLE);
        }
    }

    private void listenChanges(){
        database.collection("Driver Vehicles")
                .whereEqualTo("Vehicle Brand", "")
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo("License Plate", "")
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = ((value, error) -> {
        if (error != null){
            return;
        }if (value != null){
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    String name = documentChange.getDocument().getString("Vehicle Brand");
                    String numberPlate = documentChange.getDocument().getString("License Plate");
                    Vehicles vehicles = new Vehicles();
                    vehicles.vehicleName = name;
                    vehicles.licensePlate = numberPlate;
                    vehiclesList.add(vehicles);
                }
            }
            binding.progressBar.setVisibility(View.GONE);
            binding.ifNoVehicles.setVisibility(View.GONE);
        }
    });
}