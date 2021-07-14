package com.penautsoft.zapapp.ui.order;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.penautsoft.zapapp.Adapters.MyListAdapter;
import com.penautsoft.zapapp.Dialogs;
import com.penautsoft.zapapp.Entity.MyException;
import com.penautsoft.zapapp.Entity.Order;
import com.penautsoft.zapapp.Networking.HandlerException;
import com.penautsoft.zapapp.Networking.RequestHandler;
import com.penautsoft.zapapp.OrderDetailActivity;
import com.penautsoft.zapapp.R;
import com.penautsoft.zapapp.RecyclerItemClickListener;
import com.penautsoft.zapapp.util.Util;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderFragment extends Fragment implements RecyclerItemClickListener, SwipeRefreshLayout.OnRefreshListener{

    private View rootView,loadingView,responseView;
    private RecyclerView recyclerOrders;
    private SwipeRefreshLayout swipeLayout;
    private ArrayList<Order> orders;
    private MyListAdapter mainAdapter;
    private RequestHandler requestHandler;
    private HandlerException handlerException;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_orders,container,false);

        initComponents();

        loadOrders(false);

        return rootView;
    }

    private void initComponents(){
        loadingView = rootView.findViewById(R.id.fg_order_loading);
        responseView = rootView.findViewById(R.id.fg_order_response);
        recyclerOrders = rootView.findViewById(R.id.fg_order_recycler);
        swipeLayout = rootView.findViewById(R.id.fg_order_swipe);
        orders = new ArrayList<>();
        mainAdapter = new MyListAdapter(getContext());
        requestHandler = new RequestHandler(getContext());
        handlerException = new HandlerException();
    }

    private void loadOrders(boolean isRefreshing){

        if( isRefreshing ){
            loadingView.setVisibility(View.VISIBLE);
            responseView.setVisibility(View.GONE);
            recyclerOrders.setVisibility(View.GONE);
        }

        loadRemote();


    }

    private void loadRemote(){
        String url = getResources().getString(R.string.host) +
                "api/Pedidos/clienteFiltro";
        RequestHandler.GET getHandler = requestHandler.get();

        SharedPreferences sha = getContext().getSharedPreferences(getResources().getString(R.string.SharedPrefKey), Context.MODE_PRIVATE);
                Map<String,String> header = new HashMap<>();
                header.put("Authorization","Bearer "+sha.getString("token",null));

        try{
            getHandler.getOrders(url, header, null, new RequestHandler.callBack() {
                @Override
                public void respuesta(Object response) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Order>>(){}.getType();

                    try {
                        orders = gson.fromJson((String) response, listType);
                        if( orders == null )
                            orders = new ArrayList<>();
                    }catch (Exception e){
                        orders = new ArrayList<>();
                    }

                    if( !orders.isEmpty() ){
                        orders = Util.orderSorter(orders);
                        MyListAdapter.OrdersAdapter ordersAdapter =
                                mainAdapter.new OrdersAdapter(getContext(),OrderFragment.this,orders);
                        LinearLayoutManager llm = new LinearLayoutManager(getContext());

                        recyclerOrders.setLayoutManager(llm);
                        recyclerOrders.setAdapter(ordersAdapter);
                        recyclerOrders.setVisibility(View.VISIBLE);
                    }else{
                        TextView txvMessage = rootView.findViewById(R.id.mdl_error_message);
                        ImageView imgIcon  = rootView.findViewById(R.id.mdl_error_icon);
                        rootView.findViewById(R.id.mdl_error_btn).setVisibility(View.GONE);

                        imgIcon.setImageResource(R.drawable.ic_accessibility_24);
                        txvMessage.setText("No hay pedidos activos");
                        responseView.setVisibility(View.VISIBLE);
                    }
                    loadingView.setVisibility(View.GONE);
                    swipeLayout.setOnRefreshListener(OrderFragment.this);
                    swipeLayout.setRefreshing(false);
                }

                @Override
                public void error(VolleyError msg) {
                    try {
                        MyException exception = handlerException.getException(msg);
                        TextView txvMessage = rootView.findViewById(R.id.mdl_error_message);
                        ImageView imgIcon  = rootView.findViewById(R.id.mdl_error_icon);
                        rootView.findViewById(R.id.mdl_error_btn).setVisibility(View.GONE);

                        imgIcon.setImageResource(exception.getIcon());
                        txvMessage.setText(exception.getMessage());
                        loadingView.setVisibility(View.GONE);
                        responseView.setVisibility(View.VISIBLE);
                        swipeLayout.setOnRefreshListener(OrderFragment.this);
                        swipeLayout.setRefreshing(false);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(Object object, int id,View view) {
        if( id == R.id.mdl_order_detail){
            Intent intent = new Intent(getContext(), OrderDetailActivity.class);
            intent.putExtra("order",(Order)object);
            try {
                getContext().startActivity(intent);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else{
            Dialogs.FullDialog.display(getParentFragmentManager(), (Order) object);
        }
    }

    @Override
    public void onRefresh() {
        loadOrders(true);
    }
}
