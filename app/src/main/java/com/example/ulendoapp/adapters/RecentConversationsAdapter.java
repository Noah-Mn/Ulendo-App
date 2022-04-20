package com.example.ulendoapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.databinding.ItemContainerRecentConversationBinding;
import com.example.ulendoapp.listeners.ConversationListener;
import com.example.ulendoapp.models.ChatMessage;
import com.example.ulendoapp.models.User;

import java.util.List;

/**
 * The type Recent conversations adapter.
 */
public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversationHolder>{

    private final List<ChatMessage> chatMessages;
    private ConversationListener conversationListener;

    /**
     * Instantiates a new Recent conversations adapter.
     *
     * @param chatMessages         the chat messages
     * @param conversationListener the conversation listener
     */
    public RecentConversationsAdapter(List<ChatMessage> chatMessages, ConversationListener conversationListener) {
        this.chatMessages = chatMessages;
        this.conversationListener = conversationListener;
    }

    @NonNull
    @Override
    public ConversationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationHolder(
                ItemContainerRecentConversationBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    /**
     * The type Conversation holder.
     */
    class ConversationHolder extends RecyclerView.ViewHolder{

        /**
         * The Binding.
         */
        ItemContainerRecentConversationBinding binding;

        /**
         * Instantiates a new Conversation holder.
         *
         * @param itemContainerRecentConversationBinding the item container recent conversation binding
         */
        ConversationHolder(ItemContainerRecentConversationBinding itemContainerRecentConversationBinding){
            super(itemContainerRecentConversationBinding.getRoot());
            binding = itemContainerRecentConversationBinding;
        }

        /**
         * Set data.
         *
         * @param chatMessage the chat message
         */
        void setData(ChatMessage chatMessage){
            binding.imageProfile.setImageBitmap(getConversationImage(chatMessage.conversionImage));
            binding.textName.setText(chatMessage.conversationName);
            binding.textRecentMessage.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(view -> {
                User user = new User();
                user.id = chatMessage.conversationId;
                user.name = chatMessage.conversationName;
                user.image = chatMessage.conversionImage;
                conversationListener.onConversationClicked(user);
            });
        }
    }

    private Bitmap getConversationImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0, bytes.length);
    }
}
