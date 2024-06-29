package com.saudi.remindme.consultant.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.saudi.remindme.R;
import com.saudi.remindme.admin.ui.model.PatientItem;
import com.saudi.remindme.consultant.ui.interfaces.OnOngoingQueryListener;
import com.saudi.remindme.consultant.ui.model.PatientChatItem;

import java.util.List;


public class OngoingQueryAdapter extends RecyclerView.Adapter<OngoingQueryAdapter.ViewHolder> {
    private final Context mContext;
    private final List<PatientChatItem> patientChatItems;
    public OnOngoingQueryListener onOngoingQueryListener;


    public OngoingQueryAdapter(Context mContext, List<PatientChatItem> patientChatItem, OnOngoingQueryListener onOngoingQueryListener) {
        this.mContext = mContext;
        this.patientChatItems = patientChatItem;
        this.onOngoingQueryListener = onOngoingQueryListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.ongoing_query_item, parent, false);
        return new ViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PatientChatItem patientChatItem = this.patientChatItems.get(position);
        PatientItem patient = this.patientChatItems.get(position).getPatientItem();
        holder.nameTextView.setText(patient.getName());
        holder.lastMessageTextView.setText(patientChatItem.getLastMessage());
        holder.nameTextView.setOnClickListener(view -> onOngoingQueryListener.onChat(patient));
        holder.lastMessageTextView.setOnClickListener(view -> onOngoingQueryListener.onChat(patient));
        holder.profileImageView.setOnClickListener(view -> onOngoingQueryListener.onProfile(patient));


        if (patient.getGender().equalsIgnoreCase("Male")) {
            holder.profileImageView.setBackground(mContext.getDrawable(R.drawable.c_m_profile));
        } else
            holder.profileImageView.setBackground(mContext.getDrawable(R.drawable.female_profile));


    }

    @Override
    public int getItemCount() {
        return patientChatItems.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView profileImageView;
        TextView nameTextView;
        TextView lastMessageTextView;

        ViewHolder(View itemView) {
            super(itemView);
            this.profileImageView = itemView.findViewById(R.id.profile);
            this.nameTextView = itemView.findViewById(R.id.user_name);
            this.lastMessageTextView = itemView.findViewById(R.id.lastMessageTextView);
        }
    }

}


