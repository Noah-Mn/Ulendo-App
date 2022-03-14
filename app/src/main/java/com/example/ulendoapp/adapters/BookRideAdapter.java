package com.example.ulendoapp.adapters;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.activityClasses.DriverVehiclesModel;
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
    private OnTripClickListener onTripClickListener;
    private String textDriverName, textTripState, pickupTime, pickupPoint, destination, carBrand, carModel, carModelYr, carColor, driverName, email, date,encodedImage;
    private FirebaseFirestore db;
    private int selectedPos;

    public BookRideAdapter(List<OfferRideModel> offerRideModelList, OnTripClickListener onTripClickListener) {
        this.offerRideModelList = offerRideModelList;
        this.onTripClickListener = onTripClickListener;
        selectedPos = -1;
    }

    public BookRideAdapter() {

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
    public void onBindViewHolder(@NonNull BookRideViewHolder holder, int position) {

        pickupPoint = offerRideModelList.get(position).getPickupPoint();
        destination = offerRideModelList.get(position).getDestination();
        email = offerRideModelList.get(position).getEmailAddress();
        pickupTime = offerRideModelList.get(position).getPickupTime();
        date = offerRideModelList.get(position).getPickupDate();

        getDriverName(email, holder);
        getRideData(email, holder);
        String dateTime =new StringBuilder().append("Date: ").append(date).append(" @ ")
                .append(pickupTime).toString();


        holder.dateTime.setText(dateTime);
        holder.tripStartPoint.setText(pickupPoint);
        holder.tripDestination.setText(destination);

        if (selectedPos == position){
            holder.tripLayout.setBackgroundColor(Color.LTGRAY);
            holder.itemView.setSelected(true);
        }
        else {
            holder.itemView.setSelected(false);
        }

        holder.itemView.setOnClickListener(view -> {
            if (selectedPos >= 0)
                notifyItemChanged(selectedPos);
            selectedPos = holder.getAdapterPosition();
            notifyItemChanged(selectedPos);
//            int previousItem  = selectedPos;
//            selectedPos = position;
//            notifyItemChanged(previousItem);
//            notifyItemChanged(selectedPos);
        });

    }

      /* public void getCarDetailData() {
        db.collection("Find Ride")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        progressDialog.dismiss();
                        carDetailModelList.clear();
                        for (DocumentSnapshot documentSnapshot: task.getResult()){

                            String destination = documentSnapshot.getString("Destination");
                            String pickupPoint = documentSnapshot.getString("Pickup Point");
                            String pickupTime = documentSnapshot.getString("Pickup Time");
                            String luggage = documentSnapshot.getString("Luggage");
                            String bookedSeats = documentSnapshot.getString("Booked Seats");
                            String latLng = String.valueOf(documentSnapshot.getString("Location"));

                            DriverVehiclesModel carDetail = new DriverVehiclesModel();

                            carDetailModelList.add(carDetail);

                            getFilteredRides();
                            Toast.makeText(BookingActivity.this, carDetailModelList.get(0).getVehicleBrand(), Toast.LENGTH_SHORT).show();

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
*/

    public void getRideData(String email, BookRideViewHolder holder) {
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

                            holder.carDetail.setText(car);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

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
                            String encodedImage = documentSnapshot.getString("Profile Pic");
                            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            holder.driverProfilePic.setImageBitmap(bitmap);
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
