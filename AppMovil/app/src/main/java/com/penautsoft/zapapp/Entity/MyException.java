package com.penautsoft.zapapp.Entity;

public class MyException {

    private String message,cuase;
    private int statusCode,icon;

    public MyException(){}

    public MyException(String message, String cuase, int statusCode, int icon) {
        this.message = message;
        this.cuase = cuase;
        this.statusCode = statusCode;
        this.icon = icon;
    }

    public String getMessage() {
        return message;
    }

    public String getCuase() {
        return cuase;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "MyException{" +
                "message='" + message + '\'' +
                ", cuase='" + cuase + '\'' +
                ", statusCode=" + statusCode +
                ", icon=" + icon +
                '}';
    }
}
