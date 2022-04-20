package com.example.ulendoapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.databinding.ItemContainerUserBinding;
import com.example.ulendoapp.listeners.UserListener;
import com.example.ulendoapp.models.User;

import java.util.List;

/**
 * The type Users adapter.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private final List<User> users;
    private  final UserListener userListener;

    /**
     * Instantiates a new Users adapter.
     *
     * @param users        the users
     * @param userListener the user listener
     */
    public UsersAdapter(List<User> users, UserListener userListener) {
        this.users = users;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(itemContainerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserdata(users.get(position));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    /**
     * The type User view holder.
     */
    class UserViewHolder extends RecyclerView.ViewHolder{

        /**
         * The Binding.
         */
        ItemContainerUserBinding binding;

        /**
         * Instantiates a new User view holder.
         *
         * @param itemContainerUserBinding the item container user binding
         */
        UserViewHolder(ItemContainerUserBinding itemContainerUserBinding){
            super(itemContainerUserBinding.getRoot());
            binding = itemContainerUserBinding;
        }

        /**
         * Set userdata.
         *
         * @param user the user
         */
        void setUserdata(User user){
            binding.textName.setText(user.name);
            binding.textEmail.setText(user.email);
            binding.imageProfile.setImageBitmap(getUserImage(user.image));
            binding.getRoot().setOnClickListener(view -> userListener.onUserClicked(user));
        }
    }

    private Bitmap getUserImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
