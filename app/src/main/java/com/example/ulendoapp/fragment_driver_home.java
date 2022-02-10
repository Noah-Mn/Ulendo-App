package com.example.ulendoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class fragment_driver_home extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    Chip findRide, offerRide;

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
        viewPager = view.findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());



        findRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        offerRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        viewPager.setAdapter(adapter);

    }

}