package com.example.ulendoapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.databinding.BookTripLayoutBinding;
import com.example.ulendoapp.listeners.RideListener;
import com.example.ulendoapp.models.DriverVehiclesModel;
import com.example.ulendoapp.models.OfferRideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class BookRideAdapter extends RecyclerView.Adapter<BookRideAdapter.BookRideViewHolder>{
    private final RideListener rideListener;
    private ArrayList<OfferRideModel> offerRideModelList;
    private String textDriverName, textTripState, pickupTime, pickupPoint, destination, carBrand, carModel,
            carModelYr, carColor, driverName, email, date,encodedImage;
    private FirebaseFirestore db;
    private int checkedPosition = 0;

    public BookRideAdapter(ArrayList<OfferRideModel> offerRideModelList, RideListener rideListener) {
        this.offerRideModelList = offerRideModelList;
        this.rideListener = rideListener;
    }

    @Override
    public int getItemCount() {
        return offerRideModelList.size();

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
        OfferRideModel offerRideModel = new OfferRideModel();
//        holder.bind(offerRideModelList.get(position));
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
        holder.tripLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rideListener.onRideClick(offerRideModel);
            }
        });

    }

//    @SuppressLint("RecyclerView")
//    @Override
//    public void onBindViewHolder(@NonNull BookRideViewHolder holder, int position) {
//
//        pickupPoint = offerRideModelList.get(position).getPickupPoint();
//        destination = offerRideModelList.get(position).getDestination();
//        email = offerRideModelList.get(position).getEmailAddress();
//        pickupTime = offerRideModelList.get(position).getPickupTime();
//        date = offerRideModelList.get(position).getPickupDate();
//
//        getDriverName(email, holder);
//        getRideData(email, holder);
//        String dateTime =new StringBuilder().append("Date: ").append(date).append(" @ ")
//                .append(pickupTime).toString();
//
//        holder.dateTime.setText(dateTime);
//        holder.tripStartPoint.setText(pickupPoint);
//        holder.tripDestination.setText(destination);
//
//    }
    class BookRideViewHolder extends RecyclerView.ViewHolder{

        public RoundedImageView driverProfilePic;
        public TextView driverName, carDetail, tripStartPoint, tripDestination, dateTime;
        public CardView tripLayout;

         BookTripLayoutBinding binding;
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
//        void bind(final OfferRideModel offerRideModel){
//            if (checkedPosition == -1){
//                itemView.setBackgroundColor(Color.WHITE);
//            }else {
//                if (checkedPosition == getAdapterPosition()){
//                    itemView.setBackgroundColor(Color.LTGRAY);
//                }else {
//                    itemView.setBackgroundColor(Color.WHITE);
//                }
//
//            }
//            getRideData(email);
//            getDriverName(email);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                  itemView.setBackgroundColor(Color.LTGRAY);
//                  if (checkedPosition != getAdapterPosition()){
//                      notifyItemChanged(checkedPosition);
//                      checkedPosition = getAdapterPosition();
//                  }
//                }
//            });
//        }

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

                            DriverVehiclesModel carDetail = new DriverVehiclesModel(vBrand, vModel, vModelYr, vColor);
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
