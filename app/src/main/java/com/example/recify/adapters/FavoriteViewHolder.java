package com.example.recify.adapters;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recify.R;
import com.example.recify.RecipeDetailActivity;
import com.example.recify.db.AppDatabase;
import com.example.recify.db.Dao.RecipeDao;
import com.example.recify.entities.Recipe;
import com.squareup.picasso.Picasso;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    private final ImageView image;
    private final TextView name;
    private Recipe recipe;
    public static String EXTRA_FAVORITE_RECIPE_ID = "favoriteRecipeId";

    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.favoriteListItem_image);
        name = itemView.findViewById(R.id.favoriteListItem_name);

        RecipeDao recipeDao = AppDatabase.getDatabaseInstance(itemView.getContext()).recipeDao();
        TextView hidden = itemView.findViewById(R.id.hidden);

        itemView.findViewById(R.id.favoriteListItem_delete).setOnClickListener(view -> {
            AppDatabase.databaseWriteExecutor.execute(() -> {
                recipeDao.delete(recipe);
            });
        });
        itemView.setOnClickListener(view -> {
            Intent intent = new Intent(itemView.getContext(), RecipeDetailActivity.class);
            intent.putExtra(EXTRA_FAVORITE_RECIPE_ID, recipe.getRecipeId());
            itemView.getContext().startActivity(intent);
        });
    }

    public void bind(Recipe recipe) {
        this.recipe = recipe;
        Picasso.get().load(recipe.getImage()).into(image);
        name.setText(recipe.getName());
    }

}
