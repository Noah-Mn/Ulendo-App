package com.example.ulendoapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private static final String TAG = "EmailPassword";

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private TextInputEditText loginEmail, loginPassword;
    private TextInputLayout materialLogPassword, materialLogEmail;
    private Button loginBtn;
    private static String status;
    private MaterialTextView materialCreateAcc, materialForgotPasswd;
    private ProgressDialog progressDialog;
    private Intent intent;
    String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
    private String logEmailAddress;
    private String logPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();
        loginBtn = findViewById(R.id.loginBtn);
        materialCreateAcc = findViewById(R.id.materialCreateAcc);
        materialForgotPasswd = findViewById(R.id.materialForgotPasswd);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);

        materialLogEmail = findViewById(R.id.materialLogEmail);
        materialLogPassword = findViewById(R.id.materialLogPassword);
        progressDialog = new ProgressDialog(this);

        materialForgotPasswd.setOnClickListener(v -> startActivity(new Intent(Login.this,ForgotPassword.class)));
        materialCreateAcc.setOnClickListener(v -> startActivity(new Intent(Login.this, UserSignup.class)));

        loginBtn.setOnClickListener(v -> {
            Login.this.startActivity(new Intent(Login.this, HomeUser.class));
//            performLogin();

        });
    }

    private void performLogin(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        logEmailAddress = Objects.requireNonNull(loginEmail.getText()).toString();
        logPassword = Objects.requireNonNull(loginPassword.getText()).toString();

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
                        getStatus();

                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Login.this.updateUI(user);

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

    private void getStatus(){
        db.collection("Users")
                .whereEqualTo("Email Address", logEmailAddress)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "status collected successfully");
                                status = document.getString("Status");
                                loginState(status);
//                                Toast.makeText(Login.this, "status get success"+ status, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "failed to get status ", task.getException());
//                            Toast.makeText(Login.this, "failed to get status", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void loginState(String userStatus){

        if(userStatus.equals("customer")){
            Login.this.startActivity(intent = new Intent(Login.this, HomeUser.class));
            intent.putExtra("email", logEmailAddress);

        } else{
            Login.this.startActivity(new Intent(Login.this, HomeDriver.class));
        }
        Toast.makeText(Login.this.getApplicationContext(), "Log in successfully", Toast.LENGTH_SHORT).show();
    }

        private void updateUI(FirebaseUser user) {
    }
    
}