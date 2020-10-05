package com.example.practica01;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationPublisher extends BroadcastReceiver {

    public static final String CONFIRMAR_PEDIDO = "com.example.practica01.CONFIRMAR_PEDIDO" ;


    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equalsIgnoreCase(CONFIRMAR_PEDIDO)){
             enviarNotificacion(context,intent);
        }
    }
    private void enviarNotificacion(Context context,Intent intent){
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        Notification notification = intent.getParcelableExtra(CONFIRMAR_PEDIDO);
        notificationManager.notify(99,notification);
    }
}
