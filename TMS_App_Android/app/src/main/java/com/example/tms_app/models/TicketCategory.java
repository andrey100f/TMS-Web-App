package com.example.tms_app.models;

import java.util.UUID;

public class TicketCategory {
    private UUID ticketCategoryId;
    private String description;
    private Float price;
    private Integer ticketCategoryVersion;
    private Event event;

    public TicketCategory() {
    }

    public UUID getTicketCategoryId() {
        return ticketCategoryId;
    }

    public void setTicketCategoryId(UUID ticketCategoryId) {
        this.ticketCategoryId = ticketCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getTicketCategoryVersion() {
        return ticketCategoryVersion;
    }

    public void setTimestamp(Integer ticketCategoryVersion) {
        this.ticketCategoryVersion = ticketCategoryVersion;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
