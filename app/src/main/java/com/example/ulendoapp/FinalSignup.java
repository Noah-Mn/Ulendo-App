package com.example.ulendoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FinalSignup extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private String firstName, lastName, phoneNumber, gender;
    private String email, password, confirmPassword;
    private Button confirmBtn,backBtn;
    private TextInputEditText textEmail, textPassword, textConfirmPassword;
    private TextInputLayout materialEmail, materialPassword, materialConfirmPassword;
    private ProgressDialog progressDialog;
    private DatabaseReference reference;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db;

    private String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_signup);

        Intent intent = getIntent();
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        phoneNumber = intent.getStringExtra("phoneNumber");
        gender = intent.getStringExtra("gender");

        confirmBtn = findViewById(R.id.confirmBtn);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(FinalSignup.this, Signup.class));
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFinalForm()){

                    performSignUp(email,password);
                    Intent intentSignup = new Intent(FinalSignup.this, Login.class);
//                    Toast.makeText(FinalSignup.this, "Successfully Signed up", Toast.LENGTH_LONG).show();
                    startActivity(intentSignup);
                }
            }
        });
    }
    private boolean validateFinalForm(){
        materialEmail = findViewById(R.id.materialEmailAddress);
        materialPassword = findViewById(R.id.materialPassword);
        materialConfirmPassword = findViewById(R.id.materialConfirmPassword);
        textEmail = findViewById(R.id.textEmailAddress);
        textPassword = findViewById(R.id.textPassword);
        textConfirmPassword = findViewById(R.id.textConfirmPassword);

        email = textEmail.getText().toString();
        password = textPassword.getText().toString();
        confirmPassword = textConfirmPassword.getText().toString();

        boolean valid = false;

        try {
            if (!email.matches(emailPattern)) {
                materialEmail.setError("Please enter a valid email address");
            }else if (email.isEmpty()) {
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


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //User is already signed in
            reload();
        }
    }
    private void performSignUp(String email, String password) {
            reference = FirebaseDatabase.getInstance().getReference("Users");
//            FirebaseUser user = firebaseAuth.getCurrentUser();

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Signing up please wait...");
            progressDialog.setTitle("Signup");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
//                        User user = new User(firstName, lastName, phoneNumber, gender, email, password, confirmPassword);
//                        reference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
//                                 .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()){
//                                    progressDialog.dismiss();
//                                    Log.w(TAG, "Signup:success", task.getException());
//                                    Toast.makeText(getApplicationContext(), "Signup successful", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(FinalSignup.this, Login.class));
//                                }else {
//                                    progressDialog.dismiss();
//                                    Log.w(TAG, " Signup:failure", task.getException());
//                                    Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        });
                        addUser();

                    } else {
                        progressDialog.dismiss();
                        Log.w(TAG, " Signup:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    private void reload(){

    }
    private void updateUI( FirebaseUser user){

    }
    public void addUser(){
        Map<String, String> user = new HashMap<>();
        user.put("First Name", firstName);
        user.put("Last Name", lastName);
        user.put("Email Address", email);
        user.put("Gender", gender);
        user.put("Password", password);
        user.put("State", "Customer");

        db.collection("Users")
                .add(user)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        progressDialog.dismiss();
                        Log.w(TAG, "Signup:success", task.getException());
                        Toast.makeText(getApplicationContext(), "Signup successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FinalSignup.this, Login.class));
                    }
                });


    }

}