package com.penautsoft.zapapp.Entity;

import android.content.Context;

import com.penautsoft.zapapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Conversation implements Serializable {

    private int idHilo,estado;
    private String postPrincipal,fechaPost,horaRequest;
    private ArrayList<String> imagenPostPrincipal;
    private Date currentDate;
    private Object usuarioPost;
    private ArrayList<Comment> conversaciones;

    /*public Conversation(Parcel in){
        readFromParcel(in);
    }*/

    public Conversation(){}

    public Conversation(Context ctx){
        this.postPrincipal = ctx.getString(R.string.lorem_ipsum);
    }

    public Conversation(Context ctx,ArrayList<String> images,String date,ArrayList<Comment> comments){
        this.postPrincipal = ctx.getString(R.string.lorem_ipsum);
        this.imagenPostPrincipal = images;
        this.fechaPost = date;
        this.currentDate = Calendar.getInstance().getTime();
        this.conversaciones = comments != null ? comments : new ArrayList<Comment>();
    }

    public Conversation(String msg){
        this.postPrincipal = msg;
    }

    public Conversation(String msg,ArrayList<String> images){
        this.postPrincipal = msg;
        this.imagenPostPrincipal = images;
    }

    public Conversation(String msg,ArrayList<String> images,String date){
        this.postPrincipal = msg;
        this.imagenPostPrincipal = images;
        this.fechaPost = date;
        this.currentDate = Calendar.getInstance().getTime();
        this.conversaciones = new ArrayList<>();
    }

    public Conversation(int postId, int status, String message, String date, String horaRequest, ArrayList<String> images, Object user, ArrayList<Comment> comments) {
        this.idHilo = postId;
        this.estado = status;
        this.postPrincipal = message;
        this.fechaPost = date;
        this.horaRequest = horaRequest;
        this.imagenPostPrincipal = images != null ? images : new ArrayList<String>();
        this.usuarioPost = user;
        this.conversaciones = comments != null ? comments : new ArrayList<Comment>();
    }

    /*public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Conversation createFromParcel(Parcel in) {
            return new Conversation(in);
        }

        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };*/

    public String getPostPrincipal() {
        return postPrincipal;
    }

    public void setPostPrincipal(String postPrincipal) {
        this.postPrincipal = postPrincipal;
    }

    public ArrayList<String> getImagenPostPrincipal() {
        return imagenPostPrincipal;
    }

    public void setImagenPostPrincipal(ArrayList<String> imagenPostPrincipal) {
        this.imagenPostPrincipal = imagenPostPrincipal;
    }

    public String getFechaPost() {
        return fechaPost;
    }

    public void setFechaPost(String fechaPost) {
        this.fechaPost = fechaPost;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Object getUsuarioPost() {
        return usuarioPost;
    }

    public void setUsuarioPost(Object usuarioPost) {
        this.usuarioPost = usuarioPost;
    }

    public ArrayList<Comment> getConversaciones() {
        return conversaciones;
    }

    public void setConversaciones(ArrayList<Comment> conversaciones) {
        this.conversaciones = conversaciones;
    }

    public int getIdHilo() {
        return idHilo;
    }

    public void setIdHilo(int idHilo) {
        this.idHilo = idHilo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getHoraRequest() {
        return horaRequest;
    }

    public void setHoraRequest(String horaRequest){ this.horaRequest = horaRequest; }

    public boolean isPhotosEmpty(){
        if( imagenPostPrincipal != null )
            return imagenPostPrincipal.isEmpty();

        return true;
    }

    public boolean isCommentsEmpty(){
        if( conversaciones != null)
            return conversaciones.isEmpty();
        return true;
    }

    public void addComment(Comment comment){
        try {
            this.conversaciones.add(comment);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "idHilo=" + idHilo +
                ", estado=" + estado +
                ", postPrincipal='" + postPrincipal + '\'' +
                ", fechaPost='" + fechaPost + '\'' +
                ", horaRequest='" + horaRequest + '\'' +
                ", imagenPostPrincipal=" + imagenPostPrincipal +
                ", currentDate=" + currentDate +
                ", usuarioPost=" + usuarioPost +
                ", conversaciones=" + conversaciones +
                '}';
    }
}
