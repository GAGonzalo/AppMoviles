package com.example.practica01;


import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         Button altaCliente = findViewById(R.id.registrarPersona32);
         Button altaPlato = findViewById(R.id.crearPlato);
         Button listarPlatos = findViewById(R.id.listarPlatos);


        altaCliente.setOnClickListener((View v)->{
            Intent intent = new Intent(this,AltaClienteActivity.class);
            startActivity(intent);
        });
        altaPlato.setOnClickListener((View v)->{

           Intent intent2 = new Intent(this,AltaPlatoActivity.class);
            startActivity(intent2);
        });
        listarPlatos.setOnClickListener((View v)->{
            Intent intent = new Intent(this,ListarPlatosActivity.class);
            startActivity(intent);
        });

    }


}
