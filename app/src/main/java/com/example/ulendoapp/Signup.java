package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Objects;

public class Signup extends AppCompatActivity {
    private MaterialButton nextBtn;
    private TextInputEditText textFirstName, textLastName, textPhoneNumber;
    private MaterialSpinner textGender;
    private TextInputLayout materialFistName, materialLastName, materialPhoneNumber, materialGender;

    private String fistName;
    private String lastName;
    private String phoneNumber;
    private String gender;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textFirstName = findViewById(R.id.textFirstName);
        textLastName = findViewById(R.id.textLastName);
        textPhoneNumber = findViewById(R.id.textPhoneNumber);
        textGender = findViewById(R.id.genderSpinner);
        materialFistName = findViewById(R.id.materialFistName);
        materialLastName = findViewById(R.id.materialLastName);
        materialPhoneNumber = findViewById(R.id.materialPhoneNumber);
        materialGender = findViewById(R.id.materialGenderSpinner);
        nextBtn = findViewById(R.id.nextBtn);

        progressDialog = new ProgressDialog(this);

        setSpinner();

        nextBtn.setOnClickListener(view -> {
            if(validateFirstForm()) {
                Intent intent = new Intent(Signup.this, FinalSignup.class);
                intent.putExtra("firstName", fistName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("gender", gender);

                Toast.makeText(Signup.this, fistName + " " + lastName + " is " + gender, Toast.LENGTH_LONG).show();
                startActivity(intent);
            } else {
                Toast.makeText(Signup.this, lastName + " is " + gender, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setSpinner(){
        textGender = findViewById(R.id.genderSpinner);
        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");

        textGender.setItems(genderList);
        textGender.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>)(view, position, id, item) ->
                gender = item);
    }

    public boolean validateFirstForm(){

        fistName = Objects.requireNonNull(textFirstName.getText()).toString();
        lastName = Objects.requireNonNull(textLastName.getText()).toString();
        phoneNumber = Objects.requireNonNull(textPhoneNumber.getText()).toString();
        boolean valid = false;

        try {
            if (fistName.isEmpty()) {
                materialFistName.setError("Please enter first name");
            } else if (lastName.isEmpty()) {
                materialLastName.setError("Please enter last name");
            } else if (phoneNumber.isEmpty()) {
                materialPhoneNumber.setError("Please enter phone number");
            } else if (gender == null) {
                materialGender.setError("Select gender");
            } else{
                valid = true;
            }
        }catch(Exception e){}
        return valid;
    }
}














