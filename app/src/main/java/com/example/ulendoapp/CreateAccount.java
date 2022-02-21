package com.example.ulendoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateAccount extends AppCompatActivity {
    private static final String TAG = "tag";

    private String firstName, lastName, birthday, gender, phoneNumber, email, nationalId, physicalAddress, password, confirmPassword;
    private TextInputEditText textPassword, textConfirmPassword, textEmailAddress;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private ImageView signupBackBtn;
    private Button signupBtn;
    private TextInputLayout materialPassword, materialConfirmPassword, materialEmail;
    public boolean valid;
    String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        db = FirebaseFirestore.getInstance();

        textEmailAddress = findViewById(R.id.email_address);
        textPassword = findViewById(R.id.password);
        textConfirmPassword = findViewById(R.id.confirm_password);

        materialPassword = findViewById(R.id.material_password);
        materialConfirmPassword = findViewById(R.id.material_confirm_password);
        materialEmail = findViewById(R.id.material_email_address);

        signupBtn = findViewById(R.id.create_account_btn);
        signupBackBtn = findViewById(R.id.create_account_back_btn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserDetails();
                if(validateFinalForm()) {
                    performSignUp();
                }
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
        valid = false;
        email = Objects.requireNonNull(textEmailAddress.getText().toString());
        password = Objects.requireNonNull(textPassword.getText().toString());
        confirmPassword = Objects.requireNonNull(textConfirmPassword.getText().toString());

        try{
            if(email.isEmpty()){
                materialEmail.setError("Please enter email address");
            }else if (!email.equals(emailPattern)) {
                materialEmail.setError("Invalid email address");
            }
            if(password.isEmpty()){
                materialPassword.setError("Please enter password");
            } else if (password.length() <= 6) {
                materialPassword.setError("Password must be more than 6 characters");
            } else if(password != confirmPassword){
                materialConfirmPassword.setError("Passwords do not match");
            }else {
//                validateEmail();
                valid = true;

            }


        } catch(Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.getMessage());
        }

        return valid;
    }

    private void validateEmail(){
        getUserDetails();
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
                                return;
                            }
                        } else {
                            Log.d(TAG, "Email address not in use", task.getException());
                        }
                    }
                });
    }

    private void getUserDetails() {
        Intent intent = getIntent();

        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        birthday = intent.getStringExtra("birthday");
        gender = intent.getStringExtra("gender");
        phoneNumber = intent.getStringExtra("phoneNumber");
        email = intent.getStringExtra("email");
        nationalId = intent.getStringExtra("nationalId");
        physicalAddress = intent.getStringExtra("physicalAddress");

    }

    private void addUser(){
        if(validateFinalForm()){
            db = FirebaseFirestore.getInstance();
            Map<String, Object> user = new HashMap<>();

            user.put("First Name", firstName);
            user.put("Surname", lastName);
            user.put("Date of Birth", birthday);
            user.put("Gender", gender);
            user.put("Phone Number", phoneNumber);
            user.put("Email Address", email);
            user.put("National ID", nationalId);
            user.put("Physical Address", physicalAddress);
            user.put("Status", "customer");
            user.put("Number of Trips", "N/A");
            user.put("Rating", "N/A");

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
    }

    private void performSignUp(){
        firebaseAuth = FirebaseAuth.getInstance();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing up please wait...");
        progressDialog.setTitle("UserSignup");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        getUserDetails();

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

    public void reload(){

    }

    private void updateUI(FirebaseUser user) {
    }
}