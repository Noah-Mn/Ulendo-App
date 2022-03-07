package com.example.ulendoapp.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.adapters.UserRideAdapter;

public class UserRideViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView firstName, lastName, status, phoneNumber;
    public UserRideAdapter.OnUserClickListener onUserClickListener;

    public UserRideViewHolder(View itemView, UserRideAdapter.OnUserClickListener onUserClickListener) {
        super(itemView);

    }

    @Override
    public void onClick(View view) {
        onUserClickListener.onUserClick(getAdapterPosition());
    }


}
