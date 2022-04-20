package com.example.ulendoapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.models.UserModel;
import com.example.ulendoapp.databinding.ItemContainerUserBinding;
import com.example.ulendoapp.viewHolders.UserViewHolder;

import java.util.List;

/**
 * The type User adapter.
 */
public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> implements View.OnClickListener{
    private List<UserModel> onlineUserModelList;
    private OnUserOnlineClickListener onUserOnlineClickListener;
    /**
     * The Rc.
     */
    RecyclerView rc;
    private List<UserModel> user;

    private Bitmap getUserImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Instantiates a new User adapter.
     *
     * @param onlineUserModelList       the online user model list
     * @param onUserOnlineClickListener the on user online click listener
     */
    public UserAdapter(List<UserModel> onlineUserModelList, UserAdapter.OnUserOnlineClickListener onUserOnlineClickListener) {
        this.onlineUserModelList = onlineUserModelList;
        this.onUserOnlineClickListener = onUserOnlineClickListener;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(LayoutInflater.from(parent.getContext()));
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View onlineUserView = inflater.inflate(R.layout.online_user_layout, parent, false);

        UserViewHolder onlineUserHolder = new UserViewHolder(onlineUserView, onUserOnlineClickListener);
        return onlineUserHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return onlineUserModelList.size();
    }

    /**
     * The interface On user online click listener.
     */
    public interface OnUserOnlineClickListener {
        /**
         * On user online click.
         *
         * @param adapterPosition the adapter position
         */
        void onUserOnlineClick(int adapterPosition);
    }


}
