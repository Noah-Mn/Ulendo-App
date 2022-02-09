package com.example.ulendoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserRideAdapter extends RecyclerView.Adapter<UserRideViewHolder> implements View.OnClickListener{
    private List<UserModel> userModelList;
    private OnUserClickListener onUserClickListener;

    public UserRideAdapter(List<UserModel> userModelList, UserRideAdapter.OnUserClickListener onUserClickListener) {
        this.userModelList = userModelList;
        this.onUserClickListener = onUserClickListener;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public UserRideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View userView = inflater.inflate(R.layout.user_ride_layout, parent, false);

        UserRideViewHolder userHolder = new UserRideViewHolder(userView, onUserClickListener);
        return userHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull UserRideViewHolder holder, int position) {

    }

    public interface OnUserClickListener {
        void onUserClick(int position);
    }


    @Override
    public int getItemCount() {
        return userModelList.size();
    }
}
