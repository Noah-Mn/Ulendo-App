package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
}