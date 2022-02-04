package com.example.ulendoapp;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener{
    //    ListActivity listActivity;
//    Context context;
    private List<UserModel> driverModelList;
    private OnDriverClickListener onDriverClickListener;

    public CustomAdapter(List<UserModel> driverModelList, OnDriverClickListener onDriverClickListener) {
        this.driverModelList = driverModelList;
        this.onDriverClickListener = onDriverClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View driverView = inflater.inflate(R.layout.model_layout, parent, false);

        ViewHolder driverHolder = new ViewHolder(driverView, onDriverClickListener);
        return driverHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.firstName.setText(driverModelList.get(position).getFirstName());
        holder.lastName.setText(driverModelList.get(position).getLastName());
        holder.phoneNumber.setText(driverModelList.get(position).getPhoneNumber());
        holder.status.setText(driverModelList.get(position).getStatus());
    }

    public interface OnDriverClickListener {
        void onDriverClick(int position);
    }

    @Override
    public int getItemCount() {
        return driverModelList.size();
    }

    @Override
    public void onClick(View view) {

    }

}