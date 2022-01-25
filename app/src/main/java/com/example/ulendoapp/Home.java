package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

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
import com.google.firebase.firestore.SnapshotMetadata;

public class Home extends AppCompatActivity {
    private MaterialTextView firstName;
    private String name;
    private String userID;
    FirebaseFirestore db;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setSupportActionBar(toolbar);
        toolbar = findViewById(R.id.toolbar);
        NavigationBarMenu

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
}