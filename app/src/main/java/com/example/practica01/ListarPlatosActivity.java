package com.example.practica01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica01.adapters.PlatoAdapter;
import com.example.practica01.model.Plato;
import com.example.practica01.repository.PlatoRepository;
import com.example.practica01.service.PlatoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarPlatosActivity extends AppCompatActivity implements PlatoRepository.OnResultCallback<Plato> {

    private List<Plato> platos ;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;
    private Boolean usar_api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_platos);

        usar_api=getIntent().getExtras().getBoolean("Usar Api");

        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.listarPedidosToolbar);

        this.platos = new ArrayList<>();
        configRecyclerView();

        if(usar_api){
            obtenerPlatosApi();
        }
        else{
            obtenerPlatos();
        }

        toolbar.setTitle("Listar Pedidos");
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listar_pedidos_toolbar,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nuevo_pedido_item:
                Intent intent = new Intent(this,PedidoActivity.class);
                intent.putExtra("Usar Api", usar_api);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void obtenerPlatos() {


       PlatoRepository platoRepository = new PlatoRepository(this.getApplication(),this);
       platoRepository.findAllPlatos();

    }




    private void obtenerPlatosApi(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PlatoService platoService = retrofit.create(PlatoService.class);

        Call<List<Plato>> callPlatos = platoService.getPlatoList();
        callPlatos.enqueue(
                new Callback<List<Plato>>() {
                    List<Plato> asd;
                    @Override
                    public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Platos obtenidos");
                            platos=response.body();
                            configRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Plato>> call, Throwable t) {
                        Log.d("DEBUG", "Error obteniendo los platos");
                    }
                }
        );

    }

    private void configRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new PlatoAdapter(platos, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResult(List result) {
        platos = result;
        configRecyclerView();
    }

    @Override
    public void onResult(Plato result) {

    }
}
