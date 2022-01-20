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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FinalSignup extends AppCompatActivity {
    private String firstName, lastName, phoneNumber, gender;
    private String email, password, confirmPassword;
    private Button signupBtn;
    private TextInputEditText textEmail, textPassword, textConfirmPassword;
    private TextInputLayout materialEmail, materialPassword, materialConfirmPassword;
    private ProgressDialog progressDialog;
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

        signupBtn = findViewById(R.id.confirmBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFinalForm()){
                    saveToDatabase();
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

    private void saveToDatabase(){
        db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();

        user.put("First Name", firstName);
        user.put("Surname", lastName);
        user.put("Phone Number", phoneNumber);
        user.put("Gender", gender);
        user.put("Email Address", email);
        user.put("Password", password);
        user.put("Status", "customer ");

        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("tag", "inserted successfully");
                        Toast.makeText(FinalSignup.this, "Successfully Signed up", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("tagg", "error! failed");
                        Toast.makeText(FinalSignup.this, "error!! failed to signup", Toast.LENGTH_LONG).show();
                    }
                });

        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Log.d("data received", document.getId() + " => " + document.getData());
                                Toast.makeText(FinalSignup.this, "Successful retrieval", Toast.LENGTH_LONG).show();

                            }
                        }
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