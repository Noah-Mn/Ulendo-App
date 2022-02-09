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
    private RecyclerView recyclerViewCard, recyclerViewUser;
    private List<UserModel> userList;
    private CardView onlineCard;
    Context context;

    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides_user);
        onlineCard = (CardView) findViewById(R.id.myRideUserCardView);
        context = this;

        recyclerViewCard = findViewById(R.id.userRideRecyclerView);
        userList = new ArrayList<>();

        if(userRide()){
            Toast.makeText(MyRidesUser.this, "good", Toast.LENGTH_LONG).show();
//            UserAdapter adapter = new UserAdapter(userList, MyRidesUser.this);
//            recyclerViewUser.setAdapter(adapter);
//            recyclerViewUser.setLayoutManager(new LinearLayoutManager(MyRidesUser.this));
        }
    }

    public void onlineUser(){
        user = new UserModel("passenger", "lonjezo", "banthapo", "088889" );
        userList.add(user);
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