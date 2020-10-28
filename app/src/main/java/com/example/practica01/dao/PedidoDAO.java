package com.example.practica01.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.practica01.model.Pedido;

import java.util.List;

@Dao
public interface PedidoDAO {
    @Insert
    void insert(Pedido pedido);

    @Delete
    void delete(Pedido pedido);

    @Update
    void update(Pedido pedido);

    @Query("SELECT * FROM pedido WHERE id = :id LIMIT 1")
    Pedido getById(String id);

    @Query("SELECT * FROM pedido")
    List<Pedido> getAll();
}
