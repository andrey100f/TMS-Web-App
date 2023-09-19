package com.example.tms_app.models;

import java.util.UUID;

public class Venue {
    private UUID venueId;
    private String location;
    private String type;
    private Integer capacity;
    private Integer venueVersion;

    public Venue() {
    }

    public UUID getVenueId() {
        return venueId;
    }

    public void setVenueId(UUID venueId) {
        this.venueId = venueId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getVenueVersion() {
        return venueVersion;
    }

    public void setTimestamp(Integer venueVersion) {
        this.venueVersion = venueVersion;
    }
}
