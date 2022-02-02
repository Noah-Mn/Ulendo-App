package com.example.ulendoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Objects;

public class UserSignup extends AppCompatActivity {
    private MaterialButton nextBtn;
    private TextInputEditText textFirstName, textLastName, textPhoneNumber;
    private MaterialSpinner textGender;
    private TextInputLayout materialFistName, materialLastName, materialPhoneNumber, materialGender;
    private ProgressDialog progressDialog;
    private String firstName, lastName, phoneNumber, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textFirstName = findViewById(R.id.textCarModel);
        textLastName = findViewById(R.id.textNumberPlate);
        textPhoneNumber = findViewById(R.id.textPhysicalAddress);
        textGender = findViewById(R.id.genderSpinner);
        materialFistName = findViewById(R.id.materialCarModel);
        materialLastName = findViewById(R.id.materialNumberPlate);
        materialPhoneNumber = findViewById(R.id.materialPhysicalAddress);
        materialGender = findViewById(R.id.materialGenderSpinner);
        nextBtn = findViewById(R.id.submitBtn);

        progressDialog = new ProgressDialog(this);

        setSpinner();

        nextBtn.setOnClickListener(view -> {
            if(validateFirstForm()) {
                Intent intent = new Intent(UserSignup.this, CreateAccount.class);
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("gender", gender);

                Toast.makeText(UserSignup.this, firstName + " " + lastName + " is " + gender, Toast.LENGTH_LONG).show();
                startActivity(intent);
            } else {
                Toast.makeText(UserSignup.this, lastName + " is " + gender, Toast.LENGTH_LONG).show();
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
        firstName = Objects.requireNonNull(textFirstName.getText()).toString();
        lastName = Objects.requireNonNull(textLastName.getText()).toString();
        phoneNumber = Objects.requireNonNull(textPhoneNumber.getText().toString());
        boolean valid = false;

        try {
            if (firstName.isEmpty()) {
                materialFistName.setError("Please enter first name");
            } else if(firstName.length() > 20){
                materialFistName.setError("Invalid first name");
            } else if (lastName.isEmpty()) {
                materialLastName.setError("Please enter last name");
            } else if(lastName.length() > 20){
                materialLastName.setError("Invalid surname");
            }else if (phoneNumber.isEmpty()) {
                materialPhoneNumber.setError("Please enter phone number");
            } else if(phoneNumber.length() > 15){
                materialPhoneNumber.setError("Invalid phone number");
            } else if (gender == null) {
                materialGender.setError("Select gender");
            } else{
                valid = true;
            }
        }catch(Exception e){}
        return valid;
    }
}














