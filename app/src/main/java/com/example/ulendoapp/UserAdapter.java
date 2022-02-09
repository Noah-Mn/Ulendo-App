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

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> implements View.OnClickListener{
    private List<UserModel> onlineUserModelList;
    private OnUserOnlineClickListener onUserOnlineClickListener;
    RecyclerView rc;

    public UserAdapter(List<UserModel> onlineUserModelList, UserAdapter.OnUserOnlineClickListener onUserOnlineClickListener) {
        this.onlineUserModelList = onlineUserModelList;
        this.onUserOnlineClickListener = onUserOnlineClickListener;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View onlineUserView = inflater.inflate(R.layout.online_user_layout, parent, false);

        UserViewHolder onlineUserHolder = new UserViewHolder(onlineUserView, onUserOnlineClickListener);
        return onlineUserHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return onlineUserModelList.size();
    }

    public interface OnUserOnlineClickListener {
        void onUserOnlineClick(int adapterPosition);
    }
}
