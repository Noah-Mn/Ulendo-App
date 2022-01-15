package com.example.ulendoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Signup extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    MaterialButton nextBtn;
    TextInputEditText textFirstName, textLastName, textPhoneNumber;
    AutoCompleteTextView textGender;
    TextInputLayout materialFistName, materialLastName, materialPhoneNumber, materialGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textFirstName = findViewById(R.id.textFirstName);
        textLastName = findViewById(R.id.textLastName);
        textPhoneNumber = findViewById(R.id.textPhoneNumber);
        textGender = findViewById(R.id.textGender);
        materialFistName = findViewById(R.id.materialFistName);
        materialLastName = findViewById(R.id.materialLastName);
        materialPhoneNumber = findViewById(R.id.materialPhoneNumber);
        materialGender = findViewById(R.id.materialGender);
        nextBtn = findViewById(R.id.nextBtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSignUp();
            }
        });


    }
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null){
            reload();
        }
    }

    public void reload(){

    }

    private void performSignUp(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        String fistName = Objects.requireNonNull(textFirstName.getText()).toString();
        String lastName = Objects.requireNonNull(textLastName.getText()).toString();
        String phoneNumber = Objects.requireNonNull(textPhoneNumber.getText()).toString();
        String gender = textGender.getText().toString();

        if (fistName.isEmpty()){
            materialFistName.setError("Please enter first name");
        }else if (lastName.isEmpty()){
            materialLastName.setError("Please enter last name");
        }else if (phoneNumber.isEmpty()){
            materialPhoneNumber.setError("Please enter phone number");
        }else if (gender.isEmpty()){
            materialGender.setError("Select gender");
        }

    }
}