package com.example.recify.entities;

import java.util.List;

public class SearchRecipes {
    private List<SearchRecipesResult> results;

    public List<SearchRecipesResult> getResults() {
        return results;
    }

    public void setResults(List<SearchRecipesResult> results) {
        this.results = results;
    }
}
