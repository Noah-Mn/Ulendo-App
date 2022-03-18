package com.example.ulendoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ulendoapp.R;
import com.example.ulendoapp.models.TripModel;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link currentRides#newInstance} factory method to
 * create an instance of this fragment.
 */
public class currentRides extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    MaterialTextView textView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public currentRides() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment currentRides.
     */
    // TODO: Rename and change types and number of parameters
    public static currentRides newInstance(String param1, String param2) {
        currentRides fragment = new currentRides();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_rides, container, false);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        textView = view.findViewById(R.id.show_text);
        getData();


        return view;
    }

    public void getData(){
        db.collection("MyTrips")
                .whereEqualTo("Email Address", getEmail())
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

                        }
                        else {
                            Toast.makeText(getContext(), "Failed to get trips", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Failed to get trips", Toast.LENGTH_SHORT).show();
                    }
                });
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