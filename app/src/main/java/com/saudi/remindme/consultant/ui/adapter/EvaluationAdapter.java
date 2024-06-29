package com.saudi.remindme.consultant.ui.adapter;

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
import com.saudi.remindme.user.ui.model.EvaluationItem;

import java.util.List;

public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.ViewHolder> {

    private final Context mContext;
    private final List<EvaluationItem> evaluations;

    public EvaluationAdapter(Context mContext, List<EvaluationItem> evaluationItemList) {
        this.evaluations = evaluationItemList;
        this.mContext = mContext;

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

        holder.user_name.setText(evaluationItem.getUserName());
        holder.review.setText(evaluationItem.getReview());
        holder.date.setText(evaluationItem.getDate());
        holder.ratingBar.setRating(evaluationItem.getRating());


    }

    @Override
    public int getItemCount() {
        return evaluations.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {


        protected TextView user_name;
        protected TextView review;
        protected RatingBar ratingBar;
        protected TextView date;
        protected ImageButton delete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);


            this.user_name = itemView.findViewById(R.id.userNameTextView);
            this.review = itemView.findViewById(R.id.reviewTextView);
            this.delete = itemView.findViewById(R.id.deleteReviewImageButton);
            this.ratingBar = itemView.findViewById(R.id.ratingBar);
            this.date = itemView.findViewById(R.id.dateReviewTextView);
        }
    }

}
