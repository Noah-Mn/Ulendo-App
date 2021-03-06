package com.example.ulendoapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ulendoapp.R;
import com.google.android.material.textview.MaterialTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link driver_my_payments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class driver_my_payments extends Fragment {

    MaterialTextView textView;
    ImageView imageView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Instantiates a new Driver my payments.
     */
    public driver_my_payments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment driver_my_payments.
     */
// TODO: Rename and change types and number of parameters
    public static driver_my_payments newInstance(String param1, String param2) {
        driver_my_payments fragment = new driver_my_payments();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_my_payments, container, false);

        textView = view.findViewById(R.id.mpamba);
        imageView = view.findViewById(R.id.mpamba_image);

        textView.setOnClickListener(view12 -> {
//            Intent callIntent = new Intent(Intent.ACTION_CALL, ussdToCallableUri("*211#"));
//            startActivity(callIntent);
        });
        imageView.setOnClickListener(view1 -> {
//            Intent callIntent = new Intent(Intent.ACTION_CALL, ussdToCallableUri("*211#"));
//            startActivity(callIntent);
        });
        return view;
    }
    private Uri ussdToCallableUri(String ussd){
        String uriString = "";

        if(!ussd.startsWith("tel:"))
            uriString += "tel:";

        for(char c : ussd.toCharArray()) {

            if(c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }

        return Uri.parse(uriString);
    }
}