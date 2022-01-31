package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationBarMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotMetadata;

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
    RecyclerView.LayoutManager layoutManager;
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
//
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

        //showData();

        name = findViewById(R.id.firstName);
        //excraMark = findViewById(R.id.excraMark);
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

        if (savedInstanceState == null){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyRidesFragragment()).commit();
        navigationView.setCheckedItem(R.id.my_rides);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.searchItem);
        SearchView searchView  = (SearchView) MenuItemCompat.getActionView(item);
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
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.help){
            Toast.makeText(this, "Help clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setMenu(){
        toolbar.inflateMenu(R.menu.menu);;
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.searchItem) {
                    // do something
                }else if (item.getItemId()==R.id.help){
                    // help
                }else if (item.getItemId() == R.id.log_out){
                    // log out
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            item.setCheckable(true);
            item.setChecked(true);
            switch (item.getItemId()) {
                case R.id.my_rides:

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyRidesFragragment()).commit();
                    break;

                case R.id.my_favorites:

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyFavoritesFragment()).commit();
                    break;

                case R.id.notifications:

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationsFragment()).commit();
                    break;

                case R.id.my_payments:

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyPaymentsFragment()).commit();
                    break;

            }
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }
    }
