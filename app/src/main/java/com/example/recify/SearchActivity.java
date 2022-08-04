package com.example.recify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.recify.adapters.SearchAdapter;
import com.example.recify.entities.SearchRecipes;
import com.example.recify.network.RecipeApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerView list = findViewById(R.id.activity_search_searchResult);
        list.setHasFixedSize(false);
        list.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        int loginId = intent.getIntExtra(HomeActivity.EXTRA_HOME_LOGIN_ID, -1);

        SearchAdapter searchAdapter = new SearchAdapter(loginId);
        list.setAdapter(searchAdapter);

        findViewById(R.id.activity_searchButton).setOnClickListener(view -> {
            EditText search = findViewById(R.id.activity_search_query);
            Call<SearchRecipes> searchRecipesCall = RecipeApi.service.getSearchRecipes(search.getText().toString());
            searchRecipesCall.enqueue(new Callback<SearchRecipes>() {
                @Override
                public void onResponse(Call<SearchRecipes> call, Response<SearchRecipes> response) {
                    searchAdapter.replace(response.body().getResults());
                }

                @Override
                public void onFailure(Call<SearchRecipes> call, Throwable t) {
                    Log.e("TAG", t.toString());
                }
            });
        });

    }
}