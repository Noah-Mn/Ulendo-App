package com.example.ulendoapp.listeners;

import com.example.ulendoapp.models.User;

/**
 * The interface Conversation listener.
 */
public interface ConversationListener {
    /**
     * On conversation clicked.
     *
     * @param user the user
     */
    void onConversationClicked(User user);
}
