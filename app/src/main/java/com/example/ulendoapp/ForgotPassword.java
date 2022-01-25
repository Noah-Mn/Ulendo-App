package com.example.ulendoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ForgotPassword extends AppCompatActivity {
    private Button confirmBtn;
    private TextInputEditText newPassword, confirmPassword, emailAddress;
    private ProgressDialog progressDialog;
    private TextInputLayout materialEmailAddress, materialNewPassword, materialConfirmPassword;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static final String TAG = "Forgot password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        newPassword = findViewById(R.id.newPassword);
        confirmBtn = findViewById(R.id.confirmBtn);
        confirmPassword = findViewById(R.id.confirmPassword);
        emailAddress = findViewById(R.id.emailAddress);
        materialEmailAddress = findViewById(R.id.materialEmailAddress);
        materialNewPassword = findViewById(R.id.materialNewPassword);
        materialConfirmPassword = findViewById(R.id.materialConfirmPassword);
        progressDialog = new ProgressDialog(ForgotPassword.this);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performReset();
            }
        });
    }
        private void performReset(){

            String email = Objects.requireNonNull(emailAddress.getText()).toString();
            String password = Objects.requireNonNull(newPassword.getText()).toString();
            String confirmPasswd = Objects.requireNonNull(confirmPassword.getText()).toString();

            if (password.equals(confirmPasswd)){

                progressDialog.setMessage("Loading please wait...");
                progressDialog.setTitle("Password Reset");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                assert user != null;
                user.updatePassword(password)
                        .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPassword.this, "Password updated ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgotPassword.this, Login.class));
//                        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task1 -> {
//                            if (task1.isSuccessful()){
//                                Toast.makeText(getApplicationContext(), "Please check your email", Toast.LENGTH_SHORT).show();
//                            }else {
//                                Toast.makeText(getApplicationContext(), "Oops!! There was an error!" + task1.getException(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }else {
                        progressDialog.dismiss();
                        Log.w(TAG, "Change password: failed", task.getException());
                        Toast.makeText(ForgotPassword.this, "Oops!! There was an error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                materialNewPassword.setError("Passwords don't match!");
                materialConfirmPassword.setError("Passwords don't match!");
            }


        }
}