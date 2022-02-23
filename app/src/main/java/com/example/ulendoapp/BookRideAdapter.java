package com.example.ulendoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookRideAdapter extends RecyclerView.Adapter<BookRideViewHolder> implements View.OnClickListener {
    private List<OfferRideModel> offerRideModelList;
    private List<DriverVehiclesModel> carDetailModelList;
    private OnTripClickListener onTripClickListener;
    private String textDriverName, textTripState, pickupTime, pickupPoint, destination, carBrand, carModel, carModelYr, carColor;

    public BookRideAdapter(List<OfferRideModel> offerRideModelList, List<DriverVehiclesModel> carDetailModelList, OnTripClickListener onTripClickListener) {
        this.offerRideModelList = offerRideModelList;
        this.carDetailModelList = carDetailModelList;
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

        String pickupPoint = offerRideModelList.get(position).getPickupPoint();
        String destination = offerRideModelList.get(position).getDestination();
        String email = offerRideModelList.get(position).getEmailAddress();
        String pickupTime = offerRideModelList.get(position).getPickupTime();
//        String vBrand = carDetailModelList.get(position).getVehicleBrand();
//        String vModel = carDetailModelList.get(position).getVehicleModel();
//        String vModelYr = carDetailModelList.get(position).getVehicleModelYr();
//        String vColor = carDetailModelList.get(position).getVehicleColor();

        holder.driverName.setText("destination");
        holder.tripState.setText("destination");
        holder.carDetail.setText("destination");
        holder.tripStartPoint.setText(pickupPoint);
        holder.tripDestination.setText(destination);

    }

    @Override
    public int getItemCount() {
        return offerRideModelList.size();

    }

    public interface OnTripClickListener {
        void onTripClick(int position);
    }

}
