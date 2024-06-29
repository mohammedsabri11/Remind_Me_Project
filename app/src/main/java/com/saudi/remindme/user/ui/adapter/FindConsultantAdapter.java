package com.saudi.remindme.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.saudi.remindme.R;
import com.saudi.remindme.Url;
import com.saudi.remindme.admin.ui.model.ConsultantItem;
import com.saudi.remindme.user.ui.interfaces.OnItemListener;

import java.util.List;


public class FindConsultantAdapter extends RecyclerView.Adapter<FindConsultantAdapter.ViewHolder> {
    private final Context mContext;
    private final List<ConsultantItem> itemList;
    public OnItemListener onItemListener;


    public FindConsultantAdapter(Context mContext, List<ConsultantItem> itemList, OnItemListener onItemListener) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.onItemListener = onItemListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View rootView = LayoutInflater.from(mContext).inflate(R.layout.consultant_user_item, parent, false);

        return new ViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ConsultantItem consultantItem = itemList.get(position);

        holder.name.setText(consultantItem.getName());
        holder.short_description.setText(consultantItem.getShortDescription());
        holder.consultantSpeciality.setText(consultantItem.getSpeciality());
        holder.ExperienceYear.setText(consultantItem.getExperience());
        holder.evaluationCount.setText(consultantItem.getEvaluation());

        holder.chatImageButton.setOnClickListener(view -> onItemListener.onChatClick(consultantItem));

        holder.evaluation.setOnClickListener(view -> onItemListener.onEvaluationClick(consultantItem));
        if (consultantItem.getProfile().equalsIgnoreCase("null")) {
            if (consultantItem.getGender().equalsIgnoreCase("Male")) {
                holder.profile.setImageDrawable(mContext.getDrawable(R.drawable.c_m_profile));
            } else
                holder.profile.setImageDrawable(mContext.getDrawable(R.drawable.female_profile));
        } else {
            Glide.with(mContext).load(Url.URL_LOAD_IMAGE + consultantItem.getProfile()).into(holder.profile);

        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView profile;
        TextView name;
        TextView short_description;
        TextView consultantSpeciality;
        TextView ExperienceYear;
        TextView evaluationCount;
        ImageButton chatImageButton;
        RelativeLayout evaluation;

        ViewHolder(View itemView) {
            super(itemView);
            this.profile = itemView.findViewById(R.id.profile);
            this.name = itemView.findViewById(R.id.user_name);
            this.short_description = itemView.findViewById(R.id.short_description);
            this.consultantSpeciality = itemView.findViewById(R.id.consultantSpeciality);
            this.ExperienceYear = itemView.findViewById(R.id.ExperienceYear);
            this.evaluationCount = itemView.findViewById(R.id.evaluationCount);
            this.evaluation = itemView.findViewById(R.id.evaluationItem);
            this.chatImageButton = itemView.findViewById(R.id.chat);


        }
    }

}


