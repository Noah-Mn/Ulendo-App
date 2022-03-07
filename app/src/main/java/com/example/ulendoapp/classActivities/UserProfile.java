package com.example.ulendoapp.classActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ulendoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;

public class UserProfile extends AppCompatActivity {
    ImageView profile_back, user_edit_profile;
    MaterialTextView user_name, user_number;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    RoundedImageView userprofilePic;
    private final String TAG = "User Profile";
    String firstName, lastName, number, encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        profile_back = findViewById(R.id.profile_back);
        user_name = findViewById(R.id.user_name);
        user_number = findViewById(R.id.user_number);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        user_edit_profile = findViewById(R.id.user_edit_profile);
        userprofilePic = findViewById(R.id.user_profile_pic);
        getUserData();

        profile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, HomeUser.class));
            }
        });

        user_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, EditUserProfile.class));
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
                                number = document.getString("Phone Number");
                                encodedImage = document.getString("Profile Pic");
                                byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                userprofilePic.setImageBitmap(bitmap);

                                user_name.setText(new StringBuilder().append(firstName).append(" ").append(lastName).toString());
                                user_number.setText(number);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public String getEmail(){
        String emailAddress;
        emailAddress = firebaseUser.getEmail();
        return emailAddress;
    }

}