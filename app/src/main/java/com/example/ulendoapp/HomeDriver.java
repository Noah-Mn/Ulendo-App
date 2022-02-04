package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;

public class HomeDriver extends AppCompatActivity {
    BottomNavigationView driver_bottom_nav;
    NavigationView navigation_view_driver;
    DrawerLayout drawerLayout;
    private MaterialTextView  header_name, header_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_driver);
        driver_bottom_nav = findViewById(R.id.driver_bottom_nav);
        driver_bottom_nav.inflateMenu(R.menu.bottom_navigation_menu);
        driver_bottom_nav.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        navigation_view_driver = findViewById(R.id.navigation_view_driver);
        drawerLayout = findViewById(R.id.driver_drawer_Layout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navInit();

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
                            startActivity(new Intent(HomeDriver.this, DriverProfile.class));
                            //replaceFragments(new fragment_driver_profile());
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

    private void navInit() {
        header_name = navigation_view_driver.getHeaderView(0).findViewById(R.id.header_name);
        header_email = navigation_view_driver.getHeaderView(0).findViewById(R.id.header_email);

        navigation_view_driver.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);

                switch (item.getItemId()) {
                    case R.id.dashboard:

                        replaceFragments(new My_Rides());
                        break;

                    case R.id.driver_chats:

                        replaceFragments(new My_Favorites());
                        break;

                    case R.id.driver_rides:

                        replaceFragments(new fragment_notifications());
                        break;

                    case R.id.driver_payments:

                        replaceFragments(new My_Payments());
                        break;
                    case R.id.my_vehicles:

                        replaceFragments(new My_Payments());
                        break;
                    case R.id.driver_groups:

                        replaceFragments(new My_Payments());
                        break;

                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }
}

