package com.example.practica01.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.practica01.model.Plato;

import java.util.List;

@Dao
public interface PlatoDAO {
    @Insert
    void insert(Plato plato);

    @Delete
    void delete(Plato plato);

    @Update
    void update(Plato plato);

    @Query("SELECT * FROM plato WHERE id = :id LIMIT 1")
    Plato getById(String id);

    @Query("SELECT * FROM plato")
    List<Plato> getAll();

}
