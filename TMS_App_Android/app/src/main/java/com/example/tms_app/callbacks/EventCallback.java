package com.example.tms_app.callbacks;

import android.content.Context;

import com.example.tms_app.models.dto.EventDto;

import java.util.List;

public interface EventCallback {
    void onEventsLoaded(List<EventDto> events);
    void onEventsLoadFailed(String errorMessage, Context context);
}
