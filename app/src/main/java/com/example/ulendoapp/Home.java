package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private MaterialTextView name, excraMark, text, rideText, header_name, header_email;
    private String userName, userText, userMark, userRideText, firstName, lastName, email;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private MaterialToolbar toolbar;
    ProgressDialog progressDialog;
    private String TAG;
    NavigationView navigationView;

    RecyclerView recyclerView;
    List<Model> modelList = new ArrayList<>();
    CustomAdapter adapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        //showData();

        name = findViewById(R.id.firstName);
        text = findViewById(R.id.text);
        rideText = findViewById(R.id.rideText);

        drawerLayout = findViewById(R.id.drawer_layout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        getUserFirstName();
        setMenu();

        header_name = (MaterialTextView) navigationView.getHeaderView(0).findViewById(R.id.header_name);
        header_email = (MaterialTextView) navigationView.getHeaderView(0).findViewById(R.id.header_email);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);

                switch (item.getItemId()) {
                    case R.id.my_rides:

                        replaceFragments(new My_Rides());
                        break;

                    case R.id.my_favorites:

                        replaceFragments(new My_Favorites());
                        break;

                    case R.id.notifications:


                        replaceFragments(new Notifications());
                        break;

                    case R.id.my_payments:

                        replaceFragments(new My_Payments());
                        break;

                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

    }
    
    //    private void showData() {
    //        db.collection("Users")
    //                .get()
    //                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
    //                    @Override
    //                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
    //
    //                    }
    //                })
    //                .addOnFailureListener(new OnFailureListener() {
    //                    @Override
    //                    public void onFailure(@NonNull Exception e) {
    //
    //                    }
    //                });
    //    }


    public void getUserFirstName(){
        db.collection("Users")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                firstName = document.getString("First Name");
                                lastName = document.getString("Surname");
                                email = document.getString("Email Address");
                                name.setText(firstName);
                                header_name.setText(new StringBuilder().append(firstName).append(" ").append(lastName).toString());
                                header_email.setText(email);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        
        MenuItem item = menu.findItem(R.id.searchItem);

//        SearchView searchView  = (SearchView) MenuItemCompat.getActionView(item);
        //        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        //                        @Override
        //                        public boolean onQueryTextSubmit(String query) {
        //                            String s = null;
        //                            assert s != null;
        //                            searchData(s);
        //                            return false;
        //                        }
        //
        //                        @Override
        //                        public boolean onQueryTextChange(String newText) {
        //                            return false;
        //                        }
        //                    });

        return super.onCreateOptionsMenu(menu);
    }

    public void setMenu(){
        toolbar.inflateMenu(R.menu.menu);;
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.searchItem) {
                    // do something
                }else if (item.getItemId()==R.id.help){
                    Toast.makeText(getApplicationContext(), "Help clicked", Toast.LENGTH_SHORT).show();
                }else if (item.getItemId() == R.id.log_out){
                    // log out
                    Toast.makeText(getApplicationContext(), "log out clicked", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

        });
    }

    private void searchData(String s) {
        progressDialog.setTitle("Searching...");
        progressDialog.show();

        db.collection("Users").whereEqualTo("Status",s.toLowerCase(Locale.ROOT))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelList.clear();
                        progressDialog.dismiss();
                        for (DocumentSnapshot documentSnapshot: task.getResult()){
                            Model model = new Model(documentSnapshot.getString("Status"),
                                    documentSnapshot.getString("First Name"),
                                    documentSnapshot.getString("Surname"),
                                    documentSnapshot.getString("Phone Number"));
                            modelList.add(model);
                        }
                        adapter = new CustomAdapter(Home.this, modelList);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    
    // don't write your code here.....it won't work
    //<<<<<<<<<<<<<<<<<<<<<<<<Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return true;
        }
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    private void replaceFragments(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        }
    }
