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
    List<Model>modelList;
    Context context;

    public CustomAdapter(ListActivity listActivity, List<Model> modelList, Context context) {
        this.listActivity = listActivity;
        this.modelList = modelList;
        this.context = context;
    }

    public CustomAdapter(Home home, List<Model> modelList) {
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

                String firstname = modelList.get(position).getFirstName();
                String lastname = modelList.get(position).getLastName();
                String status = modelList.get(position).getStatus();
                String phonenumber = modelList.get(position).getPhoneNumber();

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
        holder.first_name.setText(modelList.get(position).getFirstName());
        holder.last_name.setText(modelList.get(position).getLastName());
        holder.phone_number.setText(modelList.get(position).getPhoneNumber());
        holder.status.setText(modelList.get(position).getStatus());
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
