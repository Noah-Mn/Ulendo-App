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

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private String firstName, lastName, phoneNumber, gender;
    private String email, password, confirmPassword;
    private Button confirmBtn;
    private TextInputEditText textEmail, textPassword, textConfirmPassword;
    private TextInputLayout materialEmail, materialPassword, materialConfirmPassword;    
    private FirebaseFirestore db;
    boolean success;
    private String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Intent intent = getIntent();
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        phoneNumber = intent.getStringExtra("phoneNumber");
        gender = intent.getStringExtra("gender");

        confirmBtn = findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSignUp();

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

    private void addUser(){
        db = FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Map<String, Object> user = new HashMap<>();

        user.put("First Name", firstName);
        user.put("Surname", lastName);
        user.put("Phone Number", phoneNumber);
        user.put("Gender", gender);
        user.put("Email Address", email);
        user.put("Status", "customer");

        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "inserted successfully");
//                        Toast.makeText(CreateAccount.this, "Successfully Signed up", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "error! failed");
//                        Toast.makeText(CreateAccount.this, "error!! failed to signup", Toast.LENGTH_LONG).show();
                    }
                });

        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Toast.makeText(CreateAccount.this, "Successful retrieval", Toast.LENGTH_LONG).show();

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
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        ProgressDialog progressDialog = new ProgressDialog(this);

        if(validateFinalForm()) {
            progressDialog.setMessage("Logging in please wait...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                            Log.d(TAG, "signup:success", task.getException());
                        progressDialog.dismiss();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        updateUI(user);
                        Toast.makeText(getApplicationContext(), "Sign up successfullty", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreateAccount.this, Login.class));
                        addUser();
                    } else {
                            Log.w(TAG, " Signup:failure", task.getException());
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Sign up failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        }
    }

    private void updateUI(FirebaseUser user) {
    }
}