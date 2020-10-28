package com.example.practica01.service;

import com.example.practica01.model.Pedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PedidoService {
    @GET("pedidos/{id}")
    Call<Pedido> getPedido(@Path("id") String id);

    @GET("pedidos")
    Call<List<Pedido>> getPedidoList();

    @POST("pedidos")
    Call<Pedido> createPedido(@Body Pedido p);
  /*
    Si deciden usar SendMeal-Fake-API deberán usar un body
    del tipo @Body String body. Lo cual les facilitara cumplir el formato esperado

    JSONObject bodyExample = new JSONObject();
    paramObject.put("email", "sample@gmail.com");
    paramObject.put("pass", "4384984938943");
    createPlato(bodyExample.toString())
  */
}
