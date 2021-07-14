package com.penautsoft.zapapp.util;

import android.content.Context;

import com.penautsoft.zapapp.Entity.Comment;
import com.penautsoft.zapapp.Entity.Conversation;
import com.penautsoft.zapapp.Entity.Order;
import com.penautsoft.zapapp.Entity.Service;
import com.penautsoft.zapapp.Entity.Shoe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class DataGeneration {


    public static class ConversationGen {

        public static int[] simpleConversation(int quantity){
            int[] items = new int[quantity];
            for (int i = 0; i < quantity; i++) {
                items[i] = i + 1;
            }

            return items;
        }

        public static ArrayList<Conversation> dynamicConversation(int quantity, Context ctx){
            ArrayList<Conversation> items = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'hh:mm:ss");
            Random rn = new Random();
            for (int i = 0; i < quantity; i++) {
                try {

                    items.add(new Conversation(ctx,null,DataGeneration.randomDate(10)/*sdf.parse(DataGeneration.randomDate(10))*/,conversationWithResponse(rn.nextInt(5)+1,0,ctx)));
                } catch (Exception e) {
                    e.printStackTrace();
                    items.add(new Conversation(ctx));
                }
            }

            return items;
        }

        public static ArrayList<Comment> conversationWithResponse(int comments, int delay, Context ctx){
            ArrayList<Comment> items = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'hh:mm:ss");
            for (int i = 0; i < comments; i++) {
                try{
                    //items.add(new Comment(ctx,sdf.parse(DataGeneration.randomDate(10))));
                }catch (Exception e){
                    e.printStackTrace();
                    items.add(new Comment(ctx));
                }
            }

            if( delay != 0){
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return items;
        }



    }

    public static class OrderGen {

        public static ArrayList<Order> genOrders(int quantity){
            ArrayList<Order> list = new ArrayList<>();
            Random rn = new Random();
            int bound = 1;
            for (int i = 0; i < quantity; i++) {

                bound = rn.nextInt(5)+1;

                try{
                    list.add( new Order(i,rn.nextInt(4)+1,"Pedido "+String.valueOf(i),"12/12/2002","18/12/2002", getServices(bound)) );
                }catch (Exception e){
                    e.printStackTrace();
                    list.add( new Order() );
                }
            }
            return list;
        }

        public static ArrayList<Service> getServices(int bound){
            ArrayList<Service> list = new ArrayList<>();
            Random rn = new Random();
            int cost = 0;
            for (int i = 0; i < bound; i++) {
                try{
                    list.add(new Service("Servicio "+( i + 1), rn.nextInt(200) + 1 ));
                }catch (Exception e){
                    list.add(new Service());
                }
            }

            return list;
        }
    }

    public static class FireSaleGen{

        public static ArrayList<Shoe> genShoe(int count){
            ArrayList<Shoe> list = new ArrayList<>();

            for (int i = 0; i < count; i++) {

                try{
                    list.add(new Shoe(ImageRepoTest.retImageUrl(),getRandomColorName(),"Chabelo",100,i+1,27));
                }catch (Exception e){
                    list.add(new Shoe(ImageRepoTest.retImageUrl()));
                }

            }

            return list;
        }

        private static String getRandomColorName(){
           String[] names = {"Negro","Cafe","Kaki"};
           Random rn = new Random();
           return names[rn.nextInt(names.length)];
        }
    }

    public static String randomDate(int monthLimit){
        Random rnDays = new Random(),
        rnMonth = new Random();

        int[][]  data =  { {1,3,5,7,8,10,12},
                  {4,6,9,11},
                  {2}
                };

        int month = rnMonth.nextInt(monthLimit)+1;
        int day = 0;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {

                if( data[i][j] == month){
                    if( i == 0){ //moth with 31 days
                        day = rnDays.nextInt(30) + 1;
                        break;
                    }else if( i == 1){ //months with 30 days
                        day = rnDays.nextInt(31) + 1;
                        break;
                    }else{ //february
                        day = rnDays.nextInt(28) + 1;
                        break;
                    }
                }

            }
        }
        return ""+day+"-"+month+"-2020T00:00:00";

    }
}
