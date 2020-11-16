package com.example.practica01;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HOME_ACTIVITY_AUTH";

    private Toolbar toolbar;
    private Switch switchDB;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

        switchDB = findViewById(R.id.switchDB);
        toolbar = findViewById(R.id.homeToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        signInAnonymously();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.d("[FCM - TOKEN]","- ERROR");
                        return;
                    }

                    // FCM token
                    String token = task.getResult();
                    // Imprimirlo en un toast y en logs
                    Log.d("[FCM - TOKEN]", token);
                });


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

    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Exito
                        Log.d(TAG, "signInAnonymously:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        // Error
                        Log.w(TAG, "signInAnonymously:failure", task.getException());

                    }
                });
    }



}
