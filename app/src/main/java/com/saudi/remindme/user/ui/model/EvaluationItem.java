package com.saudi.remindme.user.ui.model;

import java.io.Serializable;

public class EvaluationItem implements Serializable {
    private final String id;
    private final String userId;
    private final String userName;
    private final String review;
    private final Float rating;
    private final String date;

    public EvaluationItem(String id, String userId, String userName, String review, Float rating, String date) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.review = review;
        this.rating = rating;
        this.date = date;
    }

    public String getReview() {
        return review;
    }

    public Float getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }


}
