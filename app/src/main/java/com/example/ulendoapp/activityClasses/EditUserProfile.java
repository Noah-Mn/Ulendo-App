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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ulendoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * The type Edit user profile.
 */
public class EditUserProfile extends AppCompatActivity {
    private final String TAG = "tag";
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private TextInputEditText edit_full_name, edit_email_address, edit_phone_number, edit_date_of_birth, edit_national_id, edit_physical_address;
    private String firstName, lastName, phoneNumber, dateOfBirth, nationalId, physicalAddress, emailAddress;
    private String fName, surname, birthday, pNumber, email, id, phyAddress, encodedImage;
    private ImageView E_profile_back;
    private Button updateBtn;
    private boolean success;
    /**
     * The Password.
     */
    public String password;
    private Object view;
    private AuthCredential credential;
    /**
     * The E profile pic.
     */
    RoundedImageView EProfilePic;
    /**
     * The Change avatar.
     */
    MaterialTextView changeAvatar;
    /**
     * The User password.
     */
    static String userPassword;
    private TextInputLayout materialFullName, materialBirthday, materialEmailAddress, materialPhoneNumber, materialNationalId, materialPhysicalAddress;
    private boolean valid;
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
        edit_national_id = findViewById(R.id.edit_national_id);
        EProfilePic = findViewById(R.id.E_profile_pic);
        materialFullName = findViewById(R.id.material_full_name);
        materialBirthday = findViewById(R.id.material_date_of_birth);
        materialPhoneNumber = findViewById(R.id.material_phone_number);
        materialEmailAddress = findViewById(R.id.material_email_address);
        materialNationalId = findViewById(R.id.material_national_id);
        materialPhysicalAddress = findViewById(R.id.material_physical_address);
        changeAvatar = findViewById(R.id.change_avatar);

        updateBtn = findViewById(R.id.user_update_btn);

        currentUser = auth.getCurrentUser();

        E_profile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditUserProfile.this, UserProfile.class));
            }
        });
        changeAvatar.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateUpdateForm()) {
                    updateAuthEmail();
                    updateFirstName();
                    updateSurname();
                    updatePhoneNumber();
                    updateDoB();
                    updateNationalID();
                    updatePhysicalAddress();
                    changeAvatar();
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
//        builder.setTitle("Enter password");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.text_input_password, (ViewGroup) getView(), false);
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                password = input.getText().toString();
                if(password.equals(userPassword) && validateEmail()){
                    success = true;
                    dialog.dismiss();
                } else {
                    Toast.makeText(EditUserProfile.this, "Wrong password, Email change failed", Toast.LENGTH_LONG).show();

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

    /**
     * Set user text info.
     */
    public void setUserTextInfo(){
        String fullName = edit_full_name.getText().toString();
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

        dateOfBirth = edit_date_of_birth.getText().toString();
        phoneNumber = edit_phone_number.getText().toString();
        emailAddress = edit_email_address.getText().toString();
        nationalId = edit_national_id.getText().toString();
        physicalAddress = edit_physical_address.getText().toString();
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
                                encodedImage = document.getString("Profile Pic");
                                byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                EProfilePic.setImageBitmap(bitmap);

                                edit_full_name.setText(new StringBuilder().append(fName).append(" ").append(surname).toString(),  TextView.BufferType.EDITABLE);
                                edit_date_of_birth.setText(birthday,  TextView.BufferType.EDITABLE);
                                edit_phone_number.setText(pNumber,  TextView.BufferType.EDITABLE);
                                edit_email_address.setText(email, TextView.BufferType.EDITABLE);
                                edit_national_id.setText(id,  TextView.BufferType.EDITABLE);
                                edit_physical_address.setText(phyAddress,  TextView.BufferType.EDITABLE);
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
                                                    Toast.makeText(EditUserProfile.this, "successfully changed Auth email" + emailAddress, Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                //----------------------------------------------------------\\
                            }
                        });
            }
        }
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
                                materialEmailAddress.setError("Email address already in use");
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
     * Validate update form boolean.
     *
     * @return the boolean
     */
    public boolean validateUpdateForm(){
        setUserTextInfo();
        valid = false;
        try {
            if (firstName.length() > 20) {
                materialFullName.setError("Invalid first name");
            } else if(phoneNumber.length() > 12){
                materialPhoneNumber.setError("Invalid phone number");
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                materialEmailAddress.setError("Invalid email");
            } else {
                valid = true;
            }

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return valid;
    }

    private void updateFirstName() {
        if(!firstName.isEmpty() && !firstName.equals(fName)){
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

                                    Toast.makeText(EditUserProfile.this, "successfully changed surname", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.d(TAG, "Email does not exist ", task.getException());
                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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

                                    Toast.makeText(EditUserProfile.this, "successfully phone number", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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

                                    Toast.makeText(EditUserProfile.this, "successfully changed birthday", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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

                                    Toast.makeText(EditUserProfile.this, "successfully changed email", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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

                                    Toast.makeText(EditUserProfile.this, "successfully changed national ID", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.d(TAG, "Email does not exist ", task.getException());
                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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

                                    Toast.makeText(EditUserProfile.this, "successfully changed physical address", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
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
                            EProfilePic.setImageBitmap(bitmap);
                            encodedImage = encodeImage(bitmap);

                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
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

                                Toast.makeText(EditUserProfile.this, "change failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

}