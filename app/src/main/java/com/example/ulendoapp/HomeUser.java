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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.LocationListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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


public class HomeUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CustomAdapter.OnDriverClickListener,
        OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener{

    private MaterialTextView name, header_name, header_email;
    private String firstName, lastName, email;
    private String f_name, surname, birthday, gender, phoneNumber, emailAddress, nationalId, physicalAddress, status, numberOfTrips, rating;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private MaterialToolbar toolbar;
    ProgressDialog progressDialog;
    private String TAG;
    public static String newText;
    private NavigationView navigationView;
    static UserModel userModel;

    private RecyclerView recyclerView;
    private List<UserModel> userModelList;
    private CustomAdapter adapter;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottom_navigation;
    private String fullName, fName, lName;
    private SearchView searchView;
    private MenuItem menuItem;

    private SupportMapFragment mapFragment;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest locationRequest;
    private boolean isPermissionGranted;
    public FusedLocationProviderClient client;
    public GoogleApiClient gClient;
    public CameraUpdate cameraUpdate;
    public GoogleMap gMap;
    public Location location;
    private static final String[] PERMISSIONS = {
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE" ,
            "android.permission.READ_PHONE_STATE"};

    private FrameLayout layout;
    public LatLng latLng;
    public double currentLatitude;
    public double currentLongitude;
    public int count = 0;
    public int count1 = 0;
    private Fragment fr;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        toolbar = findViewById(R.id.toolbarUser);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();

        progressDialog = new ProgressDialog(this);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        drawerLayout = findViewById(R.id.drawer_layout);
        layout = findViewById(R.id.fragment_container_user);
        userModelList = new ArrayList<>();
        name = findViewById(R.id.firstName);

        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getUserData();
        navInit();
        setMenu();
        checkService();
        getUserModel();
        bottom_navigation.setSelectedItemId(R.id.home);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void checkService() {
        if(checkGooglePlayServices()){
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            if(isPermissionGranted){
                checkGps();
            }

        }else{
            Toast.makeText(HomeUser.this, "Google PlayService not available", Toast.LENGTH_LONG).show();
        }
        gClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(HomeUser.this)
                .addOnConnectionFailedListener(HomeUser.this)
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
                            location = LocationServices.FusedLocationApi.getLastLocation(gClient);

                            if (location == null) {
                                LocationServices.FusedLocationApi.requestLocationUpdates(gClient, locationRequest, HomeUser.this);
                            }
                            else {
                                handleNewLocation(location);
                                Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            }
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
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gMap.animateCamera(cameraUpdate);
        LocationServices.FusedLocationApi.removeLocationUpdates(gClient, HomeUser.this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        handleNewLocation(location);
        latLng = new LatLng(currentLatitude, currentLongitude);
        latitude = currentLatitude;
        longitude = currentLongitude;

        gMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
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
            LocationServices.FusedLocationApi.removeLocationUpdates(gClient, HomeUser.this);
            gClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.clear();
        gMap.getUiSettings().setZoomControlsEnabled(true);
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
                    Toast.makeText(HomeUser.this, "User Cancelled dialog", Toast.LENGTH_LONG).show();
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
                            resolvableApiException.startResolutionForResult(HomeUser.this, 101);
                        } catch (IntentSender.SendIntentException sendIntentException) {
                            sendIntentException.printStackTrace();
                        }
                    }
                    if (e.getStatusCode() == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE) {
                        Toast.makeText(HomeUser.this, "Settings not available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void navInit() {
        header_name = navigationView.getHeaderView(0).findViewById(R.id.header_name);
        header_email = navigationView.getHeaderView(0).findViewById(R.id.header_email);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);

                switch (item.getItemId()) {
                    case R.id.myRidesItem:
                        replaceFragments(new UserMyRides());
                        break;
                    case R.id.myFavoritesItem:
                        replaceFragments(new My_Favorites());
                        break;
                    case R.id.myPaymentsItem:
                        replaceFragments(new My_Payments());
                        break;
                    case R.id.driver:
                        HomeUser.this.startActivity(new Intent(HomeUser.this, DriverSignup.class));
                        break;

                    case R.id.my_wallet:
                        replaceFragments(new wallet());
                        break;

                    case R.id.my_chats:
                        HomeUser.this.startActivity(new Intent(HomeUser.this, UserChat.class));
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    public void setMenu(){
        toolbar.inflateMenu(R.menu.menu_user);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.searchItem) {
                    searchDriver();
                    replaceFragments(new fragment_recyclerview());

                }else if (item.getItemId()==R.id.help){
                    Toast.makeText(getApplicationContext(), "Help clicked", Toast.LENGTH_SHORT).show();

                }else if (item.getItemId() == R.id.log_out){
                    FirebaseAuth.getInstance().signOut();
                    HomeUser.this.startActivity(new Intent(HomeUser.this, Login.class));
                    Toast.makeText(getApplicationContext(), "log out clicked", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

        });
    }

    public void searchDriverData() {
        db.collection("Users")
                .whereEqualTo("Status", "driver")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        progressDialog.dismiss();
                        userModelList.clear();
                        for (DocumentSnapshot documentSnapshot: task.getResult()){
                            UserModel userModel = new UserModel(documentSnapshot.getString("Status"),
                                    documentSnapshot.getString("First Name"),
                                    documentSnapshot.getString("Surname"),
                                    documentSnapshot.getString("Phone Number"));
                            userModelList.add(userModel);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeUser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.home:
                            fr = new fragment_home();
                            if(count % 2 == 0 && count1 % 2 == 0){
                                replaceFragments(fr);
                                fragmentTransaction.hide(new fragment_notifications());
                                count++;
                                break;

                            } else if(count % 2 == 0 && count1 % 2 != 0){
                                replaceFragments(fr);
                                fragmentTransaction.hide(new fragment_notifications());
                                count1++;
                                count++;
                                break;
                            } else{
                                layout.setVisibility(View.GONE);
                                Toast.makeText(HomeUser.this, String.valueOf(count), Toast.LENGTH_LONG).show();
                                count++;
                                break;
                            }
                        case R.id.notifications:
                            fr = new fragment_notifications();
                            if(count1 % 2 == 0 && count % 2 == 0){
                                replaceFragments(fr);
                                fragmentTransaction.hide(new fragment_driver_home());
                                count1++;
                                break;

                            } else if(count1 % 2 == 0 && count % 2 != 0){
                                replaceFragments(fr);
                                fragmentTransaction.hide(new fragment_driver_home());
                                count1++;
                                count++;
                                break;
                            } else{
                                layout.setVisibility(View.GONE);
                                Toast.makeText(HomeUser.this, String.valueOf(count1), Toast.LENGTH_LONG).show();
                                count1++;
                                break;
                            }
                        case R.id.profile:
                            startActivity(new Intent(HomeUser.this, UserProfile.class));
                            break;
                    }
                    return true;

                }
            };

    public void searchDriver(){
        menuItem = toolbar.getMenu().findItem(R.id.searchItem);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        menuItem.expandActionView();

        searchDriverData();
        List<UserModel> searchList = new ArrayList<>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                Log.i("well", " this worked");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                newText = query;
                searchList.clear();
                searchView.setQueryHint("Search driver");
                Log.i("well", " this worked");
                if (!query.isEmpty()) {
                    for(int i = 0; i < userModelList.size(); i++){
                        fName = userModelList.get(i).getFirstName();
                        lName = userModelList.get(i).getLastName();
                        String userStatus = userModelList.get(i).getStatus();
                        String phoneNumber = userModelList.get(i).getPhoneNumber();
                        fullName = fName + " " + lName;

                        if (fName.toLowerCase().contains(query.toLowerCase()) || lName.toLowerCase().contains(query.toLowerCase())){
                            UserModel model = new UserModel(userStatus, fName, lName, phoneNumber);
                            searchList.add(model);
                            Toast.makeText(HomeUser.this, "size of search list is " + searchList.size(), Toast.LENGTH_SHORT).show();

                        } else if (fullName.toLowerCase().contains(query.toLowerCase())){
                            UserModel model = new UserModel(userStatus, fName, lName, phoneNumber);
                            searchList.add(model);
                        }

                    }

                } else if(query.isEmpty()){
                    searchList.clear();
                }



                recyclerView = findViewById(R.id.searchRecyclerView);
                adapter = new CustomAdapter(searchList, HomeUser.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeUser.this));

                return true;
                }

        });

    }

    public void getUserModel(){
        db.collection("Users")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                f_name = document.getString("First Name");
                                surname = document.getString("Surname");
                                birthday = document.getString("Date of Birth");
                                gender = document.getString("Gender");
                                phoneNumber = document.getString("Phone Number");
                                emailAddress = document.getString("Email Address");
                                nationalId = document.getString("National ID");
                                physicalAddress = document.getString("Physical Address");
                                status = document.getString("Status");
                                numberOfTrips = document.getString("Number of Trips");
                                rating = document.getString("Rating");

                                userModel = new UserModel(f_name, surname, birthday, gender, phoneNumber, email,
                                        nationalId, physicalAddress, status, numberOfTrips, rating);
                                }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getUserData(){
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
                                email = document.getString("Email Address");
                                name.setText(firstName);
                                header_name.setText(new StringBuilder().append(firstName).append(" ").append(lastName).toString());
                                header_email.setText(email);

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
            HomeUser.this.startActivity(new Intent(HomeUser.this, Login.class));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    private void replaceFragments(Fragment fragment){
        layout.setVisibility(View.VISIBLE);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_user, fragment);
        fragmentTransaction.commit();
        }


    @Override
    public void onDriverClick(int position) {
        Toast.makeText(HomeUser.this, "you clicked position " + position, Toast.LENGTH_SHORT).show();
    }
}
