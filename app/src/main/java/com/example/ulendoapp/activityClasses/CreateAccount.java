package com.example.ulendoapp.activityClasses;

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

import com.example.ulendoapp.R;
import com.example.ulendoapp.models.UserModel;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
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
    PreferenceManager preferenceManager;

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
        firebaseAuth = FirebaseAuth.getInstance();

        textEmailAddress = findViewById(R.id.email_address);
        textPassword = findViewById(R.id.password);
        textConfirmPassword = findViewById(R.id.confirm_password);

        materialPassword = findViewById(R.id.material_password);
        materialConfirmPassword = findViewById(R.id.material_confirm_password);
        materialEmail = findViewById(R.id.material_email_address);

        signupBtn = findViewById(R.id.create_account_btn);
        signupBackBtn = findViewById(R.id.create_account_back_btn);
        preferenceManager = new PreferenceManager(getApplicationContext());

        getUserintentExtras();
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFinalForm() && validateEmail()){
                    Toast.makeText(getApplicationContext(), " in the boc", Toast.LENGTH_SHORT).show();
                    performSignUp();
                    Toast.makeText(getApplicationContext(), birthday, Toast.LENGTH_LONG).show();

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
        email = textEmailAddress.getText().toString().trim();
        password = Objects.requireNonNull(textPassword.getText().toString());
        confirmPassword = Objects.requireNonNull(textConfirmPassword.getText().toString());

        try{
            if(email.isEmpty()){
                materialEmail.setError("Please enter email address");
            }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                materialEmail.setError("Invalid email address");
            } else if(password.isEmpty()){
                materialPassword.setError("Please enter password");
            } else if (password.length() <= 6) {
                materialPassword.setError("Password must be more than 6 characters");
            } else if(!password.matches(confirmPassword)){
                materialConfirmPassword.setError("Passwords do not match");
            } else {
                valid = true;
            }


        } catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, e.getMessage());
        }

        return valid;
    }

    private boolean validateEmail(){
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
                                valid = false;
                            }
                        } else {
                            Log.d(TAG, "Email address not in use", task.getException());
                            valid = true;
                        }
                    }
                });
        return valid;
    }

    private void getUserintentExtras() {
        Intent intent = getIntent();

        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        birthday = intent.getStringExtra("birthday");
        gender = intent.getStringExtra("gender");
        phoneNumber = intent.getStringExtra("phoneNumber");
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
            user.put("Profile Pic", "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");
            user.put("fcmToken", "N/A");


            db.collection("Users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String ids = documentReference.getId();
                            UserModel sender = new UserModel(ids);
                            Log.d(TAG, "Inserted successfully");
                            sender.setSenderID(ids) ;
                            preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
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

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing up please wait...");
        progressDialog.setTitle("UserSignup");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

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
