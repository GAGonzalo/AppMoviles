package com.example.practica01;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Switch switchDB;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        switchDB = findViewById(R.id.switchDB);

        toolbar = findViewById(R.id.homeToolbar);
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
                Intent i1 = new Intent(this, AltaClienteActivity.class);
                startActivity(i1);
                break;
            case R.id.crearItem:
                Intent i2 = new Intent(this, AltaPlatoActivity.class);
                i2.putExtra("Usar Api", switchDB.isChecked());
                startActivity(i2);
                switchDB.setVisibility(View.GONE);
                break;
            case R.id.listarItems:
                Intent i3 = new Intent(this,ListarPlatosActivity.class);
                i3.putExtra("Listar","Listar Pedidos");
                i3.putExtra("Usar Api", switchDB.isChecked());
                startActivity(i3);
                switchDB.setVisibility(View.GONE);
                break;

        }

        return super.onOptionsItemSelected(item);
    }








}
