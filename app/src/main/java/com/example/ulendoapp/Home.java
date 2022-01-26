package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationBarMenu;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotMetadata;

public class Home extends AppCompatActivity {
    private MaterialTextView name, excraMark, text, rideText;
    private String userName, userText, userMark, userRideText, firstName;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private MaterialToolbar toolbar;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();

        name = findViewById(R.id.firstName);
        excraMark = findViewById(R.id.excraMark);
        text = findViewById(R.id.text);
        rideText = findViewById(R.id.rideText);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getUserFirstName();

//        Toast.makeText(Home.this, firstName, Toast.LENGTH_LONG).show();

//        NavigationBarMenu

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

//        firstName = (MaterialTextView) findViewById(R.id.firstName);
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (user != null){
//            DocumentReference documentReference = db.collection("Users").document();
//            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()){
//                        User userProfile = (User) document.getData();
//                    }
//                }
//            });
//            userID = user.getUid();
//
//            //name = user.getDisplayName();
//            //name.setFocusable(False);
//        }
//
//        //firstName.setText((CharSequence)name);

    }

    //  @Override
//    public void onBackPressed(){
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }else {
//            super.onBackPressed();
//        }
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
                                name.setText(firstName);
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
}