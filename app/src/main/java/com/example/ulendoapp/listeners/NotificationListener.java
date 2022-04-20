package com.example.ulendoapp.listeners;

import com.example.ulendoapp.models.BookingModel;

/**
 * The interface Notification listener.
 */
public interface NotificationListener {
    /**
     * On notification clicked.
     *
     * @param bookingModel the booking model
     */
    void onNotificationClicked (BookingModel bookingModel);
}
