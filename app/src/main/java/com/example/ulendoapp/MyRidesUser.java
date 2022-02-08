package com.example.ulendoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MyRidesUser extends AppCompatActivity implements UserRideAdapter.OnUserClickListener {
    private RecyclerView recyclerView;
    private List<UserModel> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides_user);
        userList = new ArrayList<>();
        UserModel user = new UserModel("passenger", "lonjezo", "banthapo", "088889" );
        userList.add(user);

        recyclerView = findViewById(R.id.userRideRecyclerView);
        UserRideAdapter adapter = new UserRideAdapter(userList, MyRidesUser.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyRidesUser.this));


    }

    @Override
    public void onUserClick(int position) {

    }

}