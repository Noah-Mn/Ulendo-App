package com.example.ulendoapp.activityClasses;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ulendoapp.databinding.ActivityUserProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

/**
 * The type User profile.
 */
public class UserProfile extends AppCompatActivity {
    /**
     * The Binding.
     */
    ActivityUserProfileBinding binding;
    /**
     * The Db.
     */
    FirebaseFirestore db;
    /**
     * The Auth.
     */
    FirebaseAuth auth;
    /**
     * The Firebase user.
     */
    FirebaseUser firebaseUser;
    private final String TAG = "User Profile";
    /**
     * The First name.
     */
    String firstName, /**
     * The Last name.
     */
    lastName, /**
     * The Number.
     */
    number, /**
     * The Encoded image.
     */
    encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        getUserData();

        binding.profileBack.setOnClickListener(v -> startActivity(
                new Intent(UserProfile.this, HomeUser.class)));

        binding.userEditProfile.setOnClickListener(v -> startActivity(
                new Intent(UserProfile.this, EditUserProfile.class)));

        binding.aLotOfStuff.setOnClickListener(view -> {
            Intent intent = new Intent(UserProfile.this, PersonalInfo.class);
            startActivity(intent);
        });

        binding.termsConditions.setOnClickListener(view -> startActivity(
                new Intent(UserProfile.this, TermsAndConditionsCustomer.class)));
    }

    /**
     * Get user data.
     */
    @SuppressLint("SetTextI18n")
    public void getUserData(){
        db.collection("Users")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            firstName = document.getString("First Name");
                            lastName = document.getString("Surname");
                            number = document.getString("Phone Number");
                            encodedImage = document.getString("Profile Pic");
                            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            binding.userProfilePic.setImageBitmap(bitmap);
                            binding.userName.setText(firstName + " " + lastName);
                            binding.userNumber.setText(number);

                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    /**
     * Get email string.
     *
     * @return the string
     */
    public String getEmail(){
        String emailAddress;
        emailAddress = firebaseUser.getEmail();
        return emailAddress;
    }

}