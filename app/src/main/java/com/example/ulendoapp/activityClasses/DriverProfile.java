package com.example.ulendoapp.activityClasses;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ulendoapp.databinding.ActivityDriverProfileBinding;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DriverProfile extends AppCompatActivity {
    ActivityDriverProfileBinding binding;
    PreferenceManager preferenceManager;

    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;
    private final String TAG = "Driver Profile";
    String fName, lastName, encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDriverProfileBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        binding.editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(DriverProfile.this, EditDriverProfile.class);
            startActivity(intent);
        });

        binding.aLotOfStuff.setOnClickListener(view -> {
            Intent intent = new Intent(DriverProfile.this, PersonalInfo.class);
            startActivity(intent);
        });

        binding.termsConditions.setOnClickListener(view -> {
            Intent intent = new Intent(DriverProfile.this, TermsAndConditions.class);
            startActivity(intent);
        });

        binding.profileBack.setOnClickListener(v -> startActivity(
                new Intent(DriverProfile.this, HomeDriver.class)));
        getUserName();
    }

    @SuppressLint("SetTextI18n")
    public void getUserName() {
        db.collection("Users")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            fName = document.getString("First Name");
                            lastName = document.getString("Surname");
                            encodedImage = document.getString(Constants.KEY_IMAGE);
                            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            binding.profileImage.setImageBitmap(bitmap);
                            binding.profileName.setText(fName + " " + lastName);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public String getEmail() {
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }
}