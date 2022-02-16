package com.example.ulendoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.chip.Chip;

import java.util.Objects;

public class fragment_driver_home extends Fragment {
    Chip findRide, offerRide;

    public fragment_driver_home(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_home, container, false);
        replaceFragments(new fragment_offer_rides());
        addFragment(view);
        return view;
    }

    @Override
    public  void onCreate(Bundle savedInstance) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstance);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){

        super.onCreateOptionsMenu(menu, inflater);
    }
    private  void addFragment(View view){
        findRide = view.findViewById(R.id.find_rides);
        offerRide = view.findViewById(R.id.offer_rides);
        assert getFragmentManager() != null;
        offerRide.setOnClickListener(view12 -> replaceFragments(new fragment_offer_rides()));
        findRide.setOnClickListener(view1 -> replaceFragments(new fragment_find_rides()));
    }
    private void replaceFragments(Fragment fragment){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    private void closeFragment(){
        requireActivity().getSupportFragmentManager().beginTransaction().remove(fragment_driver_home.this).commit();
    }

}