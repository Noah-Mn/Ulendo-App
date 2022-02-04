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


/*

    public Filter getFilter() {
        return filter;
    }
*/

    /*Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<UserModel> filteredDrivers = new ArrayList<>();
            HomeUser drivers = new HomeUser(CustomAdapter.this);

            if (!constraint.toString().isEmpty()) {

                for (int i = 0, driverListSize = driverModelList.size(); i < driverListSize; i++) {
                    String firstName = driverModelList.get(i).getFirstName();
                    String lastName = driverModelList.get(i).getLastName();
                    String status = driverModelList.get(i).getStatus();
                    String phoneNumber = driverModelList.get(i).getPhoneNumber();

                    UserModel userModel = new UserModel(firstName, lastName, status, phoneNumber);

                    if (firstName.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredDrivers.add(userModel);

                    } else if (lastName.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredDrivers.add(userModel);
                    }

                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredDrivers;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (!charSequence.toString().isEmpty()) {
                driverModelList.addAll((Collection<? extends UserModel>) filterResults.values);
                notifyDataSetChanged();
            }
        }
    };*/
/*
    public CustomAdapter(List<UserModel> driverModelList, Context context) {
//        this.listActivity = listActivity;
        this.driverModelList = driverModelList;
        this.context = context;
    }

    public CustomAdapter(HomeUser homeUser, List<UserModel> driverModelList) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_layout, parent, false);

        ViewHolder driverHolder = new ViewHolder(itemView, driverModelList);

        driverHolder.setOnClickListener(new ViewHolder.ClickListener(){

            @Override
            public void onItemClick(View view, int position) {

                String firstname = driverModelList.get(position).getFirstName();
                String lastname = driverModelList.get(position).getLastName();
                String status = driverModelList.get(position).getStatus();
                String phonenumber = driverModelList.get(position).getPhoneNumber();

                Toast.makeText(listActivity, firstname+"\n"+lastname+"\n"+status+"\n"+phonenumber, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        return driverHolder;
    }
    public void filterList(ArrayList<UserModel> filterllist) {
        driverModelList = filterllist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.first_name.setText(driverModelList.get(position).getFirstName());
        holder.last_name.setText(driverModelList.get(position).getLastName());
        holder.phone_number.setText(driverModelList.get(position).getPhoneNumber());
        holder.status.setText(driverModelList.get(position).getStatus());
    }

*/

//    @Override
//    public int getItemCount() {
//        return userModelList.size();
//    }

}