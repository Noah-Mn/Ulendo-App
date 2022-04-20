package com.example.ulendoapp.activityClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.databinding.ActivityBookRideRequestBinding;
import com.google.android.material.button.MaterialButton;

/**
 * The type Book ride request.
 */
public class BookRideRequest extends AppCompatActivity {
    /**
     * The Binding.
     */
    ActivityBookRideRequestBinding binding;
    /**
     * The Dialog.
     */
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookRideRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new Dialog(this);
        showPopup();
    }

    /**
     * Show popup.
     */
    public void showPopup(){
        TextView textView;
        MaterialButton accept, reject;
        dialog.setContentView(R.layout.ride_request_layout);
        textView = (TextView) dialog.findViewById(R.id.close_window);
        accept = (MaterialButton) dialog.findViewById(R.id.btn_accept);
        reject = (MaterialButton) dialog.findViewById(R.id.btn_reject);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onBackPressed();
            }
        });
        dialog.show();
    }
}