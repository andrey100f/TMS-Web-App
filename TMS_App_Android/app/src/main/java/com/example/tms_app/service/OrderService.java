package com.example.tms_app.service;

import com.example.tms_app.models.Order;
import com.example.tms_app.models.dto.order.OrderDto;
import com.example.tms_app.models.dto.order.OrderPostDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {
    @GET("orders/{userId}")
    Call<List<OrderDto>> getOrdersByUserId(@Path("userId") String userId);

    @POST("orders/{userId}")
    Call<OrderDto> saveOrder(@Body OrderPostDto order, @Path("userId") String userId);

    @PATCH("orders/{userId}/{orderId}")
    Call<OrderDto> patchOrder(@Body OrderPostDto order, @Path("userId") String userId,
                              @Path("orderId") String orderId);

    @DELETE("orders/{userId}/{orderId}")
    Call<OrderDto> deleteOrder(@Path("userId") String userId, @Path("orderId") String orderId);
}
