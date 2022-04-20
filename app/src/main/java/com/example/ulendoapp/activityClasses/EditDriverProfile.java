package com.example.ulendoapp.activityClasses;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ulendoapp.R;
import com.example.ulendoapp.databinding.ActivityEditDriverProfileBinding;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * The type Edit driver profile.
 */
public class EditDriverProfile extends AppCompatActivity {
    private final String TAG = "tag";
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private boolean success, valid;
    /**
     * The Password.
     */
    public String password;
    private String firstName, lastName, phoneNumber, dateOfBirth, nationalId, physicalAddress, emailAddress;
    private String encodedImage;
    private Object view;
    /**
     * The Driver password.
     */
    static String driverPassword;
    private AuthCredential credential;
    /**
     * The Preference manager.
     */
    PreferenceManager preferenceManager;
    private String fName, surname, birthday, pNumber, email, id, phyAddress;

    /**
     * The Binding.
     */
    ActivityEditDriverProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityEditDriverProfileBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        preferenceManager = new PreferenceManager(getApplicationContext());

        binding.changeAvatar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pickImage.launch(intent);
            }
        });

        binding.EDriverProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditDriverProfile.this, DriverProfile.class));
            }
        });

        binding.driverUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateUpdateForm()) {
                    updateAuthEmail();
                    updateFirstName();
                    updateSurname();
                    updateDoB();
                    updateNationalID();
                    updatePhoneNumber();
                    updatePhysicalAddress();
                    changeAvatar();
                    startActivity(new Intent(EditDriverProfile.this, DriverProfile.class));
                }
            }
        });
        getMoreUserData();
    }

    /**
     * Dialog get password boolean.
     *
     * @return the boolean
     */
    public boolean dialogGetPassword(){
        success = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.text_input_password, (ViewGroup) getView(), false);
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                password = input.getText().toString();
                if(password.equals(driverPassword) && validateEmail()){
                    success = true;
                    dialog.dismiss();
                } else {
                    Toast.makeText(EditDriverProfile.this, "Wrong password, Email change failed", Toast.LENGTH_LONG).show();

                }

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                success = false;
                dialog.cancel();

            }
        });

        builder.show();
        return success;

    }

    private String encodeImage(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    if (result.getData() != null){
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.EProfilePic.setImageBitmap(bitmap);
                            encodedImage = encodeImage(bitmap);
                            preferenceManager.putString(Constants.KEY_IMAGE, encodedImage);

                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    /**
     * Set user text info.
     */
    public void setUserTextInfo(){
        String fullName = binding.editDriverFullName.getText().toString();
        if(fullName != null){
            String[] splitName = fullName.split(" ");
            if(splitName != null){
                for(int i = 0; i < splitName.length; i++){
                    firstName = splitName[0];
                    if(splitName.length == 2){
                        lastName = splitName[1];
                    }
                }
            }
        }

        dateOfBirth = binding.editDriverDateOfBirth.getText().toString();
        phoneNumber = binding.editDriverPhoneNumber.getText().toString();
        emailAddress = binding.editDriverEmailAddress.getText().toString();
        nationalId = binding.editDriverNationalId.getText().toString();
        physicalAddress = binding.editDriverPhysicalAddress.getText().toString();
    }

    /**
     * Validate update form boolean.
     *
     * @return the boolean
     */
    public boolean validateUpdateForm(){
        setUserTextInfo();
        valid = false;
        try {
            if (firstName.length() > 20) {
                binding.materialDriverFullName.setError("Invalid first name");
            } else if(phoneNumber.length() > 12){
                binding.materialDriverPhoneNumber.setError("Invalid phone number");
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                binding.materialDriverEmailAddress.setError("Invalid email");
            }else if (encodedImage == null){
                Toast.makeText(this, "Select profile image", Toast.LENGTH_SHORT).show();
            }
            else {
                valid = true;
            }

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

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
                                binding.materialDriverEmailAddress.setError("Email address already in use");
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

    /**
     * Get more user data.
     */
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
                                fName = document.getString("First Name");
                                surname = document.getString("Surname");
                                birthday = document.getString("Date of Birth");
                                pNumber = document.getString("Phone Number");
                                email = document.getString("Email Address");
                                id = document.getString("National ID");
                                phyAddress = document.getString("Physical Address");
                                encodedImage = document.getString(Constants.KEY_IMAGE);

                                binding.editDriverFullName.setText(new StringBuilder().append(fName).append(" ").append(surname).toString(),  TextView.BufferType.EDITABLE);
                                binding.editDriverDateOfBirth.setText(birthday,  TextView.BufferType.EDITABLE);
                                binding.editDriverPhoneNumber.setText(pNumber,  TextView.BufferType.EDITABLE);
                                binding.editDriverEmailAddress.setText(email, TextView.BufferType.EDITABLE);
                                binding.editDriverNationalId.setText(id,  TextView.BufferType.EDITABLE);
                                binding.editDriverPhysicalAddress.setText(phyAddress,  TextView.BufferType.EDITABLE);

                                byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                binding.EProfilePic.setImageBitmap(bitmap);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * Update auth email.
     */
    public void updateAuthEmail(){
        if(!emailAddress.isEmpty() && !emailAddress.equals(email)) {
            if(dialogGetPassword()){
                credential = EmailAuthProvider.getCredential(getEmail(), password);

                currentUser.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "User re-authenticated.");
                                currentUser.updateEmail(emailAddress)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User email address updated.");
                                                    updateEmail();
                                                    Toast.makeText(EditDriverProfile.this, "successfully changed Auth email" + emailAddress, Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                //----------------------------------------------------------\\
                            }
                        });
            }
        }
    }
    private void updateFirstName() {
        if(!firstName.isEmpty() && !firstName.equals(fName)){
            db.collection("Users")
                    .whereEqualTo("Email Address", getEmail())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "Email already exist!");
                                String userId = document.getId();
                                db.collection("Users")
                                        .document(userId)
                                        .update("First Name", firstName);
                                Toast.makeText(EditDriverProfile.this, "successfully changed first name", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "Email does not exist ", task.getException());
                            Toast.makeText(EditDriverProfile.this, "change failed", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
//            Toast.makeText(EditDriverProfile.this, "First name did not update", Toast.LENGTH_LONG).show();
        }

    }
    private void updateSurname() {
        if(!lastName.isEmpty() && !lastName.equals(surname)){
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

                                    Toast.makeText(EditDriverProfile.this, "successfully changed surname", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.d(TAG, "Email does not exist ", task.getException());
                                Toast.makeText(EditDriverProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
//            Toast.makeText(EditDriverProfile.this, "Surname did not update", Toast.LENGTH_LONG).show();
        }

    }

    private void updateDoB() {
        if(!dateOfBirth.isEmpty() && !dateOfBirth.equals(birthday)){
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

                                    Toast.makeText(EditDriverProfile.this, "successfully changed birthday", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(EditDriverProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
//            Toast.makeText(EditDriverProfile.this, "Birthday did not update", Toast.LENGTH_LONG).show();
        }

    }
    private void updatePhoneNumber() {
        if(!phoneNumber.isEmpty() && !phoneNumber.equals(pNumber)){
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

                                    Toast.makeText(EditDriverProfile.this, "successfully changed phone number", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(EditDriverProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
//            Toast.makeText(EditDriverProfile.this, "Birthday did not update", Toast.LENGTH_LONG).show();
        }

    }
    private void updateEmail() {
        if(!emailAddress.isEmpty() && !emailAddress.equals(email)){
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
                                            .update("Email Address", emailAddress);

                                    Toast.makeText(EditDriverProfile.this, "successfully changed email", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(EditDriverProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
//            Toast.makeText(EditDriverProfile.this, "Email did not update", Toast.LENGTH_LONG).show();
        }

    }
    private void updateNationalID() {
        if(!nationalId.isEmpty() && !nationalId.equals(id)){
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
                                            .update("National ID", nationalId);

                                    Toast.makeText(EditDriverProfile.this, "successfully changed national ID", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.d(TAG, "Email does not exist ", task.getException());
                                Toast.makeText(EditDriverProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
//            Toast.makeText(EditDriverProfile.this, "Date of birth did not update", Toast.LENGTH_LONG).show();
        }

    }

    private void updatePhysicalAddress() {
        if(!physicalAddress.isEmpty() && !physicalAddress.equals(phyAddress)){
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
                                            .update("Physical Address", physicalAddress);

                                    Toast.makeText(EditDriverProfile.this, "successfully changed physical address", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(EditDriverProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
//            Toast.makeText(EditDriverProfile.this, "First name did not update", Toast.LENGTH_LONG).show();
        }

    }
    private void changeAvatar() {
        if(!encodedImage.isEmpty()){
            db.collection("Users")
                    .whereEqualTo("Email Address", getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String userId = document.getId();
                                    db.collection("Users")
                                            .document(userId)
                                            .update("Profile Pic", encodedImage);

                                }
                            } else {

                                Toast.makeText(EditDriverProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    /**
     * Get email string.
     *
     * @return the string
     */
    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

    /**
     * Gets view.
     *
     * @return the view
     */
    public Object getView() {
        return view;
    }

    /**
     * Sets view.
     *
     * @param view the view
     */
    public void setView(Object view) {
        this.view = view;
    }

}