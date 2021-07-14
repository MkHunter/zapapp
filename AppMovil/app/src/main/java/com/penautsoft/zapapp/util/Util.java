package com.penautsoft.zapapp.util;

import android.content.Context;

import com.penautsoft.zapapp.Entity.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Util {

    public static class DateManagement{

        public static String formattedDate(Date date){
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        }

        public static String dateView(Date postDate,Date currentDate,int dayLimit){
            String date;


            Calendar fd = Calendar.getInstance(),td = Calendar.getInstance();
            fd.setTime(postDate);
            td.setTime(currentDate);
            long diff = td.getTimeInMillis() - fd.getTimeInMillis();
            long number = diff / ( 60 * 60 * 1000);

            if( number == 0 ) {
                number = diff / (60 * 1000); // -> minutos
                date = number == 0? "Justo ahora" : number + (number > 1? " minutos" : " minuto");
            }else if( number > 23 ) {
                number = diff / (24 * 60 * 60 * 1000); // -> dias
                if( number > dayLimit){
                    date = Util.DateManagement.formattedDate(postDate);
                }else
                    date = number + (number > 1? " dias" : " dia");
            }else {
                //long days = diff / (60 * 1000); // -> miutos
                date =  number + (number > 1? " horas" : " hora");
            }

            return date;
        }

        public static String getRemainTime(Date postDate,Date currentDate){
            String date = "";


            Calendar fd = Calendar.getInstance(),td = Calendar.getInstance();
            fd.setTime(postDate);
            td.setTime(currentDate);
            long diff = td.getTimeInMillis() - fd.getTimeInMillis();
            long number = diff / ( 60 * 60 * 1000);

            if( number > 23 ) {
                number = diff / (24 * 60 * 60 * 1000); // -> dias
                date = number + (number > 1? " dias" : " dia");
            }else{
                date = "0 dias";
            }

            return date;
        }

        public static String getDeviceFormattedDate(String format){
            return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        }

    }

    public static class ScreenManagement{

        public static float convertPxToDp(Context context, float px) {
            return px / context.getResources().getDisplayMetrics().density;
        }

        public static float convertDpToPx(Context context, float dp) {
            return dp * context.getResources().getDisplayMetrics().density;
        }

    }


    public static String formatPhone(String phone){
        String aux = "";

        for (int i = 0; i < phone.length(); i++) {

            aux += phone.charAt(i);
            if( i == 2 || i == 6)
                aux += "-";
        }

        return aux;
    }

    public static String prettyList(ArrayList list){
        StringBuilder out = new StringBuilder("[\n");

        for(Object obj : list){
            out.append(String.valueOf(obj)).append(", \n");
        }
        out.append("]");

        return out.toString();
    }


    public static ArrayList<Order> orderSorter(List<Order> orderList){
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        ArrayList<Order> unsortedActive = new ArrayList<>(),
                        unsortedNonActive = new ArrayList<>();
        for (Order ord : orderList) {
            if( ord.getEstadoPedido() < 4)
                unsortedActive.add(ord);
            else
                unsortedNonActive.add(ord);
        }

        Collections.sort(unsortedActive, new Comparator<Order>() {
            @Override
            public int compare(Order order, Order t1) {
                try {
                    return sdf.parse(order.getFechaPedido()).compareTo(sdf.parse(t1.getFechaPedido()));
                } catch (Exception e) {
                    return 0;
                }
            }
        });

        Collections.sort(unsortedNonActive, new Comparator<Order>() {
            @Override
            public int compare(Order order, Order t1) {
                try {
                    return sdf.parse(order.getFechaPedido()).compareTo(sdf.parse(t1.getFechaPedido()));
                } catch (Exception e) {
                    return 0;
                }
            }
        });

//        Collections.sort(unsortedActive,Collections.reverseOrder());
        Collections.reverse(unsortedActive);
        unsortedActive.addAll(unsortedNonActive);

        return unsortedActive;
    }
}
