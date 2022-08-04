package com.example.recify.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("id")
    @ColumnInfo
    private int recipeId;
    @ColumnInfo
    private int loginId;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String image;

    public Recipe(int recipeId, int loginId, String name, String image) {
        this.recipeId = recipeId;
        this.loginId = loginId;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
