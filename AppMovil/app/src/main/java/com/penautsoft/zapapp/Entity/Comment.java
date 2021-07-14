package com.penautsoft.zapapp.Entity;

import android.content.Context;

import com.penautsoft.zapapp.R;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Comment implements Serializable {

    private String mensaje;
    private int usuario;
    private String fechaConversacion;//,horaRequest ;
    private int idConversacion;

    public Comment(){}

    public Comment(Context context) {
        this.mensaje = context.getString(R.string.lorem_ipsum_med);
    }

    public Comment(Context context, String date) {
        this.mensaje = context.getString(R.string.lorem_ipsum_med);
        this.fechaConversacion = date;
    }

    public Comment(String message, String date, int user,int idconv) {
        this.mensaje = message;
        this.fechaConversacion = date;
        idConversacion = idconv;
        this.usuario = user;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFechaConversacion() {
        return fechaConversacion;
    }

    public void setFechaConversacion(String fechaConversacion) {
        this.fechaConversacion = fechaConversacion;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getIdConversacion() {
        return idConversacion;
    }

    /*public String getHoraRequest() {
        return horaRequest;
    }

    public void setHoraRequest(String horaRequest) {
        this.horaRequest = horaRequest;
    }*/

    public void setIdConversacion(int idConversacion) {
        this.idConversacion = idConversacion;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "mensaje='" + mensaje + '\'' +
                ", usuario=" + usuario +
                ", fechaConversacion='" + fechaConversacion + '\'' +
                //", horaRequest='" + horaRequest + '\'' +
                ", idConversacion=" + idConversacion +
                '}';
    }
}
