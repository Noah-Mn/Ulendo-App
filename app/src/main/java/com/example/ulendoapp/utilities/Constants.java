package com.example.ulendoapp.utilities;

import java.util.HashMap;

public class Constants {
    public static final String KEY_COLLECTION_USERS = "Users";
    public static final String KEY_EMAIL = "Email Address";
    public static final String KEY_NAME = "name";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_IMAGE = "Profile Pic";
    public static final String KEY_T_SENDER_ID = "Trip sender ID";
    public static final String KEY_PASSENGER_NAME = "Passenger Name";
    public static final String KEY_T_RECEIVER_ID = "Trip receiver ID";
    public static final String KEY_USER = "user";
    public static final String KEY_COLLECT_CHAT = "chat";
    public static final String KEY_SENDER_ID = "senderId";
    public static final String KEY_RECEIVER_ID = "receiverId";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_COLLECTION_CONVERSATIONS = "conversations";
    public static final String KEY_SENDER_NAME = "senderName";
    public static final String KEY_RECEIVER_NAME = "receiverName";
    public static final String KEY_SENDER_IMAGE = "senderImage";
    public static final String KEY_TRIP_ID = "tripID";
    public static final String KEY_RECEIVER_IMAGE = "receiverImage";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_AVAILABILITY = "availability";
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String KEY_PREFERENCE_NAME = "chatAppPreference";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";
    public static final String KEY_VEHICLE_ID = "vehicleId";

    public static HashMap<String, String> remoteMsgHeaders = null;
    public static HashMap<String, String> getRemoteMsgHeaders(){
        if (remoteMsgHeaders == null){
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION, "key =  AAAAT2GYdrM:APA91bGQ1bP__6Z8wdVPrSiPn0I-5-YGbMhOXG8Hivm1YbrNHxrVACxuP6uxzbnXKHeh2-1OFoc6cFhg_huPpHhmJxr4UpnkVPgKR0sS-JVIXbl0ZAkWLxviUk3FFqQ6JPgFznD9Z3xv "
            );
            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json"
            );
        }
        return remoteMsgHeaders;
    }
}
