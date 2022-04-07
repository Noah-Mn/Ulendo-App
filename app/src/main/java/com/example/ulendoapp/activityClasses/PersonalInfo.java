package com.example.ulendoapp.activityClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.example.ulendoapp.databinding.ActivityPersonalInfoBinding;
import com.example.ulendoapp.utilities.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class PersonalInfo extends AppCompatActivity {
    ActivityPersonalInfoBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;
    String fName, lastName, encodedImage,gender, email, birthday, phoneNumber, nationalID, physicalAddr;
    private final String TAG = "Personal Info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        getInformation();
    }

    public void getInformation(){
        db.collection("Users")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            fName = document.getString("First Name");
                            lastName = document.getString("Surname");
                            email = document.getString("Email Address");
                            gender = document.getString("Gender");
                            phoneNumber = document.getString("Phone Number");
                            birthday = document.getString("Date of Birth");
                            nationalID = document.getString("National ID");
                            physicalAddr = document.getString("Physical Address");
                            encodedImage = document.getString(Constants.KEY_IMAGE);
                            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            binding.profileImage.setImageBitmap(bitmap);
                            binding.infoDisplayName.setText(new StringBuilder().append(fName).append(" ").append(lastName).toString());
                            binding.infoAddress.setText(physicalAddr);
                            binding.infoBirthday.setText(birthday);
                            binding.infoID.setText(nationalID);
                            binding.infoEmail.setText(email);
                            binding.infoNumber.setText(phoneNumber);
                            binding.infoGender.setText(gender);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }
}