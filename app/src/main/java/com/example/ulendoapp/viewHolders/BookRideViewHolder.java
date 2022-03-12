package com.example.ulendoapp.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.adapters.BookRideAdapter;
import com.example.ulendoapp.R;
import com.google.android.material.button.MaterialButton;

public class BookRideViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView imageView;
    public TextView driverName, carDetail, tripStartPoint, tripDestination, dateTime;
    public CardView tripLayout;
    public MaterialButton btnBook;

    public BookRideViewHolder(@NonNull View itemView, BookRideAdapter.OnTripClickListener onTripClickListener) {
        super(itemView);
        driverName = itemView.findViewById(R.id.name_of_driver);
        dateTime = itemView.findViewById(R.id.trip_status);
        carDetail = itemView.findViewById(R.id.car_details);
        tripStartPoint = itemView.findViewById(R.id.book_trip_starting_point);
        tripDestination = itemView.findViewById(R.id.book_trip_end_point);
        tripLayout = (CardView) itemView.findViewById(R.id.trip_layout);
        btnBook = itemView.findViewById(R.id.trip_book_btn);
        itemView.setOnClickListener(this);
        onTripClickListener.onTripClick(getAdapterPosition());

//
//        tripLayout = itemView.findViewById(R.id.trip_layout);
//        this.onTripClickListener = onTripClickListener;
//        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
      if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
      else {
          btnBook.setClickable(true);
      }

    }
}
