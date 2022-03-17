package com.example.ulendoapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ulendoapp.R;
import com.example.ulendoapp.activityClasses.AddNewRide;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRides#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRides extends Fragment{

    private FloatingActionButton addRide;
    Chip currentRides, ridesHistory;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyRides() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserMyRides.
     */
    // TODO: Rename and change types and number of parameters
    public static MyRides newInstance(String param1, String param2) {
        MyRides fragment = new MyRides();
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
        View view = inflater.inflate(R.layout.fragment_user_my_rides, container, false);
        replaceFragments(new currentRides());
        addFragment(view);
//        recyclerViewCard = view.findViewById(R.id.userRideRecyclerView);


        return view;

    }

    private void addFragment(View view){
        currentRides = view.findViewById(R.id.currentRideChip);
        ridesHistory = view.findViewById(R.id.historyChip);
        addRide = view.findViewById(R.id.add_ride_btn);
        assert getFragmentManager() != null;
        currentRides.setOnClickListener(view1 -> replaceFragments(new currentRides()));
        ridesHistory.setOnClickListener(view1 -> replaceFragments(new rideHistory()));
        Intent intent = new Intent(getActivity(), AddNewRide.class);
        addRide.setOnClickListener(view1 -> startActivity(intent));
    }
    private void replaceFragments(Fragment fragment){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}