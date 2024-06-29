package com.saudi.remindme.statedialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.saudi.remindme.R;

public class ReviewDialog extends Dialog implements View.OnClickListener {
    private final RatingBar ratingBar;
    private final AppCompatEditText reviewEditText;
    private final AppCompatButton submitButton;

    private final OnReviewSubmittedListener listener;

    public ReviewDialog(@NonNull Context context, OnReviewSubmittedListener listener) {
        super(context);
        this.listener = listener;

        setContentView(R.layout.dialog_review);

        ratingBar = findViewById(R.id.ratingBar);
        reviewEditText = findViewById(R.id.reviewEditText);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitButton) {
            int rating = (int) ratingBar.getRating();
            String review = reviewEditText.getText().toString();
            if (rating != 0 && !review.isEmpty() && listener != null) {
                listener.onReviewSubmitted(rating, review);
            } else {
                reviewEditText.setError("type your review ");
            }


            dismiss();
        }
    }

    public interface OnReviewSubmittedListener {
        void onReviewSubmitted(int rating, String review);
    }
}
