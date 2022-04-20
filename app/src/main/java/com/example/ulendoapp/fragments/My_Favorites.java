package com.example.ulendoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
 * The type My favorites.
 */
public class My_Favorites extends Fragment {

    private RecyclerView userFavouriteRides;
    /**
     * The Auth.
     */
    FirebaseAuth auth;
    /**
     * The Current user.
     */
    FirebaseUser currentUser;
    /**
     * The Db.
     */
    FirebaseFirestore db;
    /**
     * The Text view.
     */
    MaterialTextView textView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my__favorites, container, false);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        userFavouriteRides = view.findViewById(R.id.user_favourite_rides);
        db = FirebaseFirestore.getInstance();
        textView = view.findViewById(R.id.show_text);

        db.collection("MyTrips")
                .whereEqualTo("Email Address", getEmail())
                .whereEqualTo("Status"," Favourite")
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
                            TripsAdapter adapter = new TripsAdapter(tripModelList, getContext());
                            userFavouriteRides.setLayoutManager(new LinearLayoutManager(this.getContext()));
                            userFavouriteRides.setAdapter(adapter);
                            userFavouriteRides.setVisibility(View.VISIBLE);
                        }
                        else {
                            textVisible(true);
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