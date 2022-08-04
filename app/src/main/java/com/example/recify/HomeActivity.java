package com.example.recify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recify.adapters.HomeCardImageAdapter;
import com.example.recify.entities.RandomFoodTrivia;
import com.example.recify.entities.SearchAllFood;
import com.example.recify.entities.SearchRecipes;
import com.example.recify.network.RecipeApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    public static String EXTRA_HOME_LOGIN_ID = "homeLoginId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_navigation);
        TextView foodTrivia = findViewById(R.id.home_food_trivia);


        Call<SearchAllFood> search = RecipeApi.service.getSearchResult();
        Call<SearchRecipes> searchPasta = RecipeApi.service.getSearchPasta();

        RecyclerView foodList = findViewById(R.id.home_activity_recycler_view);
        foodList.setHasFixedSize(false);
        foodList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        Intent getIntent = getIntent();
        HomeCardImageAdapter homeCardImageAdapter = new HomeCardImageAdapter(getIntent.getIntExtra(LoginActivity.EXTRA_LOGIN_ID, -1));
        foodList.setAdapter(homeCardImageAdapter);


        search.enqueue(new Callback<SearchAllFood>() {
            @Override
            public void onResponse(Call<SearchAllFood> call, Response<SearchAllFood> response) {
                homeCardImageAdapter.replace(response.body().getSearchResults().get(0).getResults());
            }

            @Override
            public void onFailure(Call<SearchAllFood> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });

        Call<RandomFoodTrivia> randomFoodTriviaCall = RecipeApi.service.getFoodTrivia();
        randomFoodTriviaCall.enqueue(new Callback<RandomFoodTrivia>() {
            @Override
            public void onResponse(Call<RandomFoodTrivia> call, Response<RandomFoodTrivia> response) {
                foodTrivia.setText("Food trivia: " + response.body().getText());
            }

            @Override
            public void onFailure(Call<RandomFoodTrivia> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent = null;
            switch (item.getItemId()) {
                case R.id.menu_home:
                    intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.menu_search:
                    intent = new Intent(this, SearchActivity.class);
                    intent.putExtra(EXTRA_HOME_LOGIN_ID, getIntent.getIntExtra(LoginActivity.EXTRA_LOGIN_ID, -1));
                    startActivity(intent);
                    break;
                case R.id.menu_favourite:
                    intent = new Intent(this, FavoriteActivity.class);
                    intent.putExtra(EXTRA_HOME_LOGIN_ID, getIntent.getIntExtra(LoginActivity.EXTRA_LOGIN_ID, -1));
                    startActivity(intent);
                    break;
            }
            return true;
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View confirmLayout = getLayoutInflater().inflate(R.layout.confirm_dialog, null);
                Button confirm = confirmLayout.findViewById(R.id.comfirm_dialog_confirm);
                Button cancel = confirmLayout.findViewById(R.id.confirm_dialog_cancel);
                builder.setView(confirmLayout);

                AlertDialog dialog = builder.create();
                confirm.setOnClickListener(view -> {
                    Intent logoutIntent = new Intent(this, LoginActivity.class);
                    startActivity(logoutIntent);
                    finish();
                });
                cancel.setOnClickListener(view -> {
                    dialog.cancel();
                });
                dialog.show();
                break;
        }
        return true;
    }
}