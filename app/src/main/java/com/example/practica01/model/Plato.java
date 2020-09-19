package com.example.practica01.model;

public class Plato {
    private String nombre;
    private String descripcion;
    private Float precio;
    private Float calorias;

<<<<<<< HEAD
<<<<<<< HEAD
    @Override
    public String toString() {
        return "Plato{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", calorias=" + calorias +
                '}';
    }
=======
>>>>>>> develop
=======
>>>>>>> develop

    public Plato() {
    }

    public Plato(String nombre, String descripcion, Float precio, Float calorias) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Float getCalorias() {
        return calorias;
    }

    public void setCalorias(Float calorias) {
        this.calorias = calorias;
    }
}
