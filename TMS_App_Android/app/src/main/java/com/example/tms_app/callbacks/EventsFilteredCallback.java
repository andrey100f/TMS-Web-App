package com.example.tms_app.callbacks;

import android.content.Context;

import com.example.tms_app.models.dto.EventDto;

import java.util.List;

public interface EventsFilteredCallback {
    void onEventsFilteredLoaded(List<EventDto> events);
    void onEventsFilteredFailed(String errorMessage, Context context);
}
