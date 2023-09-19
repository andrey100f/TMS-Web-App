package com.example.tms_app.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.tms_app.adapters.OrderAdapter;
import com.example.tms_app.api_clients.EventApiClient;
import com.example.tms_app.api_clients.OrdersApiClient;
import com.example.tms_app.callbacks.EventCallback;
import com.example.tms_app.callbacks.OrderPostCallback;
import com.example.tms_app.dialogs.FilterDialog;
import com.example.tms_app.models.Order;
import com.example.tms_app.models.dto.EventDto;
import com.example.tms_app.models.dto.order.OrderDto;
import com.example.tms_app.service.EventService;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventsActivity extends AppCompatActivity implements EventCallback, FilterDialog.OnFilterSelectedListener {
    private DrawerLayout drawerLayout;
    private EventAdapter adapter;
    private RecyclerView recyclerView;

    private List<EventDto> events = new ArrayList<>();
    private static String customerName;
    private static String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        customerName = getIntent().getStringExtra("userName");
        TextView userName = findViewById(R.id.userNameMenu);
        userName.setText(customerName);

        userId = getIntent().getStringExtra("userId");

        EventApiClient.fetchEvents(this, getBaseContext());

        drawerLayout = findViewById(R.id.drawerLayout);

        ImageView menuButton = findViewById(R.id.menuButton);
        ConstraintLayout eventsMenu = findViewById(R.id.eventsMenu);
        ConstraintLayout ordersMenu = findViewById(R.id.ordersMenu);
        ConstraintLayout logoutMenu = findViewById(R.id.logoutMenu);

        menuButton.setOnClickListener(view -> openDrawer(drawerLayout));

        eventsMenu.setOnClickListener(view -> recreate());

        ordersMenu.setOnClickListener(view -> redirectActivity(EventsActivity.this, OrdersActivity.class));

        logoutMenu.setOnClickListener(view -> redirectActivity(EventsActivity.this, LogInActivity.class));

        recyclerView = findViewById(R.id.eventRecycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EventAdapter(this, events, userId);
        recyclerView.setAdapter(adapter);

        EventService eventService = EventApiClient.getClient().create(EventService.class);
        Call<List<EventDto>> call = eventService.getEvents();

        call.enqueue(new Callback<List<EventDto>>() {
            @Override
            public void onResponse(Call<List<EventDto>> call, Response<List<EventDto>> response) {
                events = response.body();

                List<String> eventCategories = events.stream()
                        .map(event -> event.getEventCategory())
                        .distinct()
                        .collect(Collectors.toList());

                List<String> locations = events.stream()
                        .map(event -> event.getVenue().getLocation())
                        .distinct()
                        .collect(Collectors.toList());

                ImageButton filterButton = findViewById(R.id.filterEventButton);
                filterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FilterDialog dialogFragment = FilterDialog.newInstance(eventCategories, locations);
                        dialogFragment.setFilterSelectedListener(EventsActivity.this); // Setează listener-ul
                        dialogFragment.show(getSupportFragmentManager(), "CategoryFilterDialogFragment");
                    }
                });

                Log.d("TAG","Response = " + events);
                adapter.setEventList(events);

                EditText searchBar = findViewById(R.id.searchEventBar);
                searchBar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.filterEventsByName(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }

            @Override
            public void onFailure(Call<List<EventDto>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });

        Button resetFiltersButton = findViewById(R.id.resetFilterButton);
        resetFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();

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
    public void onEventsLoaded(List<EventDto> events) {
        this.events.clear();
        this.events.addAll(events);
        adapter.notifyDataSetChanged();

        Log.i("Events loaded", String.valueOf(events.size()));
    }

    @Override
    public void onEventsLoadFailed(String errorMessage, Context context) {
        Log.e("EventsLoadFailed", errorMessage);
        Toast.makeText(context, "Error.. " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnFilterSelected(String selectedCategory, String selectedLocation) {
        EventService eventService = EventApiClient.getClient().create(EventService.class);
        Call<List<EventDto>> call = eventService.getEventsByLocationAndCategory(selectedLocation, selectedCategory);
        call.enqueue(new Callback<List<EventDto>>() {
            @Override
            public void onResponse(Call<List<EventDto>> call, Response<List<EventDto>> response) {
                events = response.body();
                Log.d("TAG","Response = " + events);
                adapter.setEventList(events);
            }

            @Override
            public void onFailure(Call<List<EventDto>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }
}
