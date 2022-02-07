package com.example.ulendoapp;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

   /* public void highlightSearchText(String searchText){

    }*/

    @Override
    public void onClick(View view) {
        onDriverClickListener.onDriverClick(getAdapterPosition());
    }

}
