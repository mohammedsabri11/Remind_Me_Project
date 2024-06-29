package com.saudi.remindme.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.admin.ui.model.ConsultantItem;
import com.saudi.remindme.user.ui.interfaces.OnItemListener;
import com.saudi.remindme.user.ui.model.ConsultantChatItem;

import java.util.List;


public class OngoingQueryAdapter extends RecyclerView.Adapter<OngoingQueryAdapter.ViewHolder> {

    private final Context mContext;
    private final List<ConsultantChatItem> chatItemList;
    public OnItemListener onItemListener;


    public OngoingQueryAdapter(Context mContext, List<ConsultantChatItem> chatItemList, OnItemListener onItemListener) {
        this.mContext = mContext;
        this.chatItemList = chatItemList;
        this.onItemListener = onItemListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.ongoing_query_item, parent, false);

        return new ViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ConsultantChatItem chatItem = chatItemList.get(position);
        final ConsultantItem consultant = chatItemList.get(position).getConsultantItem();

        holder.nameTextView.setText(consultant.getName());
        holder.shortDescriptionTextView.setText(chatItem.getLastMessage());
        holder.nameTextView.setOnClickListener(view -> onItemListener.onChatClick(consultant));
        holder.shortDescriptionTextView.setOnClickListener(view -> onItemListener.onChatClick(consultant));
        holder.profileImageView.setOnClickListener(view -> onItemListener.onEvaluationClick(consultant));

        if (consultant.getProfile().equalsIgnoreCase("null")) {
            if (consultant.getGender().equalsIgnoreCase("Male")) {
                holder.profileImageView.setImageDrawable(mContext.getDrawable(R.drawable.c_m_profile));
            } else
                holder.profileImageView.setImageDrawable(mContext.getDrawable(R.drawable.female_profile));
        } else {
            Glide.with(mContext).load(Url.URL_LOAD_IMAGE + consultant.getProfile()).into(holder.profileImageView);

        }
    }

    @Override
    public int getItemCount() {
        return chatItemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView profileImageView;
        TextView nameTextView;
        TextView shortDescriptionTextView;

        ViewHolder(View itemView) {
            super(itemView);
            this.profileImageView = itemView.findViewById(R.id.profile);
            this.nameTextView = itemView.findViewById(R.id.user_name);
            this.shortDescriptionTextView = itemView.findViewById(R.id.lastMessageTextView);


        }
    }

}


