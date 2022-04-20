package com.example.ulendoapp.utilities;

import java.util.HashMap;

/**
 * The type Constants.
 */
public class Constants {
    /**
     * The constant KEY_COLLECTION_USERS.
     */
    public static final String KEY_COLLECTION_USERS = "Users";
    /**
     * The constant KEY_EMAIL.
     */
    public static final String KEY_EMAIL = "Email Address";
    /**
     * The constant KEY_NAME.
     */
    public static final String KEY_NAME = "name";
    /**
     * The constant KEY_FCM_TOKEN.
     */
    public static final String KEY_FCM_TOKEN = "fcmToken";
    /**
     * The constant KEY_USER_ID.
     */
    public static final String KEY_USER_ID = "userId";
    /**
     * The constant KEY_IMAGE.
     */
    public static final String KEY_IMAGE = "Profile Pic";
    /**
     * The constant KEY_T_SENDER_ID.
     */
    public static final String KEY_T_SENDER_ID = "Trip sender ID";
    /**
     * The constant KEY_PASSENGER_NAME.
     */
    public static final String KEY_PASSENGER_NAME = "Passenger Name";
    /**
     * The constant KEY_T_RECEIVER_ID.
     */
    public static final String KEY_T_RECEIVER_ID = "Trip receiver ID";
    /**
     * The constant KEY_USER.
     */
    public static final String KEY_USER = "user";
    /**
     * The constant KEY_COLLECT_CHAT.
     */
    public static final String KEY_COLLECT_CHAT = "chat";
    /**
     * The constant KEY_SENDER_ID.
     */
    public static final String KEY_SENDER_ID = "senderId";
    /**
     * The constant KEY_RECEIVER_ID.
     */
    public static final String KEY_RECEIVER_ID = "receiverId";
    /**
     * The constant KEY_MESSAGE.
     */
    public static final String KEY_MESSAGE = "message";
    /**
     * The constant KEY_TIMESTAMP.
     */
    public static final String KEY_TIMESTAMP = "timestamp";
    /**
     * The constant KEY_COLLECTION_CONVERSATIONS.
     */
    public static final String KEY_COLLECTION_CONVERSATIONS = "conversations";
    /**
     * The constant KEY_SENDER_NAME.
     */
    public static final String KEY_SENDER_NAME = "senderName";
    /**
     * The constant KEY_RECEIVER_NAME.
     */
    public static final String KEY_RECEIVER_NAME = "receiverName";
    /**
     * The constant KEY_SENDER_IMAGE.
     */
    public static final String KEY_SENDER_IMAGE = "senderImage";
    /**
     * The constant KEY_TRIP_ID.
     */
    public static final String KEY_TRIP_ID = "tripID";
    /**
     * The constant KEY_RECEIVER_IMAGE.
     */
    public static final String KEY_RECEIVER_IMAGE = "receiverImage";
    /**
     * The constant KEY_LAST_MESSAGE.
     */
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    /**
     * The constant KEY_AVAILABILITY.
     */
    public static final String KEY_AVAILABILITY = "availability";
    /**
     * The constant REMOTE_MSG_AUTHORIZATION.
     */
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    /**
     * The constant KEY_PREFERENCE_NAME.
     */
    public static final String KEY_PREFERENCE_NAME = "chatAppPreference";
    /**
     * The constant REMOTE_MSG_CONTENT_TYPE.
     */
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    /**
     * The constant REMOTE_MSG_DATA.
     */
    public static final String REMOTE_MSG_DATA = "data";
    /**
     * The constant REMOTE_MSG_REGISTRATION_IDS.
     */
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";
    /**
     * The constant KEY_VEHICLE_ID.
     */
    public static final String KEY_VEHICLE_ID = "vehicleId";

    /**
     * The Remote msg headers.
     */
    public static HashMap<String, String> remoteMsgHeaders = null;

    /**
     * Get remote msg headers hash map.
     *
     * @return the hash map
     */
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
