package com.example.tms_app.service;

import com.example.tms_app.models.User;
import com.example.tms_app.models.dto.LogInDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("login")
    Call<User> logInUser(@Body LogInDto logInDto);
}
