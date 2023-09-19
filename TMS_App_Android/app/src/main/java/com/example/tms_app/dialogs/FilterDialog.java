package com.example.tms_app.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tms_app.R;

import java.util.ArrayList;
import java.util.List;

public class FilterDialog extends DialogFragment {
    public interface OnFilterSelectedListener {
        void OnFilterSelected(String selectedCategory, String selectedLocation);
    }

    private OnFilterSelectedListener filterSelectedListener;
    private List<String> categories;
    private List<String> locations;

    public FilterDialog(){
    }

    public void setFilterSelectedListener(OnFilterSelectedListener listener) {
        filterSelectedListener = listener;
    }

    public static FilterDialog newInstance(List<String> categories, List<String> locations) {
        FilterDialog fragment = new FilterDialog();
        fragment.categories = categories;
        fragment.locations = locations;
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFilterSelectedListener) {
            filterSelectedListener = (OnFilterSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFilterSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.filter_fragment, container, false);

        Spinner categorySpinner = dialogView.findViewById(R.id.categoryFilter);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        Spinner locationSpinner = dialogView.findViewById(R.id.locationFilter);
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, locations);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);

        Button confirmButton = dialogView.findViewById(R.id.submitFilterButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCategory = (String) categorySpinner.getSelectedItem();
                String selectedLocation = (String) locationSpinner.getSelectedItem();
                if (selectedCategory != null && selectedLocation != null && filterSelectedListener != null) {
                    filterSelectedListener.OnFilterSelected(selectedCategory, selectedLocation); // Transmite categoria selectată către activitate
                }
                dismiss();
            }
        });

        return dialogView;
    }
}
