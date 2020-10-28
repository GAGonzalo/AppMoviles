package com.example.practica01;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica01.adapters.PedidoAdapter;
import com.example.practica01.model.Pedido;
import com.example.practica01.model.Plato;
import com.example.practica01.repository.PedidoRepository;
import com.example.practica01.service.PedidoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PedidoActivity extends AppCompatActivity {

    private Button agregarPlatos;
    private static final int CODE=3232;

    private List<Plato> pedido;
    private RecyclerView listaPedidos;
    private PedidoAdapter adapter;
    private RadioGroup tipoPedido;
    private TextView mailTV;
    private TextView direccionTV;
    private TextView numeroTV;
    private Button confirmarButton;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;
    private TextView precioTotalTV;

    private Double precioTotal=Double.valueOf(0);
    private boolean tipopedidochecked=false;
    private boolean mail=false;
    private boolean direccion=false;
    private boolean numero=false;
    private Boolean usar_api;
    private BroadcastReceiver notificationBroadcastReceiver;

    public static final String PEDIDO_NOTIFICATION_CHANNEL_ID = "3132";

    private Application application;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);


        usar_api=getIntent().getExtras().getBoolean("Usar Api");
        application =getApplication();
        createNotificationChannel();
        setWidgets();
        setListeners();
        setAdapters();
        setBroadcasterReceiver();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        if(!precioTotal.equals(Double.valueOf(0))) {
            precioTotalTV.setVisibility(View.VISIBLE);
            precioTotalTV.setText("Precio Total: $" + precioTotal);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE && resultCode== RESULT_OK){
            Plato plato = (Plato) data.getExtras().get("Plato");
            precioTotal += plato.getPrecio();
            pedido.add(plato);
        }
    }

    class ConfirmarPedidoTask extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return strings[1];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            prepareNotification();
            Pedido p = crearPedido();


            if(usar_api) {
                crearPedidoApi(p);
            }
            else{
                crearPedidoRoom(p);
            }
            
        }

        private void crearPedidoApi(Pedido p) {
            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            PedidoService pedidoService = retrofit.create(PedidoService.class);

            Call<Pedido> call = pedidoService.createPedido(p);

            call.enqueue(new Callback<Pedido>() {
                @Override
                public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                    if (!response.isSuccessful()){
                        Log.d("DEBUG", "Pedido creado");
                    }

                }

                @Override
                public void onFailure(Call<Pedido> call, Throwable t) {
                    Log.d("DEBUG", "Error en la creacion del pedido");
                    t.printStackTrace();
                }
            });
        }

        private void crearPedidoRoom(Pedido p){

            PedidoRepository pedidoRepository = new PedidoRepository(application,null);
            pedidoRepository.insertPedido(p);
        }
    }

    private Pedido crearPedido() {
        String email = mailTV.getText().toString();
        String direccion = direccionTV.getText().toString();
        String numero = numeroTV.getText().toString();
        Boolean delivery = ((RadioButton)findViewById(R.id.domicilioRB)).isChecked();
        Boolean take_away = ((RadioButton)findViewById(R.id.takeAwayRB)).isChecked();
        Pedido p = new Pedido(null,email,direccion,numero,take_away,delivery,pedido,precioTotal);
        return p;
    }


    private void setWidgets(){
        pedido = new ArrayList<>();
        precioTotalTV = findViewById(R.id.precioTotal);
        agregarPlatos= findViewById(R.id.agregarPlatoButton);
        mailTV=findViewById(R.id.emailPedidoTV);
        direccionTV=findViewById(R.id.direccionPedidoTV);
        numeroTV=findViewById(R.id.numeroPedidoTV);
        confirmarButton= findViewById(R.id.confirmarPedidoButton);
        listaPedidos =findViewById(R.id.pedidoRV);
        tipoPedido=findViewById(R.id.tipoPedidoRG);
    }
    private void setListeners(){
        confirmarButton.setOnClickListener((View v)->{

            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            if(checkFields()){
                new ConfirmarPedidoTask().execute("Confirmando pedido","Pedido Confirmado");
            }
            else Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show();

        });


        RadioGroup.OnCheckedChangeListener tipoPedidoListener = (radioGroup, i) -> {
            switch(i){
                case R.id.takeAwayRB:
                case R.id.domicilioRB:
                    tipopedidochecked=true;
                    break;
            }
        };

        tipoPedido.setOnCheckedChangeListener(tipoPedidoListener);

        agregarPlatos.setOnClickListener((View v)->{
            Intent intent = new Intent(getApplicationContext(), ListarPlatosActivity.class);
            intent.putExtra("Pedido","Nuevo Pedido");
            intent.putExtra("Usar Api",usar_api);
            startActivityForResult(intent,CODE);
        });


    }
    private void setAdapters(){
        adapter = new PedidoAdapter(pedido,this);
        layoutManager = new LinearLayoutManager(this);
        listaPedidos.setLayoutManager(layoutManager);
        listaPedidos.setAdapter(adapter);
    }
    private void setBroadcasterReceiver(){
        notificationBroadcastReceiver = new NotificationPublisher();
        IntentFilter filter = new IntentFilter();
        filter.addAction(NotificationPublisher.CONFIRMAR_PEDIDO);
        this.registerReceiver(notificationBroadcastReceiver, filter);
    }

    private boolean checkFields(){
        mail = !mailTV.getText().toString().isEmpty();
        direccion = !direccionTV.getText().toString().isEmpty();
        numero = !numeroTV.getText().toString().isEmpty();
        tipopedidochecked = (tipoPedido.getCheckedRadioButtonId()==-1) ? false : true;

        return (mail && direccion && numero && tipopedidochecked && pedido.size()>0);
    }
    private void createNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(PEDIDO_NOTIFICATION_CHANNEL_ID, "Pedidos", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Canal para pedidos");
        NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
    private void prepareNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PEDIDO_NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle("Pedido");
        builder.setContentText("Su pedido fue confirmado y esta siendo preparado!");
        builder.setSmallIcon(R.drawable.ic_baseline_shopping_cart_24);
        builder.setAutoCancel(true);
        builder.setChannelId(PEDIDO_NOTIFICATION_CHANNEL_ID);

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.setAction(NotificationPublisher.CONFIRMAR_PEDIDO);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();

        notificationIntent.putExtra(NotificationPublisher.CONFIRMAR_PEDIDO,notification);

        sendBroadcast(notificationIntent);

    }


}
