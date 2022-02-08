package com.example.ulendoapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class UserRideViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView firstName, lastName, status, phoneNumber;
    public CardView UserLayout;
    public UserRideAdapter.OnUserClickListener onUserClickListener;

    public UserRideViewHolder(View itemView, UserRideAdapter.OnUserClickListener onUserClickListener) {
        super(itemView);
    }

    @Override
    public void onClick(View view) {
        onUserClickListener.onUserClick(getAdapterPosition());
    }


}
