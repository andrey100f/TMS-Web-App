package com.example.tms_app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tms_app.R;
import com.example.tms_app.api_clients.UserApiClient;
import com.example.tms_app.callbacks.LogInCallback;
import com.example.tms_app.models.User;

public class LogInActivity extends AppCompatActivity implements LogInCallback {
    private Button logInButton;
    private EditText usernameText;
    private EditText passwordText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        usernameText = findViewById(R.id.usernameLogIn);
        passwordText = findViewById(R.id.passwordLogIn);

        logInButton = findViewById(R.id.logInButton);

        logInButton.setOnClickListener(v -> {
            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();

            if(username.equals("") || password.equals("")){
                Toast.makeText(LogInActivity.this, "Wrong username or password!!", Toast.LENGTH_SHORT).show();
                return;
            }

            UserApiClient.logInUser(this, getBaseContext(), usernameText.getText().toString(), passwordText.getText().toString());
        });
    }

    public static void redirectActivity(Activity activity, Class secondActivity, User user) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userId", user.getUserId().toString());
        intent.putExtra("userName", user.getCustomerName());
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void onLogInSuccess(User user) {
        Log.i("Login Success", user.getUserId().toString());
        redirectActivity(LogInActivity.this, EventsActivity.class, user);
    }

    @Override
    public void onLogInFail(String errorMessage, Context context) {
        Log.i("Login Failure", errorMessage);
        Toast.makeText(LogInActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
