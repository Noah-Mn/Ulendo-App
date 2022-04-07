package com.example.ulendoapp.adapters;

import static com.example.ulendoapp.fragments.fragment_offer_rides.latitude;
import static com.example.ulendoapp.fragments.fragment_offer_rides.longitude;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.activityClasses.BookingActivity;
import com.example.ulendoapp.activityClasses.BookingRide;
import com.example.ulendoapp.databinding.BookTripLayoutBinding;
import com.example.ulendoapp.models.DriverVehiclesModel;
import com.example.ulendoapp.models.FindRideModel;
import com.example.ulendoapp.models.OfferRideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookRideAdapter extends RecyclerView.Adapter<BookRideAdapter.BookRideViewHolder> implements View.OnClickListener{
    private ArrayList<OfferRideModel> offerRideModelList;
    private OnTripClickListener onTripClickListener;
    private String textDriverName, textTripState, pickupTime, pickupPoint, destination, carBrand, carModel,
            carModelYr, carColor, driverName, email, date,encodedImage;
    private FirebaseFirestore db;
    private int checkedPosition = 0;
    private FindRideModel findRideDetails;
    private Context context;

    public BookRideAdapter(ArrayList<OfferRideModel> offerRideModelList, BookRideAdapter.OnTripClickListener onTripClickListener,
                           FindRideModel findRideDetails, Context context) {
        this.offerRideModelList = offerRideModelList;
        this.onTripClickListener = onTripClickListener;
        this.findRideDetails = findRideDetails;
        this.context = context;

    }

    public void setTrips(ArrayList<OfferRideModel> offerRideModelLists) {
        this.offerRideModelList = new ArrayList<>();
        this.offerRideModelList = offerRideModelLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return offerRideModelList.size();

    }

    @Override
    public void onClick(View view) {
        context.startActivity(new Intent(context, BookingRide.class));
    }

    public interface OnTripClickListener {
        void onTripClick(int position);
    }

    @NonNull
    @Override
    public BookRideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = FirebaseFirestore.getInstance();
        BookTripLayoutBinding bookTripLayoutBinding = BookTripLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new BookRideViewHolder(bookTripLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookRideViewHolder holder, int position) {
        holder.bind(offerRideModelList.get(position));
        pickupPoint = offerRideModelList.get(position).getPickupPoint();
        destination = offerRideModelList.get(position).getDestination();
        email = offerRideModelList.get(position).getEmailAddress();
        pickupTime = offerRideModelList.get(position).getPickupTime();
        date = offerRideModelList.get(position).getPickupDate();

        String dateTime =new StringBuilder().append("Date: ").append(date).append(" @ ")
                .append(pickupTime).toString();

        holder.dateTime.setText(dateTime);
        holder.tripStartPoint.setText(pickupPoint);
        holder.tripDestination.setText(destination);

    }

    class BookRideViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{


        public RoundedImageView driverProfilePic;
        public TextView driverName, carDetail, tripStartPoint, tripDestination, dateTime;
        public CardView tripLayout;
        public BookTripLayoutBinding binding;


        BookRideViewHolder(BookTripLayoutBinding bookTripLayoutBinding) {
            super(bookTripLayoutBinding.getRoot());
            binding = bookTripLayoutBinding;

            driverName = itemView.findViewById(R.id.name_of_driver);
            dateTime = itemView.findViewById(R.id.trip_date_time);
            carDetail = itemView.findViewById(R.id.car_details);
            tripStartPoint = itemView.findViewById(R.id.book_trip_starting_point);
            tripDestination = itemView.findViewById(R.id.book_trip_end_point);
            tripLayout = itemView.findViewById(R.id.trip_layout);
            driverProfilePic = itemView.findViewById(R.id.trip_driver_profile_image);

            getRideData(email);
            getDriverName(email);
        }

        void bind(final OfferRideModel offerRideModel){
           /* if (checkedPosition == -1){
                itemView.setBackgroundColor(Color.WHITE);
            }else {
                if (checkedPosition == getAdapterPosition()){
                    itemView.setBackgroundColor(Color.LTGRAY);
                }else {
                    itemView.setBackgroundColor(Color.WHITE);
                }

            }*/
            getRideData(email);
            getDriverName(email);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  itemView.setBackgroundColor(Color.LTGRAY);
                  if (checkedPosition != getAdapterPosition()){
                      notifyItemChanged(checkedPosition);
                      checkedPosition = getAdapterPosition();
                      int cp = checkedPosition;
                      OfferRideModel tripDetails = offerRideModelList.get(cp);

                      Intent intent = new Intent(context, BookingRide.class);
                      intent.putExtra("tripDetails", (Serializable) tripDetails);
                      intent.putExtra("findRideDetails", (Serializable) findRideDetails);

                      context.startActivity(intent);
                  }
                }
            });
        }

    @Override
    public void onClick(View view) {
        onTripClickListener.onTripClick(getAdapterPosition());
    }


    public void getRideData(String email) {
        db.collection("Driver Vehicles")
                .whereEqualTo("Email Address", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        progressDialog.dismiss();
                        for (DocumentSnapshot documentSnapshot: task.getResult()){

                            String vBrand = documentSnapshot.getString("Vehicle Brand");
                            String vModel = documentSnapshot.getString("Vehicle Model");
                            String vModelYr = documentSnapshot.getString("Model Year");
                            String vColor = documentSnapshot.getString("Vehicle Color");

                            String car = new StringBuilder().append(vBrand).append(" ").append(vModel).append(" ")
                                    .append(vModelYr).append(" ").append(vColor).toString();
                            binding.carDetails.setText(car);

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void getDriverName(String email){
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
                            String encodedImage = documentSnapshot.getString("Profile Pic");
                            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            binding.tripDriverProfileImage.setImageBitmap(bitmap);
                            String driverName = firstName +" "+ lastName;
                            binding.nameOfDriver.setText(driverName);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    }


}
