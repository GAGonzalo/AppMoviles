package com.example.practica01;

import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practica01.model.Plato;
import com.example.practica01.repository.PlatoRepository;
import com.example.practica01.service.PlatoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AltaPlatoActivity extends AppCompatActivity {

    private EditText tituloPlato;
    private EditText descripcionPlato;
    private EditText precioPlato;
    private EditText caloriasPlato;
    private Button guardarPlatoBtn;
    private Boolean usar_api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_plato);

        usar_api=getIntent().getExtras().getBoolean("Usar Api");

        PlatoRepository platoRepository = new PlatoRepository(this.getApplication(),null);

        this.tituloPlato = findViewById(R.id.tituloPlato);
        this.descripcionPlato = findViewById(R.id.descripcionPlato);
        this.precioPlato = findViewById(R.id.precioPlato);
        this.caloriasPlato = findViewById(R.id.caloriasPlato);
        this.guardarPlatoBtn = findViewById(R.id.guardarPlato);

        this.guardarPlatoBtn.setOnClickListener(v -> {

            String titulo = tituloPlato.getText().toString();
            String descripcion = descripcionPlato.getText().toString();
            Double precio = Double.valueOf(precioPlato.getText().toString());
            Integer calorias = Integer.valueOf(caloriasPlato.getText().toString());;

            Plato plato = new Plato(titulo,descripcion,precio,calorias);

            if(usar_api){
                guardarPlatoApi(plato);
            }
            else {
                platoRepository.insertPlato(plato);
            }


            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            Toast.makeText(this, "Plato Creado", Toast.LENGTH_LONG).show();

        });

    }

    private void guardarPlatoApi(Plato plato){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PlatoService platoService = retrofit.create(PlatoService.class);

        Call<Plato> call = platoService.createPlato(plato);

        call.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                if (!response.isSuccessful()){
                    Log.d("DEBUG", "Plato creado");
                }

            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Log.d("DEBUG", "Error en la creacion del plato");
            }
        });
    }


}
