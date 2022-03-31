package com.example.ulendoapp.activityClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ulendoapp.R;
import com.google.android.material.button.MaterialButton;

public class BookingRide extends AppCompatActivity {
    TextView dName, origin, destination, remainingSeats, date, pTime, sInstructions;
    String textName, textOrigin, textDestination, textSeats, textDate, textTime, textInst;
    TextView tName, tOrigin, tDestination, tSeats, tDate, tTime, tInst;
    MaterialButton btnBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_ride);

        dName = findViewById(R.id.booking_driver_name);
        origin = findViewById(R.id.booking_origin);
        destination = findViewById(R.id.booking_destination);
        remainingSeats = findViewById(R.id.booking_remaining_seats);
        date = findViewById(R.id.booking_trip_date);
        pTime = findViewById(R.id.booking_pickup_time);
        sInstructions = findViewById(R.id.booking_special_instruction);

        tName = findViewById(R.id.booking_driver_name_text);
        tOrigin = findViewById(R.id.booking_origin_text);
        tDestination = findViewById(R.id.booking_destination_text);
        tSeats = findViewById(R.id.booking_remaining_seats_texts);
        tDate = findViewById(R.id.booking_trip_date_text);
        tTime = findViewById(R.id.booking_pickup_time_text);
        tInst = findViewById(R.id.booking_special_instruction_text);

        btnBook = findViewById(R.id.trip_book_btn);

    }

    public void setText(){
        textName = dName.getText().toString();
        textOrigin =  origin.getText().toString();
        textDestination =  destination.getText().toString();
        textSeats =  remainingSeats.getText().toString();
        textDate =  date.getText().toString();
        textTime =  pTime.getText().toString();
        textInst =  sInstructions.getText().toString();

        tName.setText(textName);
        tOrigin.setText(textOrigin);
        tDestination.setText(textDestination);
        tSeats.setText(textSeats);
        tDate.setText(textDate);
        tTime.setText(textTime);
        tInst.setText(textInst);

    }
}