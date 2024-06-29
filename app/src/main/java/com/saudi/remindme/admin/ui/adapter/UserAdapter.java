package com.saudi.remindme.admin.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.saudi.remindme.R;
import com.saudi.remindme.admin.ui.interfaces.OnUserListener;
import com.saudi.remindme.admin.ui.model.PatientItem;

import java.util.List;


public class UserAdapter extends BaseAdapter<PatientItem, UserAdapter.ViewHolder> {


    public UserAdapter(Context mContext, List<PatientItem> itemList, OnUserListener onUserListener) {
        super(mContext, itemList, onUserListener);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);

        return new ViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PatientItem user = itemList.get(position);
        // final int userPosition=position;
        holder.name.setText(user.getName());


        holder.activateUser.setVisibility(user.isActive() ? View.GONE : View.VISIBLE);
        holder.deActivateUser.setVisibility(user.isActive() ? View.VISIBLE : View.GONE);
        holder.activateUser.setOnClickListener(view -> onUserListener.onActivate(user.getName(), user.getId()));
        holder.deActivateUser.setOnClickListener(view -> onUserListener.onDeActivate(user.getName(), user.getId()));

        if (user.getGender().equalsIgnoreCase("Male")) {
            holder.profileImageView.setImageDrawable(mContext.getDrawable(R.drawable.c_m_profile));
        } else
            holder.profileImageView.setImageDrawable(mContext.getDrawable(R.drawable.female_profile));


    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView profileImageView;
        TextView name;


        ImageButton activateUser;
        ImageButton deActivateUser;

        ViewHolder(View itemView) {
            super(itemView);
            this.profileImageView = itemView.findViewById(R.id.profile);
            this.name = itemView.findViewById(R.id.user_name);


            this.deActivateUser = itemView.findViewById(R.id.deActivate_user);
            this.activateUser = itemView.findViewById(R.id.activate_user);

        }
    }

}


