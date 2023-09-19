package com.example.tms_app.models;

import java.util.UUID;

public class EventCategory {
    private UUID categoryId;
    private String categoryName;
    private Integer eventCategoryVersion;

    public EventCategory() {
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getEventCategoryVersion() {
        return eventCategoryVersion;
    }

    public void setTimestamp(Integer eventCategoryVersion) {
        this.eventCategoryVersion = eventCategoryVersion;
    }
}
