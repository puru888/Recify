package com.example.recify.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recify.R;
import com.example.recify.RecipeDetailActivity;
import com.example.recify.entities.Results;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeCardImageViewHolder extends RecyclerView.ViewHolder {
    private final ImageView cardImage;
    private final TextView cardTitle;
    private Results results;
    private int recipeId;
    private int loginId;
    public static final String EXTRA_RECIPE_ID = "recipeId";
    public static final String EXTRA_LOGIN_ID = "loginId";

    public HomeCardImageViewHolder(@NonNull View itemView) {
        super(itemView);
        cardImage = itemView.findViewById(R.id.card_image);
        cardTitle = itemView.findViewById(R.id.card_image_title);

        itemView.setOnClickListener(view -> {
            Intent intent = new Intent(itemView.getContext(),RecipeDetailActivity.class);
            intent.putExtra(EXTRA_RECIPE_ID,recipeId);
            intent.putExtra(EXTRA_LOGIN_ID,loginId);
            itemView.getContext().startActivity(intent);
        });
    }

    public void bind(Results results, int recipeId, int loginId) {
        this.recipeId = recipeId;
        this.loginId = loginId;
        this.results = results;
        Picasso.get().load(results.getImage()).into(cardImage);
        cardTitle.setText(results.getName());
    }
}
