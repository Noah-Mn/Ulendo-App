package com.example.ulendoapp.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.adapters.UserAdapter;

/**
 * The type User view holder.
 */
public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /**
     * The First name.
     */
    public TextView firstName, /**
     * The Last name.
     */
    lastName, /**
     * The Status.
     */
    status, /**
     * The Phone number.
     */
    phoneNumber;
    /**
     * The Online user layout.
     */
    public CardView onlineUserLayout;
    /**
     * The On user online click listener.
     */
    public UserAdapter.OnUserOnlineClickListener onUserOnlineClickListener;

    /**
     * Instantiates a new User view holder.
     *
     * @param itemView                  the item view
     * @param onUserOnlineClickListener the on user online click listener
     */
    public UserViewHolder(View itemView, UserAdapter.OnUserOnlineClickListener onUserOnlineClickListener) {
        super(itemView);
    }

    @Override
    public void onClick(View view) {
        onUserOnlineClickListener.onUserOnlineClick(getAdapterPosition());
    }
}
