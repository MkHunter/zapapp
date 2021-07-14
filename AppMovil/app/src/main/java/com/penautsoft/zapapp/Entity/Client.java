package com.penautsoft.zapapp.Entity;

public class Client {

    private String telefono,nombre,apellido,correo;
    private int disponible,identificador;

    public Client(){}

    public Client(String telefono, String nombre, String apellido, String correo, int disponible, int identificador) {
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.disponible = disponible;
        this.identificador = identificador;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String toJson(){
        return "{" +
                "telefono='" + telefono + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", disponible=" + disponible +
                ", identificador=" + identificador +
                '}';
    }

    @Override
    public String toString() {
        return "Client{" +
                "telefono='" + telefono + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", disponible=" + disponible +
                ", identificador=" + identificador +
                '}';
    }
}
