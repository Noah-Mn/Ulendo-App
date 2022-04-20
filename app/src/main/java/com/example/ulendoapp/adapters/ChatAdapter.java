package com.example.ulendoapp.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.databinding.ItemContainerReceivedMessageBinding;
import com.example.ulendoapp.databinding.ItemContainerSentMessageBinding;
import com.example.ulendoapp.models.ChatMessage;

import java.util.List;

/**
 * The type Chat adapter.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<ChatMessage> chatMessages;
    private Bitmap receiverProfileImage;
    private final String senderId;

    /**
     * The constant VIEW_TYPE_SENT.
     */
    public static final int VIEW_TYPE_SENT = 1;
    /**
     * The constant VIEW_TYPE_RECEIVED.
     */
    public static final int VIEW_TYPE_RECEIVED = 2;

    /**
     * Set receiver profile image.
     *
     * @param bitmap the bitmap
     */
    public void setReceiverProfileImage(Bitmap bitmap){
        receiverProfileImage = bitmap;
    }

    /**
     * Instantiates a new Chat adapter.
     *
     * @param chatMessages         the chat messages
     * @param receiverProfileImage the receiver profile image
     * @param senderId             the sender id
     */
    public ChatAdapter(List<ChatMessage> chatMessages, Bitmap receiverProfileImage, String senderId) {
        this.chatMessages = chatMessages;
        this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT){
            return new SentMessageViewHolder(
                    ItemContainerSentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }else{
            return new ReceiverMessageViewHolder(
                    ItemContainerReceivedMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SENT){
            ((SentMessageViewHolder)holder).setData(chatMessages.get(position));
        }else{
            ((ReceiverMessageViewHolder)holder).setData(chatMessages.get(position),receiverProfileImage);
        }

    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT;
        }else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    /**
     * The type Sent message view holder.
     */
    static class  SentMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerSentMessageBinding binding;

        /**
         * Instantiates a new Sent message view holder.
         *
         * @param itemContainerSentMessageBinding the item container sent message binding
         */
        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding){
            super(itemContainerSentMessageBinding.getRoot());
            binding = itemContainerSentMessageBinding;
        }

        /**
         * Set data.
         *
         * @param chatMessage the chat message
         */
        void setData(ChatMessage chatMessage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
        }
    }

    /**
     * The type Receiver message view holder.
     */
    static class ReceiverMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerReceivedMessageBinding binding;

        /**
         * Instantiates a new Receiver message view holder.
         *
         * @param itemContainerReceivedMessageBinding the item container received message binding
         */
        ReceiverMessageViewHolder(ItemContainerReceivedMessageBinding itemContainerReceivedMessageBinding){
            super(itemContainerReceivedMessageBinding.getRoot());
            binding = itemContainerReceivedMessageBinding;
        }

        /**
         * Set data.
         *
         * @param chatMessage          the chat message
         * @param receiverProfileImage the receiver profile image
         */
        void setData(ChatMessage chatMessage, Bitmap receiverProfileImage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
            if (receiverProfileImage != null){
                binding.imageProfile.setImageBitmap(receiverProfileImage);
            }
        }
    }
}
