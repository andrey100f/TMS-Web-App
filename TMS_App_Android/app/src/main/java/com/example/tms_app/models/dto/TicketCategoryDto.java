package com.example.tms_app.models.dto;

import com.example.tms_app.models.Event;

public class TicketCategoryDto {
    private String description;
    private Float price;
    private EventDto event;

    public TicketCategoryDto() {
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

    public EventDto getEvent() {
        return event;
    }

    public void setEvent(EventDto event) {
        this.event = event;
    }
}
