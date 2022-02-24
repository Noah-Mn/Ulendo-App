package com.example.ulendoapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ulendoapp.databinding.ActivityMainBinding;

import java.util.Collections;

import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.api.models.FilterObject;
import io.getstream.chat.android.client.logger.ChatLogLevel;
import io.getstream.chat.android.client.models.Filters;
import io.getstream.chat.android.client.models.User;
import io.getstream.chat.android.offline.ChatDomain;
import io.getstream.chat.android.ui.channel.list.ChannelListView;
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel;
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModelBinding;
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory;

public final class DriverChats extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_driver_chats);

//        inflate binding
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        set up the client for API calls and the domain for offline storage
        ChatClient client = new ChatClient.Builder("b67pax5b2wdq",getApplicationContext())
                .logLevel(ChatLogLevel.ALL)
                .internalBuild();
        new ChatDomain.Builder(client, getApplicationContext()).build();

//        Authenticate and connect the user

        User user = new User();
        user.setId("user");
        user.setName("Name of user");
        user.setImage("https://bit.ly/2TIt8NR");

        client.connectUser(
                user, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoidHV0b3JpYWwtZHJvaWQifQ.NhEr0hP9W9nwqV7ZkdShxvi02C5PR7SJE7Cs4y7kyqg"
        ).enqueue();

//        set the channel list filter and order
        FilterObject filterObject = Filters.and(Filters.eq("type", "messaging"), Filters.in("members", Collections.singletonList(user.getId())));

        ChannelListViewModelFactory factory = new ChannelListViewModelFactory(
                filterObject,
                ChannelListViewModel.DEFAULT_SORT
        );

        ChannelListViewModel channelListViewModel = new ViewModelProvider(this, factory).get(ChannelListViewModel.class);

        ChannelListViewModelBinding.bind(channelListViewModel, binding.channelListView, this);

        binding.channelListView.setChannelItemClickListener(channel ->
            startActivity(ChannelActivity.newIntent(this, channel))
        );
    }
}