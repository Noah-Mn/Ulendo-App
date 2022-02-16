package com.example.ulendoapp;

import static com.example.ulendoapp.fragment_offer_rides.latitude;
import static com.example.ulendoapp.fragment_offer_rides.longitude;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.LocationListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

public class HomeDriver extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener{
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    BottomNavigationView driver_bottom_nav;
    NavigationView navigation_view_driver;
    DrawerLayout drawerLayout;
    private MaterialTextView  header_name, header_email, firstName;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    String fName, lastName, email;
    private final String TAG = "Home Driver";
    private Toolbar toolbar;

    private static final String[] PERMISSIONS = {
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE" ,
            "android.permission.READ_PHONE_STATE"};
    private SupportMapFragment mapFragment;
    private boolean isPermissionGranted;
    private GoogleApiClient gClient;
    private LocationRequest locationRequest;
    private GoogleMap gMap;
    private Location location;
    private CameraUpdate cameraUpdate;
    private LocationCallback locationCallback;
    private double currentLatitude;
    private double currentLongitude;
    private int hrOfDay;
    private int minutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_driver);

        toolbar = findViewById(R.id.toolbarDriver);
        driver_bottom_nav = findViewById(R.id.driver_bottom_nav);
        driver_bottom_nav.inflateMenu(R.menu.bottom_navigation_menu);
        driver_bottom_nav.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        navigation_view_driver = findViewById(R.id.navigation_view_driver);
        drawerLayout = findViewById(R.id.driver_drawer_Layout);
        firstName = findViewById(R.id.firstName);
        header_name = findViewById(R.id.header_name);
        header_email = findViewById(R.id.header_email);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        findViewById(R.id.imageMenu).setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        setMenu();
        navInit();
        getUserData();
        getUserName();
        checkService();
    }


    private void checkService() {
        if(checkGooglePlayServices()){
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.driver_map);
            mapFragment.getMapAsync(this);

            if(isPermissionGranted){
                checkGps();
            }

        }else{
            Toast.makeText(HomeDriver.this, "Google PlayService not available", Toast.LENGTH_LONG).show();
        }
        gClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(HomeDriver.this)
                .addOnConnectionFailedListener(HomeDriver.this)
                .addApi(LocationServices.API)
                .build();

        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Dexter.withActivity(this)
                .withPermissions(PERMISSIONS)
                .withListener(new MultiplePermissionsListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
//                            getCurrentUpdate(gMap);
                            isPermissionGranted = true;
                            gMap.setMyLocationEnabled(true);
                            gMap.getUiSettings().setMyLocationButtonEnabled(true);
                            gMap.getUiSettings().setZoomControlsEnabled(true);
                            location = LocationServices.FusedLocationApi.getLastLocation(gClient);
                            if (location == null) {
                                LocationServices.FusedLocationApi.requestLocationUpdates(gClient, locationRequest, HomeDriver.this);
                            }
                            else {
                                handleNewLocation(location);
                            }

                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
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
                        Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }

                }).check();

    }

    private void handleNewLocation(Location location) {
        gMap.clear();
        Log.d(TAG, location.toString());

        currentLatitude = this.location.getLatitude();
        currentLongitude = this.location.getLongitude();

        LocationServices.FusedLocationApi.removeLocationUpdates(gClient, HomeDriver.this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        handleNewLocation(location);
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        latitude = currentLatitude;
        longitude = currentLongitude;

        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        gMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                gMap.animateCamera(cameraUpdate);

                return true;
            }
        });

    }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.hasResolution()) {
                try {
                    // Start an Activity that tries to resolve the error
                    connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            } else {
                Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
            }
        }

        @Override
        protected void onResume() {
            super.onResume();
            gClient.connect();

        }

        @Override
        protected void onPause() {
            super.onPause();
            if (gClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(gClient, HomeDriver.this);
                gClient.disconnect();
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            gMap = googleMap;
            gMap.clear();

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
                        Toast.makeText(HomeDriver.this, "User Cancelled dialog", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();

            }

            return false;
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
                            resolvableApiException.startResolutionForResult(HomeDriver.this, 101);
                        } catch (IntentSender.SendIntentException sendIntentException) {
                            sendIntentException.printStackTrace();
                        }
                    }
                    if (e.getStatusCode() == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE) {
                        Toast.makeText(HomeDriver.this, "Settings not available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_driver, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void setMenu(){
        toolbar.inflateMenu(R.menu.menu_driver);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.help_driver){
                    Toast.makeText(getApplicationContext(), "Help clicked", Toast.LENGTH_SHORT).show();

                }else if (item.getItemId() == R.id.log_out_driver){
                    FirebaseAuth.getInstance().signOut();
                    HomeDriver.this.startActivity(new Intent(HomeDriver.this, Login.class));
                    Toast.makeText(getApplicationContext(), "Succesfully logged out", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

        });
    }
    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.home:

                            replaceFragments(new fragment_driver_home());
                            break;

                        case R.id.notifications:
                           
                            replaceFragments(new fragment_driver_notifications());
                            break;

                        case R.id.profile:
                            startActivity(new Intent(HomeDriver.this, DriverProfile.class));
                            //replaceFragments(new fragment_driver_profile());
                            break;
                    }
                    return true;
                }
            };
    private void replaceFragments(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void navInit() {
        header_name = navigation_view_driver.getHeaderView(0).findViewById(R.id.header_name);
        header_email = navigation_view_driver.getHeaderView(0).findViewById(R.id.header_email);

        navigation_view_driver.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);

                switch (item.getItemId()) {
                    case R.id.driver_chats:

                        replaceFragments(new drivers_chats());
                        break;

                    case R.id.driver_rides:

                        replaceFragments(new driver_my_rides());
                        break;

                    case R.id.driver_payments:

                        replaceFragments(new driver_my_payments());
                        break;
                    case R.id.my_vehicles:

                        replaceFragments(new driver_my_vehicles());
                        break;
                    case R.id.driver_groups:

                        replaceFragments(new driver_groups());
                        break;

                    case R.id.dashboard:

                        replaceFragments(new driver_dashboard());
                        break;

                    case R.id.driver_wallet:

                        replaceFragments(new driver_wallet());
                        break;

                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    public void getUserData(){
        db.collection("Drivers")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                email = document.getString("Email Address");
                                header_name.setText(new StringBuilder().append(fName).append(" ").append(lastName).toString());
                                header_email.setText(email);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void getUserName(){
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
                                lastName = document.getString("Surname");
                                firstName.setText(fName);
                                header_name.setText(new StringBuilder().append(fName).append(" ").append(lastName).toString());

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
            FirebaseAuth.getInstance().signOut();
            HomeDriver.this.startActivity(new Intent(HomeDriver.this, Login.class));
        }
    }

}

