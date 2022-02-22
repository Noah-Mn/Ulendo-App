package com.example.ulendoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookRideAdapter extends RecyclerView.Adapter<BookRideViewHolder> implements View.OnClickListener {
    private List<FindRideModel> findRideModelList;
    private OnTripClickListener onTripClickListener;


    public BookRideAdapter(List<FindRideModel> findRideModelList, OnTripClickListener onTripClickListener) {
        this.findRideModelList = findRideModelList;
        this.onTripClickListener = onTripClickListener;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public BookRideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View tripView = inflater.inflate(R.layout.book_trip_layout, parent, false);
        BookRideViewHolder tripHolder = new BookRideViewHolder(tripView, onTripClickListener);
        return tripHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookRideViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    interface OnTripClickListener{
        void onTripClick(int position);
    }
}
