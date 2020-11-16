package com.example.practica01;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.Random;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private boolean markPlaced=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMapSettings();
    }

    private void setMapSettings() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

       mMap.setOnMapLongClickListener(latLng -> {
            Intent intent = new Intent();
            intent.putExtra("LatLng",latLng);
            setResult(RESULT_OK,intent);
           crearLocalAleatorio(latLng);
        });



    }



    private void crearLocalAleatorio(LatLng posicionOriginal) {

        if(!markPlaced) {
            Random r = new Random();

            // Una direccion aleatoria de 0 a 359 grados
            int direccionRandomEnGrados = r.nextInt(360);

            // Una distancia aleatoria de 100 a 1000 metros
            int distanciaMinima = 100;
            int distanciaMaxima = 1000;
            int distanciaRandomEnMetros = r.nextInt(distanciaMaxima - distanciaMinima) + distanciaMinima;

            LatLng nuevaPosicion = SphericalUtil.computeOffset(
                    posicionOriginal,
                    distanciaRandomEnMetros,
                    direccionRandomEnGrados
            );


            mMap.addMarker(new MarkerOptions().position(nuevaPosicion).title("Local que envia tu pedido"));

            markPlaced=true;

            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.color(0x66FF0000);


            polylineOptions.add(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()), nuevaPosicion);

            mMap.addPolyline(polylineOptions);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==9999 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            setMapSettings();
        }
    }



}
