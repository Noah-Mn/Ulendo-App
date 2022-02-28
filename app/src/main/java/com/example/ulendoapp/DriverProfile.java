package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ulendoapp.databinding.ActivityDriverProfileBinding;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DriverProfile extends AppCompatActivity {
    ActivityDriverProfileBinding binding;
    PreferenceManager preferenceManager;

    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;
    private final String TAG = "Driver Profile";
    String fName, lastName;
    MaterialTextView textView;
    ImageView profile_back, edit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDriverProfileBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());

        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.profileImage.setImageBitmap(bitmap);

       LinearLayoutManager layoutManager = new LinearLayoutManager(this);
       layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


//       profile_back = findViewById(R.id.profile_back);
//       edit_profile = findViewById(R.id.edit_profile);
       firebaseAuth = FirebaseAuth.getInstance();
       currentUser = firebaseAuth.getCurrentUser();
       db = FirebaseFirestore.getInstance();
//        textView = findViewById(R.id.profile_name);

       binding.editProfile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(DriverProfile.this, EditDriverProfile.class);
               startActivity(intent);
           }
       });

       binding.profileBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(DriverProfile.this, HomeDriver.class));
           }
       });
        getUserName();
    }
    public void getUserName(){
        db.collection("Users")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                fName = document.getString("First Name");
                                lastName = document.getString("Surname");

                                binding.profileName.setText(new StringBuilder().append(fName).append(" ").append(lastName).toString());

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