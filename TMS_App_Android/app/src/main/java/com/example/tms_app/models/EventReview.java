package com.example.tms_app.models;

import java.util.UUID;

public class EventReview {
    private UUID reviewId;
    private String reviewText;
    private Integer rating;
    private Integer event_review_version;
    private User user;
    private Event event;

    public EventReview() {
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public void setReviewId(UUID reviewId) {
        this.reviewId = reviewId;
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

    public Integer getEvent_review_version() {
        return event_review_version;
    }

    public void setEvent_review_version(Integer event_review_version) {
        this.event_review_version = event_review_version;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
