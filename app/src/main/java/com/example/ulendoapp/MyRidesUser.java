package com.example.ulendoapp;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyRidesUser extends AppCompatActivity implements UserRideAdapter.OnUserClickListener, UserAdapter.OnUserOnlineClickListener {
    private RecyclerView recyclerViewCard;
    private List<UserModel> userList;
    private  CardView cardView;

    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides_user);

        cardView = findViewById(R.id.myRideUserCardView);
        recyclerViewCard = findViewById(R.id.userRideRecyclerView);
        userList = new ArrayList<>();
        userRide();

//        if(userRide()){
//            Toast.makeText(MyRidesUser.this, "good", Toast.LENGTH_LONG).show();
//            onlineUser();
//        }
    }
    public void onlineUser(){
        RecyclerView recyclerViewUser;
        recyclerViewUser = cardView.findViewById(R.id.userOnlineRideRecyclerView);
        List<UserModel> userList = new ArrayList<>();
        UserModel user = new UserModel("passenger", "lonjezo", "banthapo", "088889" );
        userList.add(user);

        UserAdapter adapter = new UserAdapter(userList, this);

        recyclerViewUser.setAdapter(adapter);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(MyRidesUser.this));
    }



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