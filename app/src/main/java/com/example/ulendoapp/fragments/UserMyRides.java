package com.example.ulendoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.adapters.UserAdapter;
import com.example.ulendoapp.adapters.UserRideAdapter;
import com.example.ulendoapp.models.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserMyRides#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserMyRides extends Fragment implements UserRideAdapter.OnUserClickListener, UserAdapter.OnUserOnlineClickListener {
    private RecyclerView recyclerViewCard;
    private List<UserModel> userList;
    private ImageView backBtn;
    private UserModel user;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserMyRides() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserMyRides.
     */
    // TODO: Rename and change types and number of parameters
    public static UserMyRides newInstance(String param1, String param2) {
        UserMyRides fragment = new UserMyRides();
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
        View view = inflater.inflate(R.layout.fragment_user_my_rides, container, false);

        recyclerViewCard = view.findViewById(R.id.userRideRecyclerView);
        userList = new ArrayList<>();
        userRide();

        return view;

    }

    public boolean userRide() {
        user = new UserModel("passenger", "lonjezo", "banthapo", "088889");
        userList.add(user);

        UserRideAdapter adapter = new UserRideAdapter(userList, UserMyRides.this);
        recyclerViewCard.setAdapter(adapter);
        recyclerViewCard.setLayoutManager(new LinearLayoutManager(getContext()));

        return true;
    }

    @Override
    public void onUserOnlineClick(int adapterPosition) {

    }

    @Override
    public void onUserClick(int position) {

    }
}