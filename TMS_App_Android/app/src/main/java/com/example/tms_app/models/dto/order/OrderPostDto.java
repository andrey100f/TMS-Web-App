package com.example.tms_app.models.dto.order;

public class OrderPostDto {
    private String eventName;
    private String ticketCategory;
    private Integer numberOfTickets;
    private Float totalPrice;

    public OrderPostDto(String eventName, String ticketCategory, Integer numberOfTickets) {
        this.eventName = eventName;
        this.ticketCategory = ticketCategory;
        this.numberOfTickets = numberOfTickets;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(String ticketCategory) {
        this.ticketCategory = ticketCategory;
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
