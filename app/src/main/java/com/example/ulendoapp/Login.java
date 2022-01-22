package com.example.ulendoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextInputEditText loginEmail, loginPassword;
    TextInputLayout materialLogPassword, materialLogEmail;
    Button loginBtn;
    MaterialTextView materialCreateAcc, materialForgotPasswd;
    ProgressDialog progressDialog;
    final String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
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
        progressDialog = new ProgressDialog(this);
        materialLogEmail = findViewById(R.id.materialLogEmail);
        materialLogPassword = findViewById(R.id.materialLogPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        materialForgotPasswd.setOnClickListener(v -> startActivity(new Intent(Login.this,ForgotPassword.class)));
        materialCreateAcc.setOnClickListener(v -> startActivity(new Intent(Login.this,Signup.class)));

        loginBtn.setOnClickListener(v -> performLogin());
    }
    private void performLogin(){

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

            firebaseAuth.signInWithEmailAndPassword(logEmailAddress, logPassword).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Log.w(TAG, "Login:success", task.getException());
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                    startActivity(new Intent(Login.this, Home.class));
                }else {
                    progressDialog.dismiss();
                    updateUI(null);
                    Log.w(TAG, " Login:failure", task.getException());
                    Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
        private void updateUI(FirebaseUser user) {
    }
    
}