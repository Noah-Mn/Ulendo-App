package com.example.ulendoapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EditUserProfile extends AppCompatActivity {
    private final String TAG = "Edit User Profile";
    private TextInputEditText edit_full_name, edit_email_address, edit_phone_number, edit_date_of_birth, edit_national_id, edit_physical_address;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private String firstName, lastName, phoneNumber, email, dateOfBirth, nationalID, physicalAddress;
    private ImageView E_profile_back;
    private Button updateBtn;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        E_profile_back = findViewById(R.id.E_profile_back);
        edit_full_name = findViewById(R.id.edit_full_name);
        edit_phone_number = findViewById(R.id.edit_phone_number);
        edit_email_address = findViewById(R.id.edit_email_address);
        edit_physical_address = findViewById(R.id.edit_physical_address);
        edit_date_of_birth = findViewById(R.id.edit_date_of_birth);
        edit_national_id = findViewById(R.id.edit_national_ID);
        updateBtn = findViewById(R.id.user_update_btn);

        currentUser = auth.getCurrentUser();

        E_profile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditUserProfile.this, UserProfile.class));
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUserTextInfo();

                if(!email.isEmpty()){
                    if(updateAuthEmail()){
                        updateEmail();
                    } else{
                        Toast.makeText(EditUserProfile.this, "email did not update", Toast.LENGTH_LONG).show();
                    }
                }
                updateFirstName();
                updateSurname();
                updateDoB();
                updateNationalID();
                updatePhysicalAddress();
            }
        });


        getMoreUserData();
    }

    public void setUserTextInfo(){
        String fullName = edit_full_name.getText().toString();
        String[] splitName = fullName.split(" ");

        if(splitName != null){
            for(int i = 0; i < splitName.length; i++){
                firstName = splitName[0];
                lastName = splitName[1];
            }
        }

        dateOfBirth = edit_date_of_birth.getText().toString();
        phoneNumber = edit_phone_number.getText().toString();
        email = edit_email_address.getText().toString();
        nationalID = edit_national_id.getText().toString();
        physicalAddress = edit_physical_address.getText().toString();
    }

    public void getMoreUserData(){
        db.collection("Users")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                firstName = document.getString("First Name");
                                lastName = document.getString("Surname");
                                phoneNumber = document.getString("Phone Number");
                                edit_full_name.setText(new StringBuilder().append(firstName).append(" ").append(lastName).toString());
                                edit_phone_number.setText(phoneNumber);
                                email = document.getString("Email Address");
                                edit_email_address.setText(email, TextView.BufferType.EDITABLE);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void updateFirstName() {
        if(!firstName.isEmpty()){
            db.collection("Users")
                    .whereEqualTo("Email Address", getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, "Email already exist!");
                                    String userId = document.getId();
                                    db.collection("Users")
                                            .document(userId)
                                            .update("First Name", firstName);

                                    Toast.makeText(EditUserProfile.this, "successfully changed first name", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.d(TAG, "Email does not exist ", task.getException());
                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(EditUserProfile.this, "First name did not update", Toast.LENGTH_LONG).show();
        }

    }

    private void updateSurname() {
        if(!lastName.isEmpty()){
            db.collection("Users")
                    .whereEqualTo("Email Address", getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, "Email already exist!");
                                    String userId = document.getId();
                                    db.collection("Users")
                                            .document(userId)
                                            .update("Surname", lastName);

                                    Toast.makeText(EditUserProfile.this, "successfully changed surname", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.d(TAG, "Email does not exist ", task.getException());
                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(EditUserProfile.this, "Surname did not update", Toast.LENGTH_LONG).show();
        }

    }

    private void updateDoB() {
        if(!email.isEmpty()){
            db.collection("Users")
                    .whereEqualTo("Email Address", getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, "Email already exist!");
                                    String userId = document.getId();
                                    db.collection("Users")
                                            .document(userId)
                                            .update("Email Address", email);

                                    Toast.makeText(EditUserProfile.this, "successfully changed email address", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.d(TAG, "Email does not exist ", task.getException());
                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(EditUserProfile.this, "Email did not update", Toast.LENGTH_LONG).show();
        }

    }

    private void updateEmail() {
        if(!phoneNumber.isEmpty()){
            db.collection("Users")
                    .whereEqualTo("Email Address", getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, "Email already exist!");
                                    String userId = document.getId();
                                    db.collection("Users")
                                            .document(userId)
                                            .update("Phone Number", phoneNumber);

                                    Toast.makeText(EditUserProfile.this, "successfully changed phone number", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(EditUserProfile.this, "Phone number did not update", Toast.LENGTH_LONG).show();
        }

    }

    private void updateNationalID() {
        if(!dateOfBirth.isEmpty()){
            db.collection("Users")
                    .whereEqualTo("Email Address", getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, "Email already exist!");
                                    String userId = document.getId();
                                    db.collection("Users")
                                            .document(userId)
                                            .update("Date of Birth", dateOfBirth);

                                    Toast.makeText(EditUserProfile.this, "successfully changed date of birth", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.d(TAG, "Email does not exist ", task.getException());
                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(EditUserProfile.this, "Date of birth did not update", Toast.LENGTH_LONG).show();
        }

    }

    private void updatePhysicalAddress() {
        if(!physicalAddress.isEmpty()){
            db.collection("Users")
                    .whereEqualTo("Email Address", getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, "Email already exist!");
                                    String userId = document.getId();
                                    db.collection("Users")
                                            .document(userId)
                                            .update("Physical Address", "driver");

                                    Toast.makeText(EditUserProfile.this, "successfully changed physical address", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(EditUserProfile.this, "First name did not update", Toast.LENGTH_LONG).show();
        }

    }

    public boolean updateAuthEmail(){
        success = false;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential("user@example.com", "password1234"); // Current Login Credentials \\

        if(!email.isEmpty()){
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "User re-authenticated.");
                            //Now change your email address \\
                            //----------------Code for Changing Email Address----------\\
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updateEmail(email)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User email address updated.");
                                            }
                                        }
                                    });
                            //----------------------------------------------------------\\
                        }
                    });
            success = true;
        } else {
            success = false;
        }
       return success;
    }



    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }
}