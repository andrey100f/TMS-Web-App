package com.example.tms_app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tms_app.R;
import com.example.tms_app.adapters.EventAdapter;
import com.example.tms_app.api_clients.EventApiClient;
import com.example.tms_app.api_clients.OrdersApiClient;
import com.example.tms_app.callbacks.OrderGetCallback;
import com.example.tms_app.models.Order;
import com.example.tms_app.adapters.OrderAdapter;
import com.example.tms_app.models.dto.EventDto;
import com.example.tms_app.models.dto.order.OrderDto;
import com.example.tms_app.service.EventService;
import com.example.tms_app.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity implements OrderGetCallback, OrderAdapter.OnReloadClickListener {
    private DrawerLayout drawerLayout;
    private ImageView menuButton;
    private ConstraintLayout eventsMenu, ordersMenu, logoutMenu;
    private static String customerName;
    public static String userId;

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<OrderDto> orders = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        customerName = getIntent().getStringExtra("userName");
        TextView userName = findViewById(R.id.userNameMenu);
        userName.setText(customerName);

        userId = getIntent().getStringExtra("userId");

        OrdersApiClient.fetchOrdersByUserId(this, getBaseContext(), userId);

        drawerLayout = findViewById(R.id.drawerLayout);
        menuButton = findViewById(R.id.menuButton);
        eventsMenu = findViewById(R.id.eventsMenu);
        ordersMenu = findViewById(R.id.ordersMenu);
        logoutMenu = findViewById(R.id.logoutMenu);

        menuButton.setOnClickListener(view -> openDrawer(drawerLayout));
        eventsMenu.setOnClickListener(view -> redirectActivity(OrdersActivity.this, EventsActivity.class));
        ordersMenu.setOnClickListener(view -> recreate());
        logoutMenu.setOnClickListener(view -> redirectActivity(OrdersActivity.this, LogInActivity.class));

        recyclerView = findViewById(R.id.orderRecycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OrderAdapter(this, orders, userId);
        adapter.setOnReloadClickListener(this);
        recyclerView.setAdapter(adapter);

        OrderService orderService = OrdersApiClient.getClient().create(OrderService.class);
        Call<List<OrderDto>> call = orderService.getOrdersByUserId(userId);

        call.enqueue(new Callback<List<OrderDto>>() {
            @Override
            public void onResponse(Call<List<OrderDto>> call, Response<List<OrderDto>> response) {
                if(response.isSuccessful()) {
                    Log.d("TAG","Response = " + response.body());
                    adapter.setOrderList(response.body());
                }
                else {
                    Log.i("Error..", "Error fetch");
                }
            }

            @Override
            public void onFailure(Call<List<OrderDto>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userName", customerName);
        intent.putExtra("userId", userId);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @Override
    public void onOrdersLoaded(List<OrderDto> orders) {
        this.orders.clear();
        this.orders.addAll(orders);
        adapter.notifyDataSetChanged();

        Log.i("Events loaded", String.valueOf(orders.size()));
    }

    @Override
    public void onOrdersLoadedFailed(String errorMessage, Context context) {
        Log.e("OrdersLoadFailed", errorMessage);
        Toast.makeText(context, "Error.. " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReloadClick() {
        recreate();
    }
}
