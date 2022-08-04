package com.example.recify.entities;

import java.util.List;

public class SearchAllFood {
    private List<SearchResults> searchResults;

    public List<SearchResults> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResults> searchResults) {
        this.searchResults = searchResults;
    }
}
