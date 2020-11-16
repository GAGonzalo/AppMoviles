package com.example.practica01;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.practica01.model.Plato;
import com.example.practica01.repository.PlatoRepository;
import com.example.practica01.service.PlatoService;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AltaPlatoActivity extends AppCompatActivity implements PlatoRepository.OnResultCallback<Plato> {

    private static final String TAG = "ALTA_PLATO_ACTIVITY";
    private EditText tituloPlato;
    private EditText descripcionPlato;
    private EditText precioPlato;
    private EditText caloriasPlato;
    private Button guardarPlatoBtn;
    private Button agregarFotoButton;
    private Boolean usar_api;
    private FirebaseStorage storage;
    private byte[] image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_plato);

        usar_api=getIntent().getExtras().getBoolean("Usar Api");

        PlatoRepository platoRepository = new PlatoRepository(this.getApplication(),this);

        this.tituloPlato = findViewById(R.id.tituloPlato);
        this.descripcionPlato = findViewById(R.id.descripcionPlato);
        this.precioPlato = findViewById(R.id.precioPlato);
        this.caloriasPlato = findViewById(R.id.caloriasPlato);
        this.guardarPlatoBtn = findViewById(R.id.guardarPlato);
        this.agregarFotoButton = findViewById(R.id.agregarFoto);
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
        this.agregarFotoButton.setOnClickListener(v->{
            crearDialog();
        });

        storage = FirebaseStorage.getInstance();


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

    static final int CAMARA_REQUEST = 1;
    static final int GALERIA_REQUEST = 2;

    private void lanzarCamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, CAMARA_REQUEST);
    }

    private void abrirGaleria() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALERIA_REQUEST);
        } else {
            Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(galeriaIntent, GALERIA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GALERIA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galeriaIntent, GALERIA_REQUEST);
            } else {
                Log.d(TAG, "El pedido de permiso fue cancelado");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALERIA_REQUEST && resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap imageBitmap = BitmapFactory.decodeFile(picturePath);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            image = baos.toByteArray();

        }

        if(requestCode==CAMARA_REQUEST && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            image = baos.toByteArray(); // Imagen en arreglo de bytes
        }
    }

    private void crearDialog(){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Que desea utilizar").setItems(R.array.exercises, (dialog, which) -> {
            switch (which){
                case 0 :
                    lanzarCamara();
                    break;
                case 1 :
                    abrirGaleria();
                    break;
            }
        });

        builder.create().show();

    }

    private void subirFoto(byte[] image, Long resultId) {
        // Creamos una referencia a nuestro Storage
        StorageReference storageRef = storage.getReference();

        // Creamos una referencia a 'images/plato_id.jpg'
        StorageReference platosImagesRef = storageRef.child("images/plato_"+resultId+".jpg");

        UploadTask uploadTask = platosImagesRef.putBytes(image);

        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            // Continuamos con la tarea para obtener la URL
            return platosImagesRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // URL de descarga del archivo
                Uri downloadUri = task.getResult();
                Log.d(TAG, downloadUri.toString());
            } else {
                // Fallo
                Log.d(TAG, "- Error subiendo la foto");
            }
        });
    }


    @Override
    public void onResult(List<Plato> result) {

    }

    @Override
    public void onResult(Plato result) {

    }

    @Override
    public void onResult(Long resultId) {
        subirFoto(image,resultId);
    }
}
