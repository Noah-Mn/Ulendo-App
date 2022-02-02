package com.example.ulendoapp;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class fragment_driver_home extends Fragment {
    public fragment_driver_home(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_home, container, false);

    }

    @Override
    public  void onCreate(Bundle savedInstance) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstance);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){

        super.onCreateOptionsMenu(menu, inflater);
    }

}