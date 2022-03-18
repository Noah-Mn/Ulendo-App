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
import com.example.ulendoapp.adapters.GroupsAdapter;
import com.example.ulendoapp.adapters.TripsAdapter;
import com.example.ulendoapp.models.Groups;
import com.example.ulendoapp.models.TripModel;
import com.google.android.material.textview.MaterialTextView;
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
    MaterialTextView textView;

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
        textView = view.findViewById(R.id.textView);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        db.collection("Groups")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null){
                        textVisible(false);
                        List<Groups> groupsList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()){
                            Groups groups = new Groups();
                            String startPoint = document.getString("Pickup Point");
                            String destination = document.getString("Destination");

                            groups.setStartingPoint(startPoint);
                            groups.setDestination(destination);
                            groupsList.add(groups);

                        }
                        if (groupsList.size() > 0){
                            GroupsAdapter adapter = new GroupsAdapter(groupsList);
                            groupList.setLayoutManager(new LinearLayoutManager(this.getContext()));
                            groupList.setAdapter(adapter);
                            groupList.setVisibility(View.VISIBLE);
                        }
                        else {
                           textVisible(true);
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Failed to get groups", Toast.LENGTH_SHORT).show();
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