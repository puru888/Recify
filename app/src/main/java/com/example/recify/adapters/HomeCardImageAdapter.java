package com.example.recify.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recify.R;
import com.example.recify.entities.Results;

import java.util.ArrayList;
import java.util.List;

public class HomeCardImageAdapter extends RecyclerView.Adapter<HomeCardImageViewHolder> {

    private List<Results> results = new ArrayList<>();
    private int id = -1;

    public void replace(List<Results> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public HomeCardImageAdapter(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public HomeCardImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new HomeCardImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCardImageViewHolder holder, int position) {
        int recipeId = results.get(position).getId();
        holder.bind(results.get(position), recipeId,id);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.recipie_card_view;
    }
}
