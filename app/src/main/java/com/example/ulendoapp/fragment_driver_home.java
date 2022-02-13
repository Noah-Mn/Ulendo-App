package com.example.ulendoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;

public class fragment_driver_home extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    Chip findRide, offerRide;
    ChipGroup chipGroup;

    public fragment_driver_home(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_home, container, false);
        addFragment(view);
        return view;
    }

    @Override
    public  void onCreate(Bundle savedInstance) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstance);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater){

        super.onCreateOptionsMenu(menu, inflater);
    }
    private  void addFragment(View view){
        findRide = view.findViewById(R.id.find_rides);
        offerRide = view.findViewById(R.id.offer_rides);
//        viewPager = view.findViewById(R.id.viewPager);
        chipGroup = view.findViewById(R.id.chip_group);
        Fragment find_rides = new fragment_find_rides();
        Fragment offer_rides= new fragment_offer_rides();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        offerRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragments(new fragment_offer_rides());
            }
        });

        findRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragments(new fragment_find_rides());
            }
        });
    }
    private void replaceFragments(Fragment fragment){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}