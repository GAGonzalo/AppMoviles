package com.example.practica01;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class NotificationService extends IntentService {

    public NotificationService() {
        super("MyIntentService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Thread.sleep(3500);
            Intent i = new Intent();
            i.putExtra("data1","INTENT SERVICE");
            i.putExtra("data2","TimeStamp: "+System.currentTimeMillis());
            i.setAction(NotificationPublisher.CONFIRMAR_PEDIDO);
            sendBroadcast(i);
            this.sendBroadcast(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
