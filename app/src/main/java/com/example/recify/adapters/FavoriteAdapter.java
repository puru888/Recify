package com.example.recify.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recify.R;
import com.example.recify.db.AppDatabase;
import com.example.recify.db.Dao.RecipeDao;
import com.example.recify.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    private List<Recipe> recipeList = new ArrayList<>();

    public FavoriteAdapter(Context context, int loginId) {
        RecipeDao recipeDao = AppDatabase.getDatabaseInstance(context).recipeDao();
        recipeDao.getAllRecipes(loginId).observe((LifecycleOwner) context, recipes -> {
            recipeList = recipes;
            notifyDataSetChanged();
        });
    }


    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType,parent,false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.bind(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.favorite_list_item;
    }
}
