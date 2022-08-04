package com.example.recify.db.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recify.entities.Register;

@Dao
public interface RegisterDao {

    @Query("select exists(select * from register where email = :email)")
    boolean exist(String email);

    @Query("select * from register where email = :email and password = :password")
    Register validate(String email, String password);

    @Insert
    void insert(Register... registers);

}
