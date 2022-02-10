package com.example.ulendoapp;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyRidesUser extends AppCompatActivity implements UserRideAdapter.OnUserClickListener, UserAdapter.OnUserOnlineClickListener {
    private RecyclerView recyclerViewCard;
    private List<UserModel> userList;
    private  CardView cardView;
    private ImageView backBtn;

    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides_user);

        cardView = findViewById(R.id.myRideUserCardView);
        recyclerViewCard = findViewById(R.id.userRideRecyclerView);
        backBtn = findViewById(R.id.ride_profile_back);
        userList = new ArrayList<>();
        userRide();

        findViewById(R.id.ride_profile_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRidesUser.this.startActivity(new Intent(MyRidesUser.this, HomeUser.class));
            }
        });

    }
//    public void onlineUser(){
//        RecyclerView recyclerViewUser;
//        recyclerViewUser = cardView.findViewById(R.id.userOnlineRideRecyclerView);
//        List<UserModel> userList = new ArrayList<>();
//        UserModel user = new UserModel("passenger", "lonjezo", "banthapo", "088889" );
//        userList.add(user);
//
//        UserAdapter adapter = new UserAdapter(userList, this);
//
//        recyclerViewUser.setAdapter(adapter);
//        recyclerViewUser.setLayoutManager(new LinearLayoutManager(MyRidesUser.this));
//    }



    public boolean userRide(){
        user = new UserModel("passenger", "lonjezo", "banthapo", "088889" );
        userList.add(user);

        UserRideAdapter adapter = new UserRideAdapter(userList, MyRidesUser.this);
        recyclerViewCard.setAdapter(adapter);
        recyclerViewCard.setLayoutManager(new LinearLayoutManager(MyRidesUser.this));

        return true;
    }

    @Override
    public void onUserClick(int position) {

    }

    @Override
    public void onUserOnlineClick(int adapterPosition) {

    }
}