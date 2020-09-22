package com.example.practica01;


import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;


import android.view.MenuItem;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;


public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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








}
