package com.example.recify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.recify.adapters.FavoriteAdapter;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        RecyclerView list = findViewById(R.id.activity_favorite_recycler_view);
        list.setHasFixedSize(false);
        list.setLayoutManager(new LinearLayoutManager(this));

        Intent getIntent = getIntent();
        int logInId = getIntent.getIntExtra(HomeActivity.EXTRA_HOME_LOGIN_ID,-1);

        FavoriteAdapter adapter = new FavoriteAdapter(this,logInId);
        list.setAdapter(adapter);
    }
}