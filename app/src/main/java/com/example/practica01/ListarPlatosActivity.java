package com.example.practica01;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica01.adapters.PlatoAdapter;
import com.example.practica01.model.Plato;

import java.util.ArrayList;
import java.util.List;

public class ListarPlatosActivity extends AppCompatActivity {

    private List<Plato> platos ;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_platos);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        crearPlato();
        mAdapter = new PlatoAdapter(platos,this);
        recyclerView.setAdapter(mAdapter);
        System.out.println(platos);
    }

    private void crearPlato() {
        platos = new ArrayList<>();
        platos.add(new Plato("Titulo 1","Descripcion 1",Float.valueOf(1),Float.valueOf(1)));
        platos.add(new Plato("Titulo 2","Descripcion 2",Float.valueOf(2),Float.valueOf(2)));
        platos.add(new Plato("Titulo 2","Descripcion 2",Float.valueOf(2),Float.valueOf(2)));
        platos.add(new Plato("Titulo 2","Descripcion 2",Float.valueOf(2),Float.valueOf(2)));
        platos.add(new Plato("Titulo 2","Descripcion 2",Float.valueOf(2),Float.valueOf(2)));
        platos.add(new Plato("Titulo 2","Descripcion 2",Float.valueOf(2),Float.valueOf(2)));
    }
}
