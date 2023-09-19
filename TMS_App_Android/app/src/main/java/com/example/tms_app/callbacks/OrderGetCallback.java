package com.example.tms_app.callbacks;

import android.content.Context;

import com.example.tms_app.models.Order;
import com.example.tms_app.models.dto.order.OrderDto;

import java.util.List;

public interface OrderGetCallback {
    void onOrdersLoaded(List<OrderDto> orders);
    void onOrdersLoadedFailed(String errorMessage, Context context);
}
