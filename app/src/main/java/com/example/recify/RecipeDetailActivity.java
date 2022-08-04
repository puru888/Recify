package com.example.recify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recify.adapters.FavoriteViewHolder;
import com.example.recify.adapters.HomeCardImageViewHolder;
import com.example.recify.adapters.RecipeStepsAdapter;
import com.example.recify.adapters.SearchViewHolder;
import com.example.recify.db.AppDatabase;
import com.example.recify.db.Dao.RecipeDao;
import com.example.recify.entities.Recipe;
import com.example.recify.entities.RecipeDetails;
import com.example.recify.entities.RecipeInstructions;
import com.example.recify.entities.SimilarRecipes;
import com.example.recify.network.RecipeApi;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailActivity extends AppCompatActivity {
    RecipeDetails recipeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        int id = -1;
        int loginId = 0;
        if (intent.hasExtra(HomeCardImageViewHolder.EXTRA_LOGIN_ID))
            loginId = intent.getIntExtra(HomeCardImageViewHolder.EXTRA_LOGIN_ID, -1);
        else if (intent.hasExtra(SearchViewHolder.EXTRA_SEARCH_LOGIN_ID))
            loginId = intent.getIntExtra(SearchViewHolder.EXTRA_SEARCH_LOGIN_ID, -1);

        Log.e("TAG",String.valueOf(loginId) );

        if (intent.hasExtra(HomeCardImageViewHolder.EXTRA_RECIPE_ID)) {
            id = intent.getIntExtra(HomeCardImageViewHolder.EXTRA_RECIPE_ID, -1);
        } else if (intent.hasExtra(FavoriteViewHolder.EXTRA_FAVORITE_RECIPE_ID)) {
            id = intent.getIntExtra(FavoriteViewHolder.EXTRA_FAVORITE_RECIPE_ID, -1);
        } else if (intent.hasExtra(SearchViewHolder.EXTRA_SEARCH_RECIPE_ID)) {
            id = intent.getIntExtra(SearchViewHolder.EXTRA_SEARCH_RECIPE_ID, -1);
        }

        ImageView recipeDetailImg = findViewById(R.id.recipe_detail_image);
        TextView title = findViewById(R.id.recipe_detail_title);

        retrofit2.Call<RecipeDetails> recipeDetailsCall = RecipeApi.service.getRecipeDetails(id);
        retrofit2.Call<List<SimilarRecipes>> similarRecipesCall = RecipeApi.service.getSimilarRecipes(id);
        retrofit2.Call<List<RecipeInstructions>> recipeInstructionsCall = RecipeApi.service.getRecipeInstructions(id);

        RecyclerView recyclerView = findViewById(R.id.activity_recipe_recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter();
        recyclerView.setAdapter(recipeStepsAdapter);

        Log.e("TAG", String.valueOf(id));

        recipeDetailsCall.enqueue(new Callback<RecipeDetails>() {
            @Override
            public void onResponse(retrofit2.Call<RecipeDetails> call, Response<RecipeDetails> response) {
                recipeDetails = response.body();
                Picasso.get().load(recipeDetails.getImage()).into(recipeDetailImg);
                title.setText(recipeDetails.getTitle());
            }

            @Override
            public void onFailure(retrofit2.Call<RecipeDetails> call, Throwable t) {

            }
        });

        recipeInstructionsCall.enqueue(new Callback<List<RecipeInstructions>>() {
            @Override
            public void onResponse(retrofit2.Call<List<RecipeInstructions>> call, Response<List<RecipeInstructions>> response) {
                recipeStepsAdapter.replace(response.body().get(0).getSteps());
            }

            @Override
            public void onFailure(retrofit2.Call<List<RecipeInstructions>> call, Throwable t) {

            }
        });
        int finalLoginId = loginId;
        findViewById(R.id.recipe_detail_favourite).setOnClickListener(view -> {
            RecipeDao recipeDao = AppDatabase.getDatabaseInstance(this).recipeDao();
            AppDatabase.databaseWriteExecutor.execute(() -> {
                Recipe recipe = new Recipe(recipeDetails.getId(), finalLoginId, recipeDetails.getTitle(), recipeDetails.getImage());
                recipeDao.insert(recipe);
            });
            Toast.makeText(this, "added to favorites", Toast.LENGTH_SHORT).show();
        });

    }
}