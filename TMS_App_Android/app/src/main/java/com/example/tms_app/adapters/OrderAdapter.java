package com.example.tms_app.adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tms_app.R;
import com.example.tms_app.activities.OrdersActivity;
import com.example.tms_app.api_clients.OrdersApiClient;
import com.example.tms_app.callbacks.OrderDeleteCallback;
import com.example.tms_app.callbacks.OrderGetCallback;
import com.example.tms_app.callbacks.OrderPatchCallback;
import com.example.tms_app.models.Order;
import com.example.tms_app.models.TicketCategory;
import com.example.tms_app.models.dto.EventDto;
import com.example.tms_app.models.dto.TicketCategoryDto;
import com.example.tms_app.models.dto.order.OrderDto;
import com.example.tms_app.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> implements OrderPatchCallback, OrderDeleteCallback {
    private List<OrderDto> orders;
    private Context context;
    private String userId;

    public interface OnReloadClickListener {
        void onReloadClick();
    }

    private OnReloadClickListener reloadClickListener;

    public void setOnReloadClickListener(OnReloadClickListener listener) {
        this.reloadClickListener = listener;
    }

    public OrderAdapter(Context context, List<OrderDto> orders, String userId) {
        this.context = context;
        this.orders = orders;
        this.userId = userId;
    }

    public void setOrderList(List<OrderDto> orderList) {
        try {
            if(orderList != null) {
                this.orders.clear();
                this.orders.addAll(orderList);
                notifyDataSetChanged();
            }
        }
        catch (Exception ex) {
            Log.i("xd", ex.getMessage());
        }
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_card, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderDto order = orders.get(position);

        holder.updateButton.setOnClickListener(v -> {
            String orderId = order.getOrderId().toString();
            String eventName = order.getEvent().getEventName();
            String ticketCategory = holder.ticketCategory.getSelectedItem().toString();
            Integer numberOfTickets = Integer.parseInt(holder.numberOfTickets.getText().toString());
            OrdersApiClient.patchOrder(this, context, eventName, ticketCategory,
                    numberOfTickets, userId, orderId);
        });

        holder.deleteButton.setOnClickListener(v -> {
            String orderId = order.getOrderId().toString();
            OrdersApiClient.deleteOrder(this, context, userId, orderId);
            if(reloadClickListener != null) {
                reloadClickListener.onReloadClick();
            }
        });

        Spinner ticketCategory = holder.ticketCategory;
        List<TicketCategoryDto> ticketCategories = order.getEvent().getTicketCategories();
        ArrayList<String> spinnerArray = new ArrayList<>();
        for(TicketCategoryDto ticketCategoryDto: ticketCategories) {
            spinnerArray.add(ticketCategoryDto.getDescription());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerArray);
        ticketCategory.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(order.getTicketCategory().getDescription());
        ticketCategory.setSelection(spinnerPosition);

        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    @Override
    public void onOrdersUpdated(OrderDto order) {
        Toast.makeText(context, "Order updated successfully!!", Toast.LENGTH_SHORT).show();
        OrderService orderService = OrdersApiClient.getClient().create(OrderService.class);
        Call<List<OrderDto>> call = orderService.getOrdersByUserId(userId);

        call.enqueue(new Callback<List<OrderDto>>() {
            @Override
            public void onResponse(Call<List<OrderDto>> call, Response<List<OrderDto>> response) {
                if(response.isSuccessful()) {
                    Log.d("TAG","Response = " + response.body());
                    setOrderList(response.body());
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

    @Override
    public void onOrdersUpdatedFailed(String errorMessage, Context context) {
        Toast.makeText(context, "Error.. " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrderDeleted(OrderDto order) {
        Toast.makeText(context, "Order deleted successfully!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrderDeletedFailed(String errorMessage, Context context) {
        Toast.makeText(context, "Error.. " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView eventName, orderedAt, totalPrice;
        private final EditText numberOfTickets;
        private final Spinner ticketCategory;
        private final Button updateButton;
        private final Button deleteButton;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventNameOrder);
            orderedAt = itemView.findViewById(R.id.orderDateOrder);
            totalPrice = itemView.findViewById(R.id.totalPriceOrder);
            ticketCategory = itemView.findViewById(R.id.ticketCategoriesOrderInput);
            numberOfTickets = itemView.findViewById(R.id.numberOfTicketsOrderInput);
            updateButton = itemView.findViewById(R.id.updateOrderButton);
            deleteButton = itemView.findViewById(R.id.orderDeleteButton);
        }

        private void bind(OrderDto order) {
            eventName.setText(order.getEvent().getEventName());
            orderedAt.setText(order.getOrderedAt());
            totalPrice.setText(order.getTotalPrice().toString());
            numberOfTickets.setText(order.getNumberOfTickets().toString());
        }
    }
}
