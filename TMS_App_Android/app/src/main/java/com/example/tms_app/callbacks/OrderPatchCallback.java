package com.example.tms_app.callbacks;

import android.content.Context;

import com.example.tms_app.models.dto.order.OrderDto;

public interface OrderPatchCallback {
    void onOrdersUpdated(OrderDto order);
    void onOrdersUpdatedFailed(String errorMessage, Context context);
}
