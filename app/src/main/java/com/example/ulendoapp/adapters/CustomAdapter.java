package com.example.ulendoapp.adapters;

import static com.example.ulendoapp.activityClasses.HomeUser.newText;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulendoapp.R;
import com.example.ulendoapp.models.UserModel;
import com.example.ulendoapp.viewHolders.ViewHolder;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener{

    private List<UserModel> driverModelList;
    private OnDriverClickListener onDriverClickListener;
    private int startPos, endPos;


    public CustomAdapter(List<UserModel> driverModelList, OnDriverClickListener onDriverClickListener) {
        this.driverModelList = driverModelList;
        this.onDriverClickListener = onDriverClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View driverView = inflater.inflate(R.layout.model_layout, parent, false);

        ViewHolder driverHolder = new ViewHolder(driverView, onDriverClickListener);
        return driverHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String fName = driverModelList.get(position).getFirstName();
        String lName = driverModelList.get(position).getLastName();

        SpannableStringBuilder firstName = new SpannableStringBuilder(fName);
        SpannableStringBuilder lastName = new SpannableStringBuilder(lName);

        ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{} }, new int[]{Color.BLUE});
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, colorStateList, null);

        if(fName.toLowerCase().contains(newText.toLowerCase())) {
            startPos = fName.toLowerCase().indexOf(newText.toLowerCase());
            endPos = startPos + newText.length();
            firstName.setSpan(textAppearanceSpan, startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        if (lName.toLowerCase().contains(newText.toLowerCase())) {
            startPos = lName.toLowerCase().indexOf(newText.toLowerCase());
            endPos = startPos + newText.length();
            lastName.setSpan(textAppearanceSpan, startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        holder.firstName.setText(firstName);
        holder.lastName.setText(lastName);
        holder.phoneNumber.setText(driverModelList.get(position).getPhoneNumber());
        holder.status.setText(driverModelList.get(position).getStatus());
    }

    public interface OnDriverClickListener {
        void onDriverClick(int position);

    }

    @Override
    public int getItemCount() {
        return driverModelList.size();
    }

    @Override
    public void onClick(View view) {

    }

}