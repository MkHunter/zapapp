package com.penautsoft.zapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.penautsoft.zapapp.Entity.Order;
import com.penautsoft.zapapp.Entity.Service;
import com.penautsoft.zapapp.Networking.RequestHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView txvAmount,txvPayment,txvCreatedDate,txvEstimatedDate,txvDescription,txvTotal;
    private Toolbar toolbar;
    private View loadingView,responseView,tableLayout;
    private Order order;
    private TableLayout servicesTable;
    private RequestHandler requestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        initComponents();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if( getIntent() != null){

            order = (Order) getIntent().getSerializableExtra("order");
            Log.d(this.getClass().getCanonicalName(),order.toString());
            String formatDate = order.getFechaEstimada().split(" ")[0];
            formatDate = formatDate.substring(0,formatDate.length()-1);

            txvPayment.setText("$" + order.getAbonado());
            txvEstimatedDate.setText(formatDate);
            txvDescription.setText(order.getDescripcion());
            txvCreatedDate.setText(order.getFechaPedido().split(" ")[0]);
            txvAmount.setText("$" + order.getMonto());

            getServicesRemote();
        }
    }

    private void initComponents(){
        txvAmount = findViewById(R.id.act_ord_det_amount);
        txvCreatedDate = findViewById(R.id.act_ord_det_create_date);
        txvDescription = findViewById(R.id.act_ord_det_description);
        txvEstimatedDate = findViewById(R.id.act_ord_det_aprox_date);
        txvPayment = findViewById(R.id.act_ord_det_payment);
        toolbar = findViewById(R.id.act_ord_det_tool);
        loadingView = findViewById(R.id.act_ord_det_loading);
        responseView = findViewById(R.id.act_ord_det_response);
        servicesTable = findViewById(R.id.act_ord_det_datable);
        txvTotal = findViewById(R.id.act_ord_det_total);
        tableLayout = findViewById(R.id.act_ord_det_table_layout);
        requestHandler = new RequestHandler(this);
    }

    private void getServicesLocal(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                for(int i = 0; i < order.getServicios().size(); i++){

                    Service srv = order.getService(i);

                    final View view = View.inflate(OrderDetailActivity.this,R.layout.model_datatable_row,null);
                    TextView txvNum = view.findViewById(R.id.mdl_datable_row_number),
                                txvServ = view.findViewById(R.id.mdl_datable_row_service),
                                txvCost = view.findViewById(R.id.mdl_datable_row_cost);

                    txvNum.setText(String.valueOf( ( i + 1 ) ) );
                    txvServ.setText(srv.getNombre());
                    txvCost.setText( String.valueOf( srv.getCosto() ) );

                    servicesTable.post(new Runnable() {
                        @Override
                        public void run() {
                            servicesTable.addView(view);
                        }
                    });
                }
                txvTotal.setText("$ "+order.getTotal());
                loadingView.setVisibility(View.GONE);
                tableLayout.setVisibility(View.VISIBLE);

            }
        }, 1000);
    }

    private void getServicesRemote(){
        String url = getResources().getString(R.string.host) +
                "api/Pedidos/"+order.getFolioPedido();

        SharedPreferences sha = getSharedPreferences(getResources().getString(R.string.SharedPrefKey), Context.MODE_PRIVATE);
        Map<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+sha.getString("token",null));

        RequestHandler.GET getHandler = requestHandler.get();

        try {
            getHandler.getServicesById(url, header, null, new RequestHandler.callBack() {
                @Override
                public void respuesta(Object response) {
                    Gson gson = new Gson();
                    Order convaux = gson.fromJson((String)response,Order.class);

                    if( !convaux.isCommentsEmpty() ){
                        order.setServicios(convaux.getServicios());
                        for( int i = 0; i < convaux.getServicios().size(); i++){
                            Service srv = order.getService(i);

                            final View view = View.inflate(OrderDetailActivity.this,R.layout.model_datatable_row,null);
                            TextView txvNum = view.findViewById(R.id.mdl_datable_row_number),
                                    txvServ = view.findViewById(R.id.mdl_datable_row_service),
                                    txvCost = view.findViewById(R.id.mdl_datable_row_cost);

                            txvNum.setText(String.valueOf( ( i + 1 ) ) );
                            txvServ.setText(srv.getNombre());
                            txvCost.setText( String.valueOf( srv.getCosto() ) );

                            servicesTable.post(new Runnable() {
                                @Override
                                public void run() {
                                    servicesTable.addView(view);
                                }
                            });
                        }

                        txvTotal.setText("$ "+order.getTotal());
                        loadingView.setVisibility(View.GONE);
                        tableLayout.setVisibility(View.VISIBLE);
                    }else{
                        final View view = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.model_error,null);
                        ImageView imgIcon = findViewById(R.id.mdl_error_icon);
                        TextView txvMessage = findViewById(R.id.mdl_error_message);
                        findViewById(R.id.mdl_error_btn).setVisibility(View.GONE);

                        txvMessage.setText("No hay servicios");
                        txvMessage.setTextColor(Color.BLACK);
                        imgIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_accessibility_24));
                        imgIcon.setColorFilter(Color.BLACK);
                        loadingView.post(new Runnable() {
                            @Override
                            public void run() {
                                loadingView.setVisibility(View.GONE);
                                responseView.setVisibility(View.VISIBLE);

                            }
                        });
                    }
                }

                @Override
                public void error(VolleyError msg) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if( id == android.R.id.home )
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }
}