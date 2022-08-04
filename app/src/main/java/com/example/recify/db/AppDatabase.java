package com.example.recify.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.recify.db.Dao.RecipeDao;
import com.example.recify.db.Dao.RegisterDao;
import com.example.recify.entities.Recipe;
import com.example.recify.entities.Register;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Recipe.class, Register.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();
    public abstract RegisterDao registerDao();

    private static final int NUMBER_OF_THREADS = 1;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile AppDatabase instance;

    public static AppDatabase getDatabaseInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "app_database"
                    ).fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}

