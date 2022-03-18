package com.example.ulendoapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.databinding.GroupLayoutBinding;
import com.example.ulendoapp.fragments.Group;
import com.example.ulendoapp.models.Groups;

import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupsViewHolder> {
    List<Groups> groupsList;

    public GroupsAdapter(List<Groups> groupsList) {
        this.groupsList = groupsList;
    }

    @NonNull
    @Override
    public GroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupLayoutBinding groupLayoutBinding = GroupLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new GroupsViewHolder(groupLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsViewHolder holder, int position) {
        holder.setGroupData(groupsList.get(position));
    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    public class GroupsViewHolder extends RecyclerView.ViewHolder{

        GroupLayoutBinding binding;
        public GroupsViewHolder(@NonNull GroupLayoutBinding groupLayoutBinding) {
            super(groupLayoutBinding.getRoot());
            binding = groupLayoutBinding;
        }
        void setGroupData(Groups groups){
            binding.startPoint.setText(groups.getStartingPoint());
            binding.destinationPoint.setText(groups.getDestination());
            binding.listOfPeople.setText(groups.getMemberName());
        }
    }
}
