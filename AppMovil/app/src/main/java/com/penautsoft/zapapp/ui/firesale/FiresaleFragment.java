package com.penautsoft.zapapp.ui.firesale;

import android.content.Context;
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
import com.penautsoft.zapapp.Entity.Shoe;
import com.penautsoft.zapapp.Networking.HandlerException;
import com.penautsoft.zapapp.Networking.RequestHandler;
import com.penautsoft.zapapp.R;
import com.penautsoft.zapapp.RecyclerItemClickListener;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FiresaleFragment extends Fragment implements RecyclerItemClickListener,SwipeRefreshLayout.OnRefreshListener {

    private View rootView,loadingView,responseView;
    private RecyclerView shoesRecycler;
    private SwipeRefreshLayout swipeLayout;
    private ArrayList<Shoe> shoes;
    private HandlerException handlerException;
    private Map<String,String> header = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_firesale,container,false);

        initComponents();

        SharedPreferences sha = getContext().getSharedPreferences(getResources().getString(R.string.SharedPrefKey), Context.MODE_PRIVATE);

        header.put("Authorization","Bearer "+sha.getString("token",null));

        loadShoes(false);

        return rootView;
    }

    private void initComponents(){
        loadingView = rootView.findViewById(R.id.fg_firesale_loading);
        responseView = rootView.findViewById(R.id.fg_firesale_response);
        shoesRecycler = rootView.findViewById(R.id.fg_firesale_shoes);
        swipeLayout = rootView.findViewById(R.id.fg_firesale_swipe);
        handlerException = new HandlerException();
    }

    private void loadShoes(boolean isRefreshing){

        if( isRefreshing ){
            loadingView.setVisibility(View.VISIBLE);
            responseView.setVisibility(View.GONE);
            shoesRecycler.setVisibility(View.GONE);
        }

        loadRemote();
    }

    private void loadRemote(){
        RequestHandler requestHandler = new RequestHandler(getContext());
        RequestHandler.GET getHandler = requestHandler.get();

        String url = getResources().getString(R.string.host)+
                    "api/Catalogo";

        try{
            getHandler.getPhotos(url, header, new RequestHandler.callBack() {
                @Override
                public void respuesta(Object response) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Shoe>>(){}.getType();

                    try {
                        shoes = gson.fromJson((String) response, listType);
                    }catch (Exception e){
                        shoes = new ArrayList<>();
                    }
                    if( !shoes.isEmpty() ){
                        MyListAdapter.FiresaleAdapter firesaleAdapter =
                                new MyListAdapter().new FiresaleAdapter(getContext(),FiresaleFragment.this,shoes);

                        LinearLayoutManager llm = new LinearLayoutManager(getContext());

                        shoesRecycler.setLayoutManager(llm);
                        shoesRecycler.setAdapter(firesaleAdapter);
                        shoesRecycler.setVisibility(View.VISIBLE);
                    }else{
                        TextView txvMessage = rootView.findViewById(R.id.mdl_error_message);
                        ImageView imgIcon  = rootView.findViewById(R.id.mdl_error_icon);
                        rootView.findViewById(R.id.mdl_error_btn).setVisibility(View.GONE);

                        imgIcon.setImageResource(R.drawable.ic_accessibility_24);
                        txvMessage.setText("No hay imagenes");
                        responseView.setVisibility(View.VISIBLE);
                    }

                    loadingView.setVisibility(View.GONE);
                    swipeLayout.setOnRefreshListener(FiresaleFragment.this);
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
                        swipeLayout.setOnRefreshListener(FiresaleFragment.this);
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
        Dialogs.ShoeDetailFullDialog.display(getParentFragmentManager(),(Shoe) object);
    }

    @Override
    public void onRefresh() {
        loadShoes(true);
    }

}
