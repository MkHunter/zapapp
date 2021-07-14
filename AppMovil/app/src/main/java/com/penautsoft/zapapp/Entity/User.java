package com.penautsoft.zapapp.Entity;

import java.io.Serializable;

public class User implements Serializable {

    private int rol,idUsuario;
    private String nombreUsuario;
    private Client entidad;

    public User() {}

    public User(int rol, int idUsuario, String nombreUsuario, Client entidad) {
        this.rol = rol;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.entidad = entidad;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Client getEntidad() {
        return entidad;
    }

    public void setEntidad(Client entidad) {
        this.entidad = entidad;
    }

    public String toJson(){
        return "{" +
                "rol=" + rol +
                ", idUsuario=" + idUsuario +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", entidad=" + entidad +
                '}';
    }

    @Override
    public String toString() {
        return "User{" +
                "rol=" + rol +
                ", idUsuario=" + idUsuario +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", entidad=" + entidad +
                '}';
    }
}
