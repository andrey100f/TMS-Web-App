package com.example.tms_app.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Event {
    private UUID eventId;
    private String eventName;
    private String eventDescription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String imageUrl;
    private Integer eventVersion;
    private Venue venue;
    private EventCategory eventCategory;
    private List<TicketCategory> ticketCategories;
    private List<EventReview> eventReviews;

    public Event() {
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getEventVersion() {
        return this.eventVersion;
    }

    public void setEventVersion(Integer eventVersion) {
        this.eventVersion = eventVersion;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public List<TicketCategory> getTicketCategories() {
        return ticketCategories;
    }

    public void setTicketCategories(List<TicketCategory> ticketCategories) {
        this.ticketCategories = ticketCategories;
    }

    public List<EventReview> getEventReviews() {
        return eventReviews;
    }

    public void setEventReviews(List<EventReview> eventReviews) {
        this.eventReviews = eventReviews;
    }
}
