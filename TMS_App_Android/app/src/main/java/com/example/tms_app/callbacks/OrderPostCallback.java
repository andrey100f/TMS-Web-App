package com.example.tms_app.callbacks;

import android.content.Context;

import com.example.tms_app.models.dto.order.OrderDto;

public interface OrderPostCallback {
    void onOrderAdded(OrderDto orders);
    void onOrderAddedFailed(String errorMessage, Context context);
}
