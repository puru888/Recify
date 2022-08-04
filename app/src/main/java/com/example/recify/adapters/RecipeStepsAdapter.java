package com.example.recify.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recify.R;
import com.example.recify.entities.Steps;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsViewHolder> {
    private List<Steps> steps = new ArrayList<>();
    public void replace(List<Steps> steps){
        this.steps = steps;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecipeStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType,parent,false);
        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsViewHolder holder, int position) {
        holder.bind(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.recipe_steps;
    }
}
