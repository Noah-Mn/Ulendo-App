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
    /**
     * The Db.
     */
    FirebaseFirestore db;
    /**
     * The Auth.
     */
    FirebaseAuth auth;
    /**
     * The Current user.
     */
    FirebaseUser currentUser;
    /**
     * The Text view.
     */
    MaterialTextView textView;
    /**
     * The User ride history.
     */
    RecyclerView userRideHistory;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        userRideHistory = view.findViewById(R.id.user_ride_history);

//                        if (tripModelList.size() > 0){
//                            TripsAdapter adapter = new TripsAdapter(tripModelList, getContext());
//                            userRideHistory.setLayoutManager(new LinearLayoutManager(this.getContext()));
//                            userRideHistory.setAdapter(adapter);
//                            userRideHistory.setVisibility(View.VISIBLE);
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