package com.example.practica01.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.practica01.model.Pedido;
import com.example.practica01.model.Plato;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Plato.class, Pedido.class}, version = 1)
@TypeConverters({Pedido.PlatosConverter.class})
public abstract class AppDB extends RoomDatabase {
    private static final String DB_NAME="app_db";
    private static final int NUMBER_OF_THREADS = 1;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static AppDB INSTANCE;
    public static AppDB getInstance(final Context context) {
        if(INSTANCE ==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDB.class,DB_NAME).build();
        }
        return INSTANCE;
    }

    public abstract PlatoDAO platoDAO();

    public abstract PedidoDAO pedidoDAO();


}
