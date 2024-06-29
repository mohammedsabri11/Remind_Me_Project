package com.saudi.remindme.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saudi.remindme.R;
import com.saudi.remindme.user.ui.interfaces.OnDeleteListener;
import com.saudi.remindme.user.ui.model.ReminderItem;

import java.util.List;


public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private final Context mContext;
    private final List<ReminderItem> reminderItemList;
    private final OnDeleteListener onDeleteListener;


    public ReminderAdapter(Context mContext, List<ReminderItem> reminderItemList, OnDeleteListener onDeleteListener) {
        this.mContext = mContext;
        this.reminderItemList = reminderItemList;
        this.onDeleteListener = onDeleteListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.reminder_item, parent, false);
        return new ViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ReminderItem remind = reminderItemList.get(position);

        holder.reminderDateTimeTextView.setText(remind.getDateTime());

        holder.reminderDetailTextView.setText(remind.getReminderDetails());
        holder.reminderDeleteImageButton.setOnClickListener(view -> onDeleteListener.onDelete(remind.getId()));


    }

    @Override
    public int getItemCount() {
        return reminderItemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView reminderDateTimeTextView;
        TextView reminderDetailTextView;
        ImageButton reminderDeleteImageButton;

        ViewHolder(View itemView) {
            super(itemView);

            this.reminderDateTimeTextView = itemView.findViewById(R.id.reminderDateTimeTextView);

            this.reminderDetailTextView = itemView.findViewById(R.id.reminderDetailTextView);
            this.reminderDeleteImageButton = itemView.findViewById(R.id.reminderDeleteImageButton);


        }
    }

}


