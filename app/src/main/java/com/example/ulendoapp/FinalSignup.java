package com.example.ulendoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FinalSignup extends AppCompatActivity {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String gender;
    private String email;
    private String password;
    private String confirmPassword;
    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_signup);

        Intent intent = getIntent();
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        phoneNumber = intent.getStringExtra("phoneNumber");
        gender = intent.getStringExtra("gender");

        signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignup = new Intent(FinalSignup.this, Home.class);
            }
        });
    }


//    public void onStart(){
//        super.onStart();
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        if (currentUser != null){
//            reload();
//        }
//    }

        public void reload(){

        }

        private void performSignUp(){
      /*  firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();



        if (fistName.isEmpty()){
            materialFistName.setError("Please enter first name");
        }else if (lastName.isEmpty()){
            materialLastName.setError("Please enter last name");
        }else if (phoneNumber.isEmpty()){
            materialPhoneNumber.setError("Please enter phone number");
        }else if (gender.isEmpty()){
            materialGender.setError("Select gender");
        }else{
            progressDialog.setMessage("Logging in please wait...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
/*
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Log.w(TAG, "Login:success", task.getException());
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        updateUI(user);
                        startActivity(new Intent(Signup.this, Login.class));
                    }else {
                        progressDialog.dismiss();
                        Log.w(TAG, " Signup:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });*/
        }

    private void updateUI(FirebaseUser user) {
    }
}