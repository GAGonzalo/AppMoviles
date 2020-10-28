package com.example.practica01.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

@Entity
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String email;

    private String direccion;

    private String numero_direccion;

    private boolean take_away;

    private boolean delivery;

    @TypeConverters(PlatosConverter.class)
    private List<Plato> listaPlatos;

    private Double precioTotal;

    public Pedido(Long id, String email, String direccion, String numero_direccion, boolean take_away, boolean delivery, List<Plato> listaPlatos, Double precioTotal) {
        this.id = id;
        this.email = email;
        this.direccion = direccion;
        this.numero_direccion = numero_direccion;
        this.take_away = take_away;
        this.delivery = delivery;
        this.listaPlatos = listaPlatos;
        this.precioTotal = precioTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumero_direccion() {
        return numero_direccion;
    }

    public void setNumero_direccion(String numero_direccion) {
        this.numero_direccion = numero_direccion;
    }

    public boolean isTake_away() {
        return take_away;
    }

    public void setTake_away(boolean take_away) {
        this.take_away = take_away;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public List<Plato> getListaPlatos() {
        return listaPlatos;
    }

    public void setListaPlatos(List<Plato> listaPlatos) {
        this.listaPlatos = listaPlatos;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public static class PlatosConverter {
        @TypeConverter
        public List<Plato> fromString(String listString){
            return new Gson().fromJson(listString, new TypeToken<List<Plato>>() {}.getType());
        }

        @TypeConverter
        public String saveList(List<Plato> listOfPlatos) {
            return new Gson().toJson(listOfPlatos);
        }
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", numero_direccion='" + numero_direccion + '\'' +
                ", take_away=" + take_away +
                ", delivery=" + delivery +
                ", listaPlatos=" + listaPlatos +
                ", precioTotal=" + precioTotal +
                '}';
    }
}
