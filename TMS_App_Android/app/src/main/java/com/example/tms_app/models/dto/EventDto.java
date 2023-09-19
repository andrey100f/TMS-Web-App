package com.example.tms_app.models.dto;

import com.example.tms_app.models.dto.event_review.EventReviewDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventDto {
    @SerializedName("venue")
    private VenueDto venue;

    @SerializedName("eventCategory")
    private String eventCategory;

    @SerializedName("eventDescription")
    private String eventDescription;

    @SerializedName("eventName")
    private String eventName;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("ticketCategories")
    private List<TicketCategoryDto> ticketCategories;

    @SerializedName("eventReviews")
    private List<EventReviewDto> eventReviews;

    public EventDto() {
    }

    public VenueDto getVenue() {
        return venue;
    }

    public void setVenue(VenueDto venue) {
        this.venue = venue;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<TicketCategoryDto> getTicketCategories() {
        return ticketCategories;
    }

    public void setTicketCategories(List<TicketCategoryDto> ticketCategories) {
        this.ticketCategories = ticketCategories;
    }

    public List<EventReviewDto> getEventReviews() {
        return eventReviews;
    }

    public void setEventReviews(List<EventReviewDto> eventReviews) {
        this.eventReviews = eventReviews;
    }
}
