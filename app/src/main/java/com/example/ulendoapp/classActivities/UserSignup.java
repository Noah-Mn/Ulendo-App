package com.example.ulendoapp.classActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ulendoapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Objects;

public class UserSignup extends AppCompatActivity {
    private MaterialButton nextBtn;
    private TextInputEditText textFirstName, textLastName, textPhoneNumber,
            textBirthday, textNatinalId, textPhysicalAddress;
    private MaterialSpinner textGender;
    private TextInputLayout materialFistName, materialLastName, materialBirthday, materialGender,
            materialPhoneNumber, materialNationalId, materialPhysicalAddress;
    private ProgressDialog progressDialog;
    public String firstName, lastName, birthday, gender, phoneNumber, email, nationalId, physicalAddress;
    public final String TAG = "tag";
    private FirebaseFirestore db;
    public boolean valid;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = FirebaseFirestore.getInstance();

        textFirstName = findViewById(R.id.first_name);
        textLastName = findViewById(R.id.surname);
        textBirthday = findViewById(R.id.date_of_birth);
        textGender = findViewById(R.id.genderSpinner);
        textPhoneNumber = findViewById(R.id.phone_number);
        textNatinalId = findViewById(R.id.national_id);
        textPhysicalAddress = findViewById(R.id.physical_address);

        materialFistName = findViewById(R.id.material_firstname);
        materialLastName = findViewById(R.id.material_surname);
        materialBirthday = findViewById(R.id.material_date_of_birth);
        materialGender = findViewById(R.id.material_gender);
        materialPhoneNumber = findViewById(R.id.material_phone_number);
        materialNationalId = findViewById(R.id.material_national_id);
        materialPhysicalAddress = findViewById(R.id.material_physical_address);

        nextBtn = findViewById(R.id.signup_next_Btn);
        progressDialog = new ProgressDialog(this);
        setSpinner();


        nextBtn.setOnClickListener(view -> setIntentExtras());
    }

    private void setIntentExtras() {
        if(validateFirstForm()){
                intent = new Intent(UserSignup.this, CreateAccount.class);
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("birthday", birthday);
                intent.putExtra("gender", gender);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("nationalId", nationalId);
                intent.putExtra("physicalAddress", physicalAddress);

                startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UserSignup.this, Login.class));

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

    public void getSignupDetails(){

        firstName = Objects.requireNonNull(textFirstName.getText().toString());
        lastName = Objects.requireNonNull(textLastName.getText().toString());
        birthday = Objects.requireNonNull(textBirthday.getText().toString());
        gender = Objects.requireNonNull(textGender.getText().toString());
        phoneNumber = Objects.requireNonNull(textPhoneNumber.getText().toString());
        nationalId = Objects.requireNonNull(textNatinalId.getText().toString());
        physicalAddress = Objects.requireNonNull(textPhysicalAddress.getText().toString());

    }

    public boolean validateFirstForm(){
        getSignupDetails();
        valid = false;
        try {
            if (firstName.length() > 20) {
                materialFistName.setError("Invalid first name");
            }else if(firstName.isEmpty()){
                materialFistName.setError("Please enter first name");
            } else if (lastName.length() > 20) {
                materialLastName.setError("Please enter surname");
            } else if(lastName.isEmpty()){
                materialLastName.setError("Invalid surname");
            } else if(birthday.isEmpty()){
                materialBirthday.setError("Please set birthday");
            } else if (gender.isEmpty()) {
                materialGender.setError("Select gender");
                Toast.makeText(getApplicationContext(), "Select gender", Toast.LENGTH_SHORT).show();
            }else if (phoneNumber.isEmpty()) {
                materialPhoneNumber.setError("Please enter phone number");
            } else if(phoneNumber.length() > 12){
                materialPhoneNumber.setError("Invalid phone number");
            } else if(nationalId.isEmpty()){
                materialNationalId.setError("please enter a national ID");
            }else if (physicalAddress.isEmpty()) {
                materialPhysicalAddress.setError("please enter your physical address");
            } else {
                valid = true;
            }

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return valid;
    }
}














