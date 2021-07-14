package com.penautsoft.zapapp.Entity;

import java.io.Serializable;

public class Shoe implements Serializable {

    private String imagen,color,marca;
    private int precio,idProducto,talla;

    public Shoe(){}

    public Shoe(String url) {
        this.imagen = url;
    }

    public Shoe(String imagen, String color, String marca, int precio, int idProducto, int talla) {
        this.imagen = imagen;
        this.color = color;
        this.marca = marca;
        this.precio = precio;
        this.idProducto = idProducto;
        this.talla = talla;
    }

    public String getImagen() {
        return imagen;
    }

    public String getColor() {
        return color;
    }

    public String getMarca() {
        return marca;
    }

    public int getPrecio() {
        return precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getTalla() {
        return talla;
    }

    @Override
    public String toString() {
        return "Shoe{" +
                "imagen='" + imagen + '\'' +
                ", color='" + color + '\'' +
                ", marca='" + marca + '\'' +
                ", precio=" + precio +
                ", idProducto=" + idProducto +
                ", talla=" + talla +
                '}';
    }
}
