package com.example.recify.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recify.R;
import com.example.recify.entities.Steps;

public class RecipeStepsViewHolder extends RecyclerView.ViewHolder {

    private final TextView step;
    private Steps steps;

    public RecipeStepsViewHolder(@NonNull View itemView) {
        super(itemView);

        step = itemView.findViewById(R.id.recipe_steps);
    }

    public void bind(Steps steps) {
        this.steps = steps;
        step.setText(steps.getNumber() + "." + steps.getStep());
    }
}
