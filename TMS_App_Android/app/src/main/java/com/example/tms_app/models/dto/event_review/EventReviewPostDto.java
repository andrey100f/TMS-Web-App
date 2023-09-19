package com.example.tms_app.models.dto.event_review;

public class EventReviewPostDto {
    private String reviewText;
    private Integer rating;

    public EventReviewPostDto() {
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
