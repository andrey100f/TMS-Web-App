package com.example.tms_app.callbacks;

import android.content.Context;

import com.example.tms_app.models.dto.order.OrderDto;

public interface OrderDeleteCallback {
    void onOrderDeleted(OrderDto order);
    void onOrderDeletedFailed(String errorMessage, Context context);
}
