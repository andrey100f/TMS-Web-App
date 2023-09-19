package com.example.tms_app.api_clients;

import android.content.Context;

import com.example.tms_app.callbacks.LogInCallback;
import com.example.tms_app.models.User;
import com.example.tms_app.models.dto.LogInDto;
import com.example.tms_app.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserApiClient {
    private static final String BASE_URL ="http://192.168.0.213:8080/";
//    private static final String BASE_URL ="http://172.16.99.29:8080/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if(retrofit == null){
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static void logInUser(final LogInCallback callback, Context context, String username, String password) {
        UserService userService = getClient().create(UserService.class);

        LogInDto loginRequest = new LogInDto(username, password);

        Call<User> call = userService.logInUser(loginRequest);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    callback.onLogInSuccess(response.body());
                } else {
                    // Handle error response here
                    callback.onLogInFail("Fail", context);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onLogInFail(t.toString(), context);
            }
        });
    }
}
