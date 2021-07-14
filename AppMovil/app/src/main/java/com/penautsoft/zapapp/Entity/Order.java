package com.penautsoft.zapapp.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {

    private int folioPedido,estadoPedido,abonado,monto;
    private String descripcion,fechaPedido,fechaEstimada,horaRequest;
    private Date currenDate;
    private Object usuario,cliente;
    private ArrayList<Service> servicios;

    public Order(){}

    public Order(int estadoPedido, String descripcion, String fechaPedido, String fechaEstimada) {
        this.estadoPedido = estadoPedido;
        this.descripcion = descripcion;
        this.fechaPedido = fechaPedido;
        this.fechaEstimada = fechaEstimada;
    }

    public Order(int orderId,int estadoPedido, String descripcion, String fechaPedido, String fechaEstimada, ArrayList<Service> services) {
        this.folioPedido = orderId;
        this.estadoPedido = estadoPedido;
        this.descripcion = descripcion;
        this.fechaPedido = fechaPedido;
        this.fechaEstimada = fechaEstimada;
        this.servicios = services;
    }

    public Order(int estadoPedido, String descripcion, String fechaPedido, String fechaEstimada, ArrayList<Service> services) {
        this.estadoPedido = estadoPedido;
        this.descripcion = descripcion;
        this.fechaPedido = fechaPedido;
        this.fechaEstimada = fechaEstimada;
        this.servicios = services;
    }

    public Order(int folioPedido, int estadoPedido, int abonado, int monto, String descripcion, String fechaPedido, String fechaEstimada, Date currenDate, Object usuario, Object cliente, ArrayList<Service> servicios) {
        this.folioPedido = folioPedido;
        this.estadoPedido = estadoPedido;
        this.abonado = abonado;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fechaPedido = fechaPedido;
        this.fechaEstimada = fechaEstimada;
        this.currenDate = currenDate;
        this.usuario = usuario;
        this.cliente = cliente;
        this.servicios = servicios;
    }

    //GETTERS

    public int getFolioPedido() {
        return folioPedido;
    }

    public void setFolioPedido(int folioPedido) {
        this.folioPedido = folioPedido;
    }

    public int getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(int estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public int getAbonado() {
        return abonado;
    }

    public void setAbonado(int abonado) {
        this.abonado = abonado;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(String fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public Date getCurrenDate() {
        return currenDate;
    }

    public void setCurrenDate(Date currenDate) {
        this.currenDate = currenDate;
    }

    public Object getUsuario() {
        return usuario;
    }

    public void setUsuario(Object usuario) {
        this.usuario = usuario;
    }

    public Object getCliente() {
        return cliente;
    }

    public void setCliente(Object cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Service> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<Service> servicios) {
        this.servicios = servicios;
    }

    public String getHoraRequest(){
        return horaRequest;
    }

    public void setHoraRequest(String horaRequest){
        this.horaRequest = horaRequest;
    }

    // Class Methods
    public Service getService(int i){
        return servicios.get(i);
    }

    public boolean isCommentsEmpty(){
        return servicios.isEmpty();
    }

    public int getTotal(){
        int total = 0;

        for (Service srv : servicios)
            total += srv.getCosto();

        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "folioPedido=" + folioPedido +
                ", estadoPedido=" + estadoPedido +
                ", abonado=" + abonado +
                ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                ", fechaPedido='" + fechaPedido + '\'' +
                ", fechaEstimada='" + fechaEstimada + '\'' +
                ", horaRequest='" + horaRequest + '\'' +
                ", currenDate=" + currenDate +
                ", usuario=" + usuario +
                ", cliente=" + cliente +
                ", servicios=" + servicios +
                '}';
    }
}
