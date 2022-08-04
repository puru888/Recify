package com.example.recify.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recify.R;
import com.example.recify.entities.SearchRecipes;
import com.example.recify.entities.SearchRecipesResult;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private List<SearchRecipesResult> searchRecipesResultList = new ArrayList<>();
    private int logInId;

    public SearchAdapter(int logInId) {
        this.logInId = logInId;
    }

    public void replace(List<SearchRecipesResult> searchRecipesResultList){
        this.searchRecipesResultList = searchRecipesResultList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bind(searchRecipesResultList.get(position),logInId);
    }

    @Override
    public int getItemCount() {
        return searchRecipesResultList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.search_list_item;
    }
}
