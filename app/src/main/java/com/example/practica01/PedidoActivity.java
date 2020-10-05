package com.example.practica01;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.practica01.model.Plato;

import java.util.ArrayList;
import java.util.List;

public class PedidoActivity extends AppCompatActivity{

    private Button agregarPlatos;
    private static final int CODE=3232;

    private List<Plato> platos;
    private RecyclerView listaPedidos;
    private PedidoAdapter adapter;
    private RadioGroup tipoPedido;
    private TextView mailTV;
    private TextView direccionTV;
    private TextView numeroTV;
    private Button confirmarButton;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;

    private boolean tipopedidochecked=false;
    private boolean mail=false;
    private boolean direccion=false;
    private boolean numero=false;
    private BroadcastReceiver notificationBroadcastReceiver;

    public static final String PEDIDO_NOTIFICATION_CHANNEL_ID = "3132";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE && resultCode== RESULT_OK){
           System.out.println("Plato: " + data.getExtras().get("Nombre_Plato") + " - Precio: " + data.getExtras().get("Precio_Plato") );
           platos.add(new Plato(data.getExtras().getString("Nombre_Plato"),data.getExtras().getFloat("Precio_Plato")));
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

        }
    }


    private void setWidgets(){
        platos = new ArrayList<>();

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
            startActivityForResult(intent,CODE);
        });


    }
    private void setAdapters(){
        adapter = new PedidoAdapter(platos,this);
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

        return (mail && direccion && numero && tipopedidochecked && platos.size()>0);
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
