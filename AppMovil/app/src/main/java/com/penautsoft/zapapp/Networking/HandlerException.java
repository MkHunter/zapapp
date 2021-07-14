package com.penautsoft.zapapp.Networking;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.penautsoft.zapapp.Entity.MyException;
import com.penautsoft.zapapp.R;

public class HandlerException {


    public MyException getException(VolleyError error){
        MyException myException;
        String message = "";
        int icon = 0;

        if (error instanceof TimeoutError) {
            message = "Se agoto la conexion";
            icon = R.drawable.ic_time_out;
        }else if (error instanceof NoConnectionError){
            message = "Sin Conexion";
            icon = R.drawable.ic_wifi_off_24;
        }else if(error instanceof AuthFailureError){
            message = "No autorizado";
            icon = R.drawable.ic_authorization;
        }else if( error instanceof ServerError){
            message = "Error de servidor";
            icon = R.drawable.ic_server_1;
        }else if( error instanceof NetworkError){
            message = "Error de red";
            icon = R.drawable.ic_network_2;
        }else if( error instanceof ParseError){
            message = "Error de parseo";
            icon = R.drawable.ic_parse;
        }

        try {
            myException = new MyException(message,error.getMessage(),error.networkResponse.statusCode,icon);
        }catch (Exception e){
            myException = new MyException(message,error.getMessage(),-1,icon);
        }

        return myException;
    }
}
