package com.example.ulendoapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BookRideViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView imageView;
    public TextView driverName, carDetail, tripStartPoint, tripDestination, dateTime;
    public BookRideAdapter.OnTripClickListener onTripClickListener;
    public CardView tripLayout;

    public BookRideViewHolder(@NonNull View itemView, BookRideAdapter.OnTripClickListener onTripClickListener) {
        super(itemView);
        driverName = itemView.findViewById(R.id.name_of_driver);
        dateTime = itemView.findViewById(R.id.trip_status);
        carDetail = itemView.findViewById(R.id.car_details);
        tripStartPoint = itemView.findViewById(R.id.book_trip_starting_point);
        tripDestination = itemView.findViewById(R.id.book_trip_end_point);

//
//        tripLayout = itemView.findViewById(R.id.trip_layout);
//        this.onTripClickListener = onTripClickListener;
//        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        onTripClickListener.onTripClick(getAdapterPosition());
    }
}
