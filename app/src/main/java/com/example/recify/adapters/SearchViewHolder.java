package com.example.recify.adapters;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recify.R;
import com.example.recify.RecipeDetailActivity;
import com.example.recify.entities.SearchRecipesResult;
import com.squareup.picasso.Picasso;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    private final ImageView image;
    private final TextView name;
    private SearchRecipesResult searchRecipesResult;
    private int loginId;
    public static final String EXTRA_SEARCH_RECIPE_ID = "searchRecipeId";
    public static final String EXTRA_SEARCH_LOGIN_ID = "searchLoginId";

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.searchListItem_image);
        name = itemView.findViewById(R.id.searchListItem_name);

        itemView.setOnClickListener(view -> {
            Intent intent = new Intent(itemView.getContext(), RecipeDetailActivity.class)
                    .putExtra(EXTRA_SEARCH_RECIPE_ID, searchRecipesResult.getId())
                    .putExtra(EXTRA_SEARCH_LOGIN_ID, loginId);
            itemView.getContext().startActivity(intent);
        });
    }

    public void bind(SearchRecipesResult searchRecipesResult, int loginId) {
        this.searchRecipesResult = searchRecipesResult;
        this.loginId = loginId;
        Picasso.get().load(searchRecipesResult.getImage()).into(image);
        name.setText(searchRecipesResult.getTitle());
    }
}
