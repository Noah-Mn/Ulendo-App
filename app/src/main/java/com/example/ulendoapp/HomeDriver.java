package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeDriver extends AppCompatActivity {
    BottomNavigationView driver_bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_driver);
        driver_bottom_nav = findViewById(R.id.driver_bottom_nav);
        driver_bottom_nav.inflateMenu(R.menu.bottom_navigation_menu);
        driver_bottom_nav.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }
    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.home:
                            replaceFragments(new fragment_driver_home());
                            break;

                        case R.id.notifications:
                            replaceFragments(new fragment_driver_notifications());
                            break;

                        case R.id.profile:
                            replaceFragments(new fragment_driver_profile());
                            break;
                    }
                    return true;
                }
            };
    private void replaceFragments(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}

