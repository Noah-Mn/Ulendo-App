package com.example.ulendoapp;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
    /*View view;
    public ViewHolder(@NonNull View itemView, List<UserModel> userModelList) {
        super(itemView);

        view = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
                return true;
            }
        });
        first_name = itemView.findViewById(R.id.first_name);
        last_name = itemView.findViewById(R.id.last_name);
        status = itemView.findViewById(R.id.status);
        phone_number = itemView.findViewById(R.id.phone_number);
    }

    private  ViewHolder.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(ClickListener clickListener){
        mClickListener = clickListener;
    }*/
}
