package com.example.practica01;


import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
<<<<<<< HEAD
<<<<<<< HEAD

import android.view.MenuItem;
=======
=======
>>>>>>> develop
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
<<<<<<< HEAD
import androidx.appcompat.widget.Toolbar;


public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
=======
=======
>>>>>>> develop


public class HomeActivity extends AppCompatActivity {

<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
<<<<<<< HEAD
<<<<<<< HEAD
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home Activity");
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.registrar:
                Intent i1 = new Intent(this,AltaClienteActivity.class);
                startActivity(i1);
                break;
            case R.id.crearItem:
                Intent i2 = new Intent(this,AltaPlatoActivity.class);
                startActivity(i2);
                break;
            case R.id.listarItems:
                Intent i3 = new Intent(this,ListarPlatosActivity.class);
                startActivity(i3);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

=======
=======
>>>>>>> develop
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


<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop
}
