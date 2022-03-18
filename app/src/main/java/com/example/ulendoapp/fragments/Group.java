package com.example.ulendoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ulendoapp.R;
import com.example.ulendoapp.adapters.TripsAdapter;
import com.example.ulendoapp.models.TripModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Group extends Fragment {
    RecyclerView groupList;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        groupList = view.findViewById(R.id.group_list);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        db.collection("MyTrips")
                .whereEqualTo("Email Address", getEmail())
                .whereEqualTo("Status","Current")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null){
                        textVisible(false);
                        List<TripModel> tripModelList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()){
                            TripModel tripModel = new TripModel();
                            String startPoint = document.getString("Starting Point");
                            String destination = document.getString("Destination");
                            String time = document.getString("Time");
                            String date = document.getString("Date");
                            tripModel.setStartPoint(startPoint);
                            tripModel.setDestination(destination);
                            tripModel.setTime(time);
                            tripModel.setDate(date);
                            tripModelList.add(tripModel);

                        }
                        if (tripModelList.size() > 0){
                            TripsAdapter adapter = new TripsAdapter(tripModelList);
                            userRideHistory.setLayoutManager(new LinearLayoutManager(this.getContext()));
                            userRideHistory.setAdapter(adapter);
                            userRideHistory.setVisibility(View.VISIBLE);
                        }
                        else {
                            Toast.makeText(getContext(), "Failed to get trips", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Failed to get trips", Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }
    private String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

    private void textVisible(Boolean isVisible){
        if (isVisible){
            textView.setVisibility(View.VISIBLE);
        }else {
            textView.setVisibility(View.INVISIBLE);
        }
    }
}