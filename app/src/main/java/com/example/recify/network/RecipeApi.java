package com.example.recify.network;

import com.example.recify.entities.RandomFoodTrivia;
import com.example.recify.entities.RecipeDetails;
import com.example.recify.entities.RecipeInstructions;
import com.example.recify.entities.SearchAllFood;
import com.example.recify.entities.SearchRecipes;
import com.example.recify.entities.SimilarRecipes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeApi {
    String apiKey = "?apiKey=8ec169c009654ccba8e2b35f98d2079b";
    String BASE_URL = "https://api.spoonacular.com/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RecipeApi service = retrofit.create(RecipeApi.class);

    @GET("food/trivia/random"+apiKey)
    Call<RandomFoodTrivia> getFoodTrivia();

    @GET("food/search"+apiKey)
    Call<SearchAllFood> getSearchResult();

    @GET("recipes/{id}/similar"+apiKey)
    Call<List<SimilarRecipes>> getSimilarRecipes(@Path("id") int id);

    @GET("recipes/{id}/information"+apiKey)
    Call<RecipeDetails> getRecipeDetails(@Path("id") int id);

    @GET("recipes/{id}/analyzedInstructions"+apiKey)
    Call<List<RecipeInstructions>> getRecipeInstructions(@Path("id") int id);

    @GET("recipes/complexSearch"+apiKey)
    Call<SearchRecipes> getSearchRecipes(@Query(value = "query", encoded = true) String query);

    @GET("recipes/complexSearch"+apiKey+"&query=pasta&number=6")
    Call<SearchRecipes> getSearchPasta();

}
