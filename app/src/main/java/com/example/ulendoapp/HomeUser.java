package com.example.ulendoapp;

import static androidx.core.view.MenuItemCompat.getActionView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class HomeUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CustomAdapter.OnDriverClickListener {

    private MaterialTextView name, header_name, header_email;
    private String firstName, lastName, email;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private MaterialToolbar toolbar;
    ProgressDialog progressDialog;
    private String TAG;
    public static String newText;
    private NavigationView navigationView;

    private RecyclerView recyclerView;
    private List<UserModel> userModelList;
    private CustomAdapter adapter;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottom_navigation;
    static String fullName;
    static String fName;
    static String lName;
    private SearchView searchView;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        toolbar = findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        drawerLayout = findViewById(R.id.drawer_layout);
        userModelList = new ArrayList<>();
        name = findViewById(R.id.firstName);

        getUserData();
        navInit();
        setMenu();

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void navInit() {
        header_name = navigationView.getHeaderView(0).findViewById(R.id.header_name);
        header_email = navigationView.getHeaderView(0).findViewById(R.id.header_email);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);

                switch (item.getItemId()) {
                    case R.id.myRidesItem:
                        HomeUser.this.startActivity(new Intent(HomeUser.this, MyRidesUser.class));
                        break;
                    case R.id.myFavoritesItem:
                        replaceFragments(new My_Favorites());
                        break;
                    case R.id.myPaymentsItem:
                        replaceFragments(new My_Payments());
                        break;
                    case R.id.driver:
                        HomeUser.this.startActivity(new Intent(HomeUser.this, DriverSignup.class));
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }
    public void setMenu(){
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.searchItem) {
                    searchDriver();
                    replaceFragments(new fragment_recyclerview());

                }else if (item.getItemId()==R.id.help){
                    Toast.makeText(getApplicationContext(), "Help clicked", Toast.LENGTH_SHORT).show();

                }else if (item.getItemId() == R.id.log_out){
                    FirebaseAuth.getInstance().signOut();
                    HomeUser.this.startActivity(new Intent(HomeUser.this, Login.class));
                    Toast.makeText(getApplicationContext(), "log out clicked", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

        });
    }
    public void searchDriverData() {
        Toast.makeText(HomeUser.this, newText, Toast.LENGTH_SHORT).show();

        db.collection("Users")
                .whereEqualTo("Status", "driver")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        progressDialog.dismiss();
                        userModelList.clear();
                        for (DocumentSnapshot documentSnapshot: task.getResult()){
                            UserModel userModel = new UserModel(documentSnapshot.getString("Status"),
                                    documentSnapshot.getString("First Name"),
                                    documentSnapshot.getString("Surname"),
                                    documentSnapshot.getString("Phone Number"));
                            userModelList.add(userModel);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeUser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void searchDriver(){
        menuItem = toolbar.getMenu().findItem(R.id.searchItem);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        menuItem.expandActionView();

        searchDriverData();
        List<UserModel> searchList = new ArrayList<>();

        MenuItem menuItem = toolbar.getMenu().findItem(R.id.searchItem);
        searchView = (SearchView) menuItem.getActionView();
        menuItem.expandActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                Log.i("well", " this worked");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                newText = query;
                searchList.clear();
                searchView.setQueryHint("Search driver");
                Log.i("well", " this worked");
                if (!query.isEmpty()) {
                    for(int i = 0; i < userModelList.size(); i++){
                        fName = userModelList.get(i).getFirstName();
                        lName = userModelList.get(i).getLastName();
                        String userStatus = userModelList.get(i).getStatus();
                        String phoneNumber = userModelList.get(i).getPhoneNumber();
                        fullName = fName + " " + lName;

                        if (fName.toLowerCase().contains(query.toLowerCase()) || lName.toLowerCase().contains(query.toLowerCase())){
                            UserModel model = new UserModel(userStatus, fName, lName, phoneNumber);
                            searchList.add(model);
                            Toast.makeText(HomeUser.this, "size of search list is " + searchList.size(), Toast.LENGTH_SHORT).show();

                        } else if (fullName.toLowerCase().contains(query.toLowerCase())){
                            UserModel model = new UserModel(userStatus, fName, lName, phoneNumber);
                            searchList.add(model);
                        }

                    }

                } else if(query.isEmpty()){
                    searchList.clear();
                }

                recyclerView = findViewById(R.id.searchRecyclerView);
                adapter = new CustomAdapter(searchList, HomeUser.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeUser.this));

                return true;
                }

        });

    }

    public void getUserData(){
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
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    
    // don't write your code here.....it won't work /*UNDERSTOOD*/
    //<<<<<<<<<<<<<<<<<<<<<<<<Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
        }
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                   switch (item.getItemId()){
                       case R.id.notifications:
                           replaceFragments(new fragment_notifications());
                           break;

                       case R.id.home:
                           replaceFragments(new fragment_home());
                           break;

                       case R.id.profile:
                          startActivity(new Intent(HomeUser.this, UserProfile.class));
                           break;
                   }
                    return true;
                }
            };

    private void setActiveFragment(){
      replaceFragments(new fragment_home());
    }

    private void replaceFragments(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        }

    @Override
    public void onDriverClick(int position) {
        Toast.makeText(HomeUser.this, "you clicked position " + position, Toast.LENGTH_SHORT).show();
    }
}
