package com.example.ulendoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ulendoapp.adapters.RecentConversationsAdapter;
import com.example.ulendoapp.databinding.ActivityDriverChatsBinding;
import com.example.ulendoapp.databinding.ActivityMainBinding;
import com.example.ulendoapp.models.ChatMessage;
import com.example.ulendoapp.utilities.Constants;
import com.example.ulendoapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private ActivityDriverChatsBinding binding;
    private PreferenceManager preferenceManager;
    private final String TAG = "Driver Chats";
    private User receiverUser;
    MaterialTextView textView;
    String fName,lastName, encodedImage, fcmToken, emailAddress,email;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    private List<ChatMessage> conversations;
    private RecentConversationsAdapter conversationsAdapter;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        binding = ActivityDriverChatsBinding.inflate(getLayoutInflater());
        init();
        getToken();
        setListeners();
        loadUserDetails();
    }

    private void init(){
        conversations = new ArrayList<>();
        conversationsAdapter = new RecentConversationsAdapter(conversations);
        binding.conversationsRecyclerView.setAdapter(conversationsAdapter);
        database = FirebaseFirestore.getInstance();
    }

    private void loadUserDetails(){
        textView = findViewById(R.id.text_name);

        db.collection("Users")
                .whereEqualTo("Email Address", getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                fName = document.getString("First Name");
                                lastName = document.getString("Surname");
                                encodedImage = document.getString("Profile Pic");

                                byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                binding.imageProfile.setImageBitmap(bitmap);
                                binding.textName.setText(new StringBuilder().append(fName).append(" ").append(lastName).toString());

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

        private void updateToken(String token){
            if(!token.isEmpty()){
                db.collection("Users")
                        .whereEqualTo("Email Address", getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, "Email already exist!");
                                        String userId = document.getId();
                                        db.collection("Users")
                                                .document(userId)
                                                .update("fcmToken", token);

//                                        Toast.makeText(DriverChats.this, "successfully updated token", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(DriverChats.this, "change failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else {
//            Toast.makeText(EditDriverProfile.this, "Email did not update", Toast.LENGTH_LONG).show();
            }
    }
    public String getEmail(){
        String emailAddress;
        emailAddress = currentUser.getEmail();
        return emailAddress;
    }
    private void setListeners(){
        binding.fabNewChat.setOnClickListener(view ->
            startActivity(new Intent(getApplicationContext(), UsersActivity.class))
        );
        binding.imageCancel.setOnClickListener(View ->
                startActivity(new Intent(DriverChats.this, HomeDriver.class)));
    }
}