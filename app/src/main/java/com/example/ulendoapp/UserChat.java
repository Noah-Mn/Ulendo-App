package com.example.ulendoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ulendoapp.databinding.ActivityUserChatBinding;

public class UserChat extends AppCompatActivity {
    ActivityUserChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}