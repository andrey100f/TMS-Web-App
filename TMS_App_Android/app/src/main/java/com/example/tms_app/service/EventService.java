package com.example.tms_app.service;

import com.example.tms_app.models.dto.EventDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EventService {
    @GET("events")
    Call<List<EventDto>> getEvents();

    @GET("events/filters")
    Call<List<EventDto>> getEventsByLocationAndCategory(@Query("location") String location,
                                                    @Query("category") String category);
}
