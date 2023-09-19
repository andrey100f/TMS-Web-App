package com.example.tms_app.api_clients;

import android.content.Context;

import com.example.tms_app.callbacks.EventCallback;
import com.example.tms_app.callbacks.EventsFilteredCallback;
import com.example.tms_app.models.dto.EventDto;
import com.example.tms_app.service.EventService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventApiClient {
    private static final String BASE_URL ="http://192.168.0.213:8080/api/";
//    private static final String BASE_URL ="http://172.16.99.29:8080/api/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if(retrofit == null){
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClientBuilder.build())
                    .build();
        }
        return retrofit;
    }

    public static void fetchEvents(final EventCallback callback, Context context) {
        EventService eventService = getClient().create(EventService.class);
        Call<List<EventDto>> call = eventService.getEvents();

        call.enqueue(new Callback<List<EventDto>>() {
            @Override
            public void onResponse(Call<List<EventDto>> call, Response<List<EventDto>> response) {
                if (response.isSuccessful()) {
                    List<EventDto> events = response.body();
                    callback.onEventsLoaded(events);
                } else {
                    callback.onEventsLoadFailed("Failed to load events", context);
                }
            }

            @Override
            public void onFailure(Call<List<EventDto>> call, Throwable t) {
                callback.onEventsLoadFailed(t.toString(), context);
            }
        });
    }

    public static void fetchFilteredEvents(final EventsFilteredCallback callback, Context context,
                                           String location, String category) {
        EventService eventService = getClient().create(EventService.class);
        Call<List<EventDto>> call = eventService.getEventsByLocationAndCategory(location, category);
        call.enqueue(new Callback<List<EventDto>>() {
            @Override
            public void onResponse(Call<List<EventDto>> call, Response<List<EventDto>> response) {
                if(response.isSuccessful()) {
                    List<EventDto> events = response.body();
                    callback.onEventsFilteredLoaded(events);
                }
                else {
                    callback.onEventsFilteredFailed("Failed to filter events", context);
                }
             }

            @Override
            public void onFailure(Call<List<EventDto>> call, Throwable t) {
                callback.onEventsFilteredFailed(t.toString(), context);
            }
        });
    }
}
