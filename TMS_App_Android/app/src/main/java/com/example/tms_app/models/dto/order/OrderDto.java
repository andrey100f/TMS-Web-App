package com.example.tms_app.models.dto.order;

import com.example.tms_app.models.dto.EventDto;
import com.example.tms_app.models.dto.TicketCategoryDto;

import java.util.UUID;

public class OrderDto {
    private UUID orderId;
    private TicketCategoryDto ticketCategory;
    private String eventName;
    private EventDto event;
    private String orderedAt;
    private Integer numberOfTickets;
    private Float totalPrice;

    public OrderDto() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public TicketCategoryDto getTicketCategory() {
        return ticketCategory;
    }

    public EventDto getEvent() {
        return event;
    }

    public void setEvent(EventDto event) {
        this.event = event;
    }

    public void setTicketCategory(TicketCategoryDto ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(String orderedAt) {
        this.orderedAt = orderedAt;
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
