package com.example.tms_app.callbacks;

import android.content.Context;

import com.example.tms_app.models.User;

public interface LogInCallback {
    void onLogInSuccess(User user);
    void onLogInFail(String errorMessage, Context context);
}
