package com.example.ulendoapp;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    ListActivity listActivity;
    List<UserModel> userModelList;
    Context context;

    public CustomAdapter(ListActivity listActivity, List<UserModel> userModelList, Context context) {
        this.listActivity = listActivity;
        this.userModelList = userModelList;
        this.context = context;
    }

    public CustomAdapter(HomeUser homeUser, List<UserModel> userModelList) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.setOnClickListener(new ViewHolder.ClickListener(){

            @Override
            public void onItemClick(View view, int position) {

                String firstname = userModelList.get(position).getFirstName();
                String lastname = userModelList.get(position).getLastName();
                String status = userModelList.get(position).getStatus();
                String phonenumber = userModelList.get(position).getPhoneNumber();

                Toast.makeText(listActivity, firstname+"\n"+lastname+"\n"+status+"\n"+phonenumber, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.first_name.setText(userModelList.get(position).getFirstName());
        holder.last_name.setText(userModelList.get(position).getLastName());
        holder.phone_number.setText(userModelList.get(position).getPhoneNumber());
        holder.status.setText(userModelList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

//    @Override
//    public int getItemCount() {
//        return userModelList.size();
//    }
}
