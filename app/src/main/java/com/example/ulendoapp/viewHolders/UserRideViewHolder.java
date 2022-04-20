package com.example.ulendoapp.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.adapters.UserRideAdapter;

/**
 * The type User ride view holder.
 */
public class UserRideViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
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
     * The On user click listener.
     */
    public UserRideAdapter.OnUserClickListener onUserClickListener;

    /**
     * Instantiates a new User ride view holder.
     *
     * @param itemView            the item view
     * @param onUserClickListener the on user click listener
     */
    public UserRideViewHolder(View itemView, UserRideAdapter.OnUserClickListener onUserClickListener) {
        super(itemView);

    }

    @Override
    public void onClick(View view) {
        onUserClickListener.onUserClick(getAdapterPosition());
    }


}
