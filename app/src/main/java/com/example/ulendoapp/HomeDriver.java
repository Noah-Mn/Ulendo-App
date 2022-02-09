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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeDriver extends AppCompatActivity {
    BottomNavigationView driver_bottom_nav;
    NavigationView navigation_view_driver;
    DrawerLayout drawerLayout;
    private MaterialTextView  header_name, header_email, firstName;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    String fName, lastName, email;
    private final String TAG = "Home Driver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_driver);
        driver_bottom_nav = findViewById(R.id.driver_bottom_nav);
        driver_bottom_nav.inflateMenu(R.menu.bottom_navigation_menu);
        driver_bottom_nav.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        navigation_view_driver = findViewById(R.id.navigation_view_driver);
        drawerLayout = findViewById(R.id.driver_drawer_Layout);
        firstName = findViewById(R.id.firstName);
        header_name = findViewById(R.id.header_name);
        header_email = findViewById(R.id.header_email);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navInit();
        getUserData();
        getUserName();

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

    public void getUserData(){
        db.collection("Drivers")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                email = document.getString("Email Address");
                                header_name.setText(new StringBuilder().append(fName).append(" ").append(lastName).toString());
                                header_email.setText(email);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void getUserName(){
        db.collection("Users")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                fName = document.getString("First Name");
                                lastName = document.getString("Surname");
                                firstName.setText(fName);
                                header_name.setText(new StringBuilder().append(fName).append(" ").append(lastName).toString());

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}

