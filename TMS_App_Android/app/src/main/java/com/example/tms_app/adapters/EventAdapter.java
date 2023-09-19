package com.example.tms_app.adapters;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tms_app.R;
import com.example.tms_app.api_clients.EventApiClient;
import com.example.tms_app.api_clients.OrdersApiClient;
import com.example.tms_app.api_clients.UserApiClient;
import com.example.tms_app.callbacks.OrderPostCallback;
import com.example.tms_app.models.dto.EventDto;
import com.example.tms_app.models.dto.TicketCategoryDto;
import com.example.tms_app.models.dto.order.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> implements OrderPostCallback {
    private List<EventDto> eventList;
    private List<EventDto> originalEventList;
    private Context context;
    private String userId;
    public EventAdapter(Context context, List<EventDto> eventList, String userId){
        this.context = context;
        this.eventList = eventList;
        this.userId = userId;
    }

    public void setEventList(List<EventDto> eventList) {
        try{
            if(eventList != null){
                this.eventList = eventList;
                this.originalEventList = new ArrayList<>(eventList);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(context, "No events available!!", Toast.LENGTH_SHORT).show();
            }
        } catch(Exception ex){
            Log.i("xd", ex.getMessage());
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventDto event = eventList.get(position);

        Glide.with(context).load(eventList.get(position).getImageUrl()).apply(RequestOptions.centerCropTransform()).into(holder.eventImage);

        holder.addOrder.setOnClickListener(v -> {
            String eventName = holder.eventName.getText().toString();
            String ticketCategory = holder.ticketCategories.getSelectedItem().toString();

            Integer numberOfTickets = Integer.parseInt(holder.numberOfTickets.getText().toString());
            OrdersApiClient.postOrder(this, context, eventName, ticketCategory, numberOfTickets, userId);
        });

        Spinner ticketCategories = holder.ticketCategories;
        List<TicketCategoryDto> ticketCategoriesList = event.getTicketCategories();
        ArrayList<String> spinnerArray = new ArrayList<>();
        for (TicketCategoryDto ticketCategory : ticketCategoriesList) {
            spinnerArray.add(ticketCategory.getDescription());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ticketCategories.setAdapter(adapter);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @Override
    public void onOrderAdded(OrderDto orders) {
        Toast.makeText(context, "Order added successfully!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrderAddedFailed(String errorMessage, Context context) {
        Toast.makeText(context, "Error.. " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventLocation, eventDescription;
        ImageView eventImage;
        Spinner ticketCategories;
        Button addOrder;
        EditText numberOfTickets;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventName);
            eventLocation = itemView.findViewById(R.id.eventLocation);
            eventDescription = itemView.findViewById(R.id.eventDescription);
            ticketCategories = itemView.findViewById(R.id.ticketCategories);
            eventImage = itemView.findViewById(R.id.eventImage);
            numberOfTickets = itemView.findViewById(R.id.numberOfTickets);
            addOrder = itemView.findViewById(R.id.addOrderButton);
        }

        private void bind(EventDto event) {
            eventName.setText(event.getEventName());
            eventLocation.setText(event.getVenue().getLocation());
            eventDescription.setText(event.getEventDescription());
        }
    }

    public void filterEventsByName(String name) {
        List<EventDto> filteredList = new ArrayList<>();
        for (EventDto event : originalEventList) {
            if (event.getEventName().toLowerCase().contains(name.toLowerCase())) {
                filteredList.add(event);
            }
        }
        eventList.clear();
        eventList.addAll(filteredList);
        notifyDataSetChanged();
    }
}


