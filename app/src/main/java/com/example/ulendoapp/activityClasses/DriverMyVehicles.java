package com.example.ulendoapp.activityClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ulendoapp.adapters.VehicleAdapter;
import com.example.ulendoapp.databinding.ActivityDriverMyVehiclesBinding;
import com.example.ulendoapp.listeners.VehicleListener;
import com.example.ulendoapp.models.Vehicles;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DriverMyVehicles extends AppCompatActivity implements VehicleListener {
    ActivityDriverMyVehiclesBinding binding;
    FirebaseFirestore database;
    FirebaseUser currentUser;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverMyVehiclesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();
        Listeners();
        loading(true);
        textVisible(true);
        getVehicles();
    }

    private void Listeners(){
        binding.floatingActionButton.setOnClickListener(view -> startActivity(new Intent(DriverMyVehicles.this, AddVehicle.class)));
        binding.imageBack.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), HomeDriver.class)));
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

    public void getVehicles(){
        database.collection("Driver Vehicles")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null){
                        textVisible(false);
                        loading(false);
                        List<Vehicles>  vehiclesList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()){
                            Vehicles vehicles = new Vehicles();
                            String name = document.getString("Vehicle Brand");
                            String numberPlate = document.getString("License Plate");
                            vehicles.vehicleName = name;
                            vehicles.licensePlate = numberPlate;
                            vehiclesList.add(vehicles);
                        }
                        if (vehiclesList.size() > 0){
                            VehicleAdapter vehicleAdapter = new VehicleAdapter(vehiclesList, this);
                            binding.vehicleList.setAdapter(vehicleAdapter);
                            binding.vehicleList.setVisibility(View.VISIBLE);
                        }
                        else {
                            Toast.makeText(DriverMyVehicles.this, "Failed to get Vehicles", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(DriverMyVehicles.this, "Failed to get Vehicles", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

    @Override
    public void onVehicleClick(Vehicles vehicles) {

    }
}