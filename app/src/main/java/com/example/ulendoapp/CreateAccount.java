package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {
    private static final String TAG = "EmailPassword";

    private String firstName, lastName, phoneNumber, gender;
    private String email;
    private String password;
    private TextInputEditText textEmail, textPassword, textConfirmPassword;
    private TextInputLayout materialEmail;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    boolean deny;
    private Button  signupBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        db = FirebaseFirestore.getInstance();

        textEmail = findViewById(R.id.textEmailAddress);
        textPassword = findViewById(R.id.textPassword);
        textConfirmPassword = findViewById(R.id.textConfirmPassword);
        signupBackBtn = findViewById(R.id.signup_backBtn);

        Intent intent = getIntent();
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        phoneNumber = intent.getStringExtra("phoneNumber");
        gender = intent.getStringExtra("gender");

        Button confirmBtn = findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSignUp();
            }
        });

        signupBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount.super.onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CreateAccount.this, Login.class));
    }

    private boolean validateFinalForm(){
        boolean valid = false;
        materialEmail = findViewById(R.id.materialEmailAddress);
        TextInputLayout materialPassword = findViewById(R.id.materialPassword);
        TextInputLayout materialConfirmPassword = findViewById(R.id.materialConfirmPassword);
        email = textEmail.getText().toString();
        password = textPassword.getText().toString();
        String confirmPassword = textConfirmPassword.getText().toString();

        try {
            String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
            if (email.isEmpty()) {
                materialEmail.setError("Please enter email address");
            } else if(!email.matches(emailPattern)) {
                materialEmail.setError("Please enter a valid email address");
            } else if (password.isEmpty() && password.length() <= 3) {
                materialPassword.setError("Password must be more than 3 characters");
                if(!password.equals(confirmPassword)){
                    materialPassword.setError("Passwords do not match");
                }
            } else if (!password.equals(confirmPassword)) {
                     materialConfirmPassword.setError("passwords do not match");
            } else{
                valid = true;
            }
        }catch(Exception e){}

        return valid;
    }

    private void validateEmail(){
        db.collection("Users")
                .whereEqualTo("Email Address", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "Email already exist!");
                                materialEmail.setError("Email address already in use");
//                                Toast.makeText(CreateAccount.this, "email already exist " + email, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "Email address not in use", task.getException());
                        }
                    }
                });
    }

    private void addUser(){
        db = FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Map<String, Object> user = new HashMap<>();

        user.put("First Name", firstName);
        user.put("Surname", lastName);
        user.put("Phone Number", phoneNumber);
        user.put("Gender", gender);
        user.put("Email Address", email);
        user.put("Date of Birth", "N/A");
        user.put("Status", "customer");
        user.put("National ID", "N/A");
        user.put("Current Location", "N/A");
        user.put("Destination", "N/A");
        user.put("Pickup Time", "N/A");
        user.put("Number of People", "N/A");
        user.put("Luggage", "N/A");
        user.put("Number of Trips", "N/A");
        user.put("Rating", "N/A");
        user.put("Complaints", "N/A");


        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "inserted successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "error! failed");
                    }
                });

    }

    private void performSignUp(){
        firebaseAuth = FirebaseAuth.getInstance();
        ProgressDialog progressDialog = new ProgressDialog(this);

        if(validateFinalForm()) {
            progressDialog.setMessage("Signing up please wait...");
            progressDialog.setTitle("UserSignup");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            validateEmail();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        updateUI(user);

                        startActivity(new Intent(CreateAccount.this, HomeUser.class));
                        Toast.makeText(getApplicationContext(), "Sign up successful", Toast.LENGTH_SHORT).show();
                        addUser();
                    } else {
                        progressDialog.dismiss();
                        Log.w(TAG, " UserSignup:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "User Signup failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        }
    }

    public void reload(){

    }

    private void updateUI(FirebaseUser user) {
    }
}