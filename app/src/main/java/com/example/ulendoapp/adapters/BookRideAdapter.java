package com.example.ulendoapp.adapters;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.classActivities.DriverVehiclesModel;
import com.example.ulendoapp.models.OfferRideModel;
import com.example.ulendoapp.R;
import com.example.ulendoapp.viewHolders.BookRideViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class BookRideAdapter extends RecyclerView.Adapter<BookRideViewHolder> implements View.OnClickListener {
    private List<OfferRideModel> offerRideModelList;
    private List<DriverVehiclesModel> carDetailModelList;
    private OnTripClickListener onTripClickListener;
    private String textDriverName, textTripState, pickupTime, pickupPoint, destination, carBrand, carModel, carModelYr, carColor, driverName, email, date;
    private FirebaseFirestore db;
    private int selectedPos;
    MaterialButton btnBook;

    public BookRideAdapter(List<OfferRideModel> offerRideModelList, List<DriverVehiclesModel> carDetailModelList, OnTripClickListener onTripClickListener) {
        this.offerRideModelList = offerRideModelList;
        this.carDetailModelList = carDetailModelList;
        this.onTripClickListener = onTripClickListener;
        selectedPos = -1;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public BookRideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = FirebaseFirestore.getInstance();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View tripView = inflater.inflate(R.layout.book_trip_layout, parent, false);

        BookRideViewHolder tripHolder = new BookRideViewHolder(tripView, onTripClickListener);
        return tripHolder;                                                              
    }

    @Override
    public void onBindViewHolder(@NonNull BookRideViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        pickupPoint = offerRideModelList.get(position).getPickupPoint();
        destination = offerRideModelList.get(position).getDestination();
        email = offerRideModelList.get(position).getEmailAddress();
        pickupTime = offerRideModelList.get(position).getPickupTime();
        date = offerRideModelList.get(position).getDate();

//        String vBrand = carDetailModelList.get(0).getVehicleBrand();
//        String vModel = carDetailModelList.get(0).getVehicleModel();
//        String vModelYr = carDetailModelList.get(0).getVehicleModelYr();
//        String vColor = carDetailModelList.get(0).getVehicleColor();

        getDriverName(email, holder);
//        String car = new StringBuilder().append(vBrand).append(" ").append(vModel).append(" ")
//                .append(vModelYr).append(" ").append(vColor).toString();

        String dateTime =new StringBuilder().append("11 dec, 2022").append(" @ ")
                .append("11 : 45 PM").toString();


        holder.dateTime.setText(dateTime);
        holder.carDetail.setText(email +" , "+ carDetailModelList.size());
        holder.tripStartPoint.setText(pickupPoint);
        holder.tripDestination.setText(destination);

        if (selectedPos == position){
            holder.tripLayout.setBackgroundColor(Color.LTGRAY);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int previousItem  = selectedPos;
                selectedPos = position;
                notifyItemChanged(previousItem);
                notifyItemChanged(position);
            }
        });

    }

    public void getDriverName(String email, BookRideViewHolder holder){
        db.collection("Users")
                .whereEqualTo("Email Address", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        progressDialog.dismiss();
                        for (DocumentSnapshot documentSnapshot: task.getResult()){
                            String firstName = documentSnapshot.getString("First Name");
                            String lastName = documentSnapshot.getString("Surname");

                            driverName = firstName +" "+ lastName;
                            holder.driverName.setText(driverName);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    @Override
    public int getItemCount() {
        return offerRideModelList.size();

    }

    public interface OnTripClickListener {
        void onTripClick(int position);
    }

}
