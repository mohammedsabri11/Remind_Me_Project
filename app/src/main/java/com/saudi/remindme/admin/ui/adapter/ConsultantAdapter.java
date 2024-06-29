package com.saudi.remindme.admin.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.admin.ui.interfaces.OnUserListener;
import com.saudi.remindme.admin.ui.model.ConsultantItem;

import java.util.List;

public class ConsultantAdapter extends BaseAdapter<ConsultantItem, ConsultantAdapter.ViewHolder> {
    OnUser onUser;

    public ConsultantAdapter(Context mContext, List<ConsultantItem> itemList, OnUserListener onUserListener, OnUser onUser) {
        super(mContext, itemList, onUserListener);
        this.onUser = onUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.consultant_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ConsultantItem consultant = itemList.get(position);
        holder.name.setText(consultant.getName());

        holder.preview.setOnClickListener(view -> onUser.onReview(consultant.getName(), consultant.getCertificateImageUrl()));

        holder.activateUser.setVisibility(consultant.isActive() ? View.GONE : View.VISIBLE);
        holder.deActivateUser.setVisibility(consultant.isActive() ? View.VISIBLE : View.GONE);

        holder.activateUser.setOnClickListener(view -> onUserListener.onActivate(consultant.getName(), consultant.getId()));
        holder.deActivateUser.setOnClickListener(view -> onUserListener.onDeActivate(consultant.getName(), consultant.getId()));
        if (consultant.getProfile().equalsIgnoreCase("null")) {
            if (consultant.getGender().equalsIgnoreCase("Male")) {
                holder.profileImageView.setImageDrawable(mContext.getDrawable(R.drawable.c_m_profile));
            } else
                holder.profileImageView.setImageDrawable(mContext.getDrawable(R.drawable.female_profile));
        } else {
            Glide.with(mContext).load(Url.URL_LOAD_IMAGE + consultant.getProfile()).into(holder.profileImageView);

        }
    }

    public interface OnUser {
        void onReview(String name, String ImageUrl);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView name;
        ImageButton preview;
        ImageButton activateUser;
        ImageButton deActivateUser;

        ViewHolder(View itemView) {
            super(itemView);
            this.profileImageView = itemView.findViewById(R.id.profile);
            this.name = itemView.findViewById(R.id.user_name);
            this.preview = itemView.findViewById(R.id.imageButtonPreview);
            this.deActivateUser = itemView.findViewById(R.id.deActivate_user);
            this.activateUser = itemView.findViewById(R.id.activate_user);
        }
    }
}


