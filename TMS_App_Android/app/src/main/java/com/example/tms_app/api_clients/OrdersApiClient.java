package com.example.tms_app.api_clients;

import android.content.Context;

import com.example.tms_app.callbacks.EventCallback;
import com.example.tms_app.callbacks.OrderDeleteCallback;
import com.example.tms_app.callbacks.OrderGetCallback;
import com.example.tms_app.callbacks.OrderPatchCallback;
import com.example.tms_app.callbacks.OrderPostCallback;
import com.example.tms_app.models.Order;
import com.example.tms_app.models.dto.EventDto;
import com.example.tms_app.models.dto.order.OrderDto;
import com.example.tms_app.models.dto.order.OrderPostDto;
import com.example.tms_app.service.EventService;
import com.example.tms_app.service.OrderService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersApiClient {
    private static final String BASE_URL ="http://192.168.0.213:8080/api/";
//    private static final String BASE_URL ="http://172.16.99.29:8080/api/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if(retrofit == null){
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClientBuilder.build())
                    .build();
        }
        return retrofit;
    }

    public static void fetchOrdersByUserId(final OrderGetCallback callback, Context context, String userId) {
        OrderService orderService = getClient().create(OrderService.class);
        Call<List<OrderDto>> call = orderService.getOrdersByUserId(userId);

        call.enqueue(new Callback<List<OrderDto>>() {
            @Override
            public void onResponse(Call<List<OrderDto>> call, Response<List<OrderDto>> response) {
                if(response.isSuccessful()) {
                    List<OrderDto> orders = response.body();
                    callback.onOrdersLoaded(orders);
                }
                else {
                    callback.onOrdersLoadedFailed("Failed to load orders...", context);
                }
            }

            @Override
            public void onFailure(Call<List<OrderDto>> call, Throwable t) {
                callback.onOrdersLoadedFailed(t.toString(), context);
            }
        });
    }

    public static void postOrder(final OrderPostCallback callback, Context context,
                                 String eventName, String ticketCategory, Integer numberOfTickets, String userId) {
        OrderService orderService = getClient().create(OrderService.class);
        OrderPostDto order = new OrderPostDto(eventName, ticketCategory, numberOfTickets);
        Call<OrderDto> call = orderService.saveOrder(order, userId);
        call.enqueue(new Callback<OrderDto>() {
            @Override
            public void onResponse(Call<OrderDto> call, Response<OrderDto> response) {
                if(response.isSuccessful()) {
                    OrderDto order = response.body();
                    callback.onOrderAdded(order);
                }
                else {
                    callback.onOrderAddedFailed("Failled to add order...", context);
                }
            }

            @Override
            public void onFailure(Call<OrderDto> call, Throwable t) {
                callback.onOrderAddedFailed(t.toString(), context);
            }
        });
    }

    public static void patchOrder(final OrderPatchCallback callback, Context context,
                                  String eventName, String ticketCategory, Integer numberOfTickets,
                                  String userId, String orderId) {
        OrderService orderService = getClient().create(OrderService.class);
        OrderPostDto order = new OrderPostDto(eventName, ticketCategory, numberOfTickets);
        Call<OrderDto> call = orderService.patchOrder(order, userId, orderId);
        call.enqueue(new Callback<OrderDto>() {
            @Override
            public void onResponse(Call<OrderDto> call, Response<OrderDto> response) {
                if(response.isSuccessful()) {
                    OrderDto order = response.body();
                    callback.onOrdersUpdated(order);
                }
                else {
                    callback.onOrdersUpdatedFailed("Failed to update order...", context);
                }
            }

            @Override
            public void onFailure(Call<OrderDto> call, Throwable t) {
                callback.onOrdersUpdatedFailed(t.toString(), context);
            }
        });
    }

    public static void deleteOrder(final OrderDeleteCallback callback, Context context, String userId, String orderId) {
        OrderService orderService = getClient().create(OrderService.class);
        Call<OrderDto> call = orderService.deleteOrder(userId, orderId);
        call.enqueue(new Callback<OrderDto>() {
            @Override
            public void onResponse(Call<OrderDto> call, Response<OrderDto> response) {
                if(response.isSuccessful()) {
                    OrderDto order = response.body();
                    callback.onOrderDeleted(order);
                }
                else {
                    callback.onOrderDeletedFailed("Failed to delete order...", context);
                }
            }

            @Override
            public void onFailure(Call<OrderDto> call, Throwable t) {
                callback.onOrderDeletedFailed(t.toString(), context);
            }
        });
    }
}
