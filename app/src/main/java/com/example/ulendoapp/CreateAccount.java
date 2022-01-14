package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    MaterialButton  nextBtn;
    TextInputEditText  textFirstName, textLastName, textPhoneNumber;
    AutoCompleteTextView textGender;
    TextInputLayout materialFistName, materialLastName, materialPhoneNumber, materialGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textFirstName = findViewById(R.id.textFirstName);
        textLastName = findViewById(R.id.textLastName);
        textPhoneNumber = findViewById(R.id.textPhoneNumber);
        textGender = findViewById(R.id.textGender);
        materialFistName = findViewById(R.id.materialFistName);
        materialLastName = findViewById(R.id.materialLastName);
        materialPhoneNumber = findViewById(R.id.materialPhoneNumber);
        materialGender = findViewById(R.id.materialGender);
        nextBtn = findViewById(R.id.nextBtn);

//
//        EditText lastName;
//        EditText phoneNumber;
//        TextView gender;
//
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        db = FirebaseFirestore.getInstance();
//
//        Map<String, Object> user = new HashMap<>();
//
//        user.put("Name", "Lonjezo");
//        user.put("Location", "Zomba");
//        user.put("Gender", "Male");
//
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("tag", "inserted successfully");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("tagg", "error! failed");
//                    }
//                });

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

    private void performSignUp(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        String fistName = textFirstName.getText().toString();
        String lastName = textLastName.getText().toString();
        String phoneNumber = textPhoneNumber.getText().toString();
        String gender = textGender.getText().toString();

        if (fistName.isEmpty()){
            materialFistName.setError("Please enter first name");
        }else if (lastName.isEmpty()){
            materialLastName.setError("Please enter last name");
        }else if (phoneNumber.isEmpty()){
            materialPhoneNumber.setError("Please enter phone number");
        }else if (gender.isEmpty()){
            materialGender.setError("Select gender");
        }else {

        }

    }
}











