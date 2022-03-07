package com.example.ulendoapp.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.adapters.CustomAdapter;
import com.example.ulendoapp.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView firstName, lastName, status, phoneNumber;
    public CardView driverLayout;
    public CustomAdapter.OnDriverClickListener onDriverClickListener;

    public ViewHolder(View driverView, CustomAdapter.OnDriverClickListener onDriverClickListener) {
        super(driverView);

        firstName = itemView.findViewById(R.id.first_name);
        lastName = itemView.findViewById(R.id.last_name);
        status = itemView.findViewById(R.id.status);
        phoneNumber = itemView.findViewById(R.id.phone_number);

        driverLayout = itemView.findViewById(R.id.driverSearchLayout);
        this.onDriverClickListener = onDriverClickListener;
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        onDriverClickListener.onDriverClick(getAdapterPosition());
    }

}
