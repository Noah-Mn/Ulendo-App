package com.example.ulendoapp;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView firstName, lastName, status, phoneNumber;
    public CardView onlineUserLayout;
    public UserAdapter.OnUserOnlineClickListener onUserOnlineClickListener;

    public UserViewHolder(View itemView, UserAdapter.OnUserOnlineClickListener onUserOnlineClickListener) {
        super(itemView);
    }

    @Override
    public void onClick(View view) {
        onUserOnlineClickListener.onUserOnlineClick(getAdapterPosition());
    }
}
