package com.saudi.remindme.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saudi.remindme.R;
import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.user.ui.interfaces.OnDeleteListener;
import com.saudi.remindme.user.ui.model.EvaluationItem;

import java.util.List;

public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.ViewHolder> {

    private final Context mContext;
    private final List<EvaluationItem> evaluations;
    private final OnDeleteListener onDeleteListener;

    public EvaluationAdapter(Context mContext, List<EvaluationItem> evaluationList) {
        this.evaluations = evaluationList;
        this.mContext = mContext;
        this.onDeleteListener = (OnDeleteListener) mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.evaluation_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final EvaluationItem evaluationItem = evaluations.get(position);

        holder.textViewUserName.setText(evaluationItem.getUserName());
        holder.textViewReview.setText(evaluationItem.getReview());
        holder.dateReviewTextView.setText(evaluationItem.getDate());
        holder.ratingBar.setRating(evaluationItem.getRating());
        holder.deleteImageButton.setVisibility(evaluationItem.getUserId().equals(SessionManager.getInstance(mContext).getKeyUserId()) ? View.VISIBLE : View.GONE);

        holder.deleteImageButton.setOnClickListener(view -> onDeleteListener.onDelete(evaluationItem.getId()));


    }

    @Override
    public int getItemCount() {
        return evaluations.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {


        protected TextView textViewUserName;
        protected TextView textViewReview;
        protected RatingBar ratingBar;
        protected TextView dateReviewTextView;
        protected ImageButton deleteImageButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);


            this.textViewUserName = itemView.findViewById(R.id.userNameTextView);
            this.textViewReview = itemView.findViewById(R.id.reviewTextView);
            this.deleteImageButton = itemView.findViewById(R.id.deleteReviewImageButton);
            this.ratingBar = itemView.findViewById(R.id.ratingBar);
            this.dateReviewTextView = itemView.findViewById(R.id.dateReviewTextView);
        }
    }

}
