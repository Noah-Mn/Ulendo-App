package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {
    FirebaseFirestore db;
    Button next;
    EditText firstName;
    EditText lastName;
    EditText phoneNumber;
    TextView gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();

        user.put("Name", "Lonjezo");
        user.put("Location", "Zomba");
        user.put("Gender", "Male");

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("tag", "inserted successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("tagg", "error! failed");
                    }
                });

//        next = findViewById(R.id.nextBtn);
//        firstName = findViewById(R.id.textFirstName);
//        lastName = findViewById(R.id.textLastName);
//        phoneNumber = findViewById(R.id.textPhoneNumber);
//        gender = findViewById(R.id.textGender);
//
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String fName = firstName.getText().toString();
//                String lName = lastName.getText().toString();
//                int pNumber = Integer.parseInt(phoneNumber.getText().toString());
//                String genda = "male/female";
//            }
//        });

    }
}











