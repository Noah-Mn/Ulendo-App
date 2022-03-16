package com.example.ulendoapp.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.adapters.BookRideAdapter;
import com.example.ulendoapp.R;
import com.example.ulendoapp.models.OfferRideModel;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

public class BookRideViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public RoundedImageView driverProfilePic;
    public TextView driverName, carDetail, tripStartPoint, tripDestination, dateTime;
    public CardView tripLayout;
    public MaterialButton btnBook;
    BookRideAdapter.OnTripClickListener onTripClickListener;

    public BookRideViewHolder(@NonNull View itemView, BookRideAdapter.OnTripClickListener onTripClickListener) {
        super(itemView);
        driverName = itemView.findViewById(R.id.name_of_driver);
        dateTime = itemView.findViewById(R.id.trip_date_time);
        carDetail = itemView.findViewById(R.id.car_details);
        tripStartPoint = itemView.findViewById(R.id.book_trip_starting_point);
        tripDestination = itemView.findViewById(R.id.book_trip_end_point);
        tripLayout = (CardView) itemView.findViewById(R.id.trip_layout);
        btnBook = itemView.findViewById(R.id.trip_book_btn);
        driverProfilePic = itemView.findViewById(R.id.trip_driver_profile_image);
        itemView.setOnClickListener(this);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


//
//        tripLayout = itemView.findViewById(R.id.trip_layout);
//        this.onTripClickListener = onTripClickListener;
//        itemView.setOnClickListener(this);

    }
    void clickEvent(){
        tripLayout.setOnClickListener(view -> onTripClickListener.onTripClick(getAdapterPosition()));
    }

    @Override
    public void onClick(View view) {
        onTripClickListener.onTripClick(getAdapterPosition());
    }
}
