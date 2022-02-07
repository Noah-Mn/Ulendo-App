package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EditUserProfile extends AppCompatActivity {
    private final String TAG = "Edit User Profile";
    TextInputEditText edit_full_name, edit_email_address, edit_phone_number;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    String firstName, lastName, phoneNumber, email;
    ImageView E_profile_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        E_profile_back = findViewById(R.id.E_profile_back);
        edit_full_name = findViewById(R.id.edit_full_name);
        edit_phone_number = findViewById(R.id.edit_phone_number);
        edit_email_address = findViewById(R.id.edit_email_address);
        currentUser = auth.getCurrentUser();

        E_profile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditUserProfile.this, HomeUser.class));
            }
        });
        getMoreUserData();
    }

    public void getMoreUserData(){
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
                                phoneNumber = document.getString("Phone Number");
                                edit_full_name.setText(new StringBuilder().append(firstName).append(" ").append(lastName).toString());
                                edit_phone_number.setText(phoneNumber);
                                email = document.getString("Email Address");
                                edit_email_address.setText(email, TextView.BufferType.EDITABLE);
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