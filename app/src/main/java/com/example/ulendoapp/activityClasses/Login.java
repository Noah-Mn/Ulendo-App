package com.example.ulendoapp.activityClasses;


import static com.example.ulendoapp.activityClasses.EditDriverProfile.driverPassword;
import static com.example.ulendoapp.activityClasses.EditUserProfile.userPassword;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.ulendoapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Objects;

/**
 * The type Login.
 */
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
    /**
     * The Email pattern.
     */
    String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
    private String logEmailAddress;
    private String logPassword;
    private LocationRequest locationRequest;
    private boolean isPermissionGranted;
    private static final String[] PERMISSIONS = {
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_STATE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestPermissions();

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

        checkService();
        loginBtn.setOnClickListener(v -> {
//            Login.this.startActivity(new Intent(Login.this, HomeDriver.class));
                performLogin();

        });
    }

    private void checkService() {
        if(checkGooglePlayServices()){
            if(isPermissionGranted){
                checkGps();
            }
        }else{
            Toast.makeText(Login.this, "Google PlayService not available", Toast.LENGTH_LONG).show();
        }
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }

    private boolean checkGooglePlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, 201, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Toast.makeText(Login.this, "User Cancelled dialog", Toast.LENGTH_LONG).show();
                }
            });
            dialog.show();

        }

        return false;
    }

    /**
     * Request permissions.
     */
    public void requestPermissions(){
        Dexter.withActivity(this)
                .withPermissions(PERMISSIONS)
                .withListener(new MultiplePermissionsListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                           isPermissionGranted = true;

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            isPermissionGranted = false;
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), "");
                            intent.setData(uri);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_LONG).show();
                    }

                }).check();

    }

    private void checkGps() {
        locationRequest = com.google.android.gms.location.LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);

        Task<LocationSettingsResponse> locationSettingsResponseTask = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        locationSettingsResponseTask.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);

                } catch (ApiException e) {
                    if (e.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                        try {
                            resolvableApiException.startResolutionForResult(Login.this, 101);
                        } catch (IntentSender.SendIntentException sendIntentException) {
                            sendIntentException.printStackTrace();
                        }
                    }
                    if (e.getStatusCode() == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE) {
                        Toast.makeText(Login.this, "Settings not available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void performLogin(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        logEmailAddress = Objects.requireNonNull(loginEmail.getText()).toString();
        logPassword = Objects.requireNonNull(loginPassword.getText()).toString();
        userPassword = logPassword;
        driverPassword = logPassword;

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(logEmailAddress).matches()){
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

    /**
     * Login state.
     *
     * @param userStatus the user status
     */
    public void loginState(String userStatus){

        if(userStatus.equals("driver")){
            Login.this.startActivity(intent = new Intent(Login.this, HomeDriver.class));
            intent.putExtra("email", logEmailAddress);
            Toast.makeText(Login.this.getApplicationContext(), "Log in successfully", Toast.LENGTH_SHORT).show();

        }else if(userStatus.equals("customer")) {
            Login.this.startActivity(intent = new Intent(Login.this, HomeUser.class));
            intent.putExtra("email", logEmailAddress);
            Toast.makeText(Login.this.getApplicationContext(), "Log in successfully", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Please make sure you have an account", Toast.LENGTH_SHORT).show();
        }

    }

        private void updateUI(FirebaseUser user) {
    }
    
}