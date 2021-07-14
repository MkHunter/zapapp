package com.penautsoft.zapapp.Entity;

import java.io.Serializable;
import java.util.Arrays;

public class Service implements Serializable {

    private int folio,costo;
    private String nombre;
    private int[] rangoCostos;

    public Service(){}

    public Service(String nombre, int costo) {
        this.costo = costo;
        this.nombre = nombre;
    }

    public Service(int folio, int costo, String nombre, int[] rangoCostos) {
        this.folio = folio;
        this.costo = costo;
        this.nombre = nombre;
        this.rangoCostos = rangoCostos;
    }

    public int getFolio() {
        return folio;
    }

    public int getCosto() {
        return costo;
    }

    public String getNombre() {
        return nombre;
    }

    public int[] getRangoCostos() {
        return rangoCostos;
    }

    @Override
    public String toString() {
        return "Service{" +
                "folio=" + folio +
                ", costo=" + costo +
                ", nombre='" + nombre + '\'' +
                ", rangoCostos=" + Arrays.toString(rangoCostos) +
                '}';
    }
}
