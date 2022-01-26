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
import com.google.android.material.textview.MaterialTextView;
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

public class Login extends AppCompatActivity {
    private static final String TAG = "EmailPassword";

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private TextInputEditText loginEmail, loginPassword;
    private TextInputLayout materialLogPassword, materialLogEmail;
    private Button loginBtn;
    private MaterialTextView materialCreateAcc, materialForgotPasswd;
    private ProgressDialog progressDialog;
    private Intent intent;
    String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        materialCreateAcc = findViewById(R.id.materialCreateAcc);
        materialForgotPasswd = findViewById(R.id.materialForgotPasswd);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);

        materialLogEmail = findViewById(R.id.materialLogEmail);
        materialLogPassword = findViewById(R.id.materialLogPassword);
        progressDialog = new ProgressDialog(this);

        materialForgotPasswd.setOnClickListener(v -> startActivity(new Intent(Login.this,ForgotPassword.class)));
        materialCreateAcc.setOnClickListener(v -> startActivity(new Intent(Login.this,Signup.class)));

        loginBtn.setOnClickListener(v -> {
            performLogin();
//            Login.this.startActivity(new Intent(Login.this, Home.class));
        });
    }

    private void performLogin(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String logEmailAddress = Objects.requireNonNull(loginEmail.getText()).toString();
        String logPassword = Objects.requireNonNull(loginPassword.getText()).toString();

        if (!logEmailAddress.matches(emailPattern)){
            materialLogEmail.setError("Please enter a valid email address");
        }else if (logEmailAddress.isEmpty()){
            materialLogEmail.setError("Enter email address");
        } else if (logPassword.isEmpty()){
            materialLogPassword.setError("Please enter password");
        }else{
            progressDialog.setMessage("Logging in please wait...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(logEmailAddress, logPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this.getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Login.this.updateUI(user);
                        intent = new Intent(Login.this, Home.class);
                        intent.putExtra("email", logEmailAddress);
                        startActivity(intent);
                    } else {
                        progressDialog.dismiss();
                        Login.this.updateUI(null);
                        Log.w(TAG, " Login:failure", task.getException());
                        Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


        private void updateUI(FirebaseUser user) {
    }
    
}