package com.penautsoft.zapapp.ui.sell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.penautsoft.zapapp.Adapters.MyListAdapter;
import com.penautsoft.zapapp.CreateConversationActivity;
import com.penautsoft.zapapp.Entity.Conversation;
import com.penautsoft.zapapp.Entity.MyException;
import com.penautsoft.zapapp.Networking.HandlerException;
import com.penautsoft.zapapp.Networking.RequestHandler;
import com.penautsoft.zapapp.R;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConversationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView rvConversation;
    private ArrayList<Conversation> conversations;
    private MyListAdapter.CommentsAdapter commentsAdapter;
    private View listView,errorView,loadingView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fab;
    private View view;
    private MyListAdapter myListAdapter;
    private boolean isActiveTab;
    private HandlerException handlerException;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list,container,false);

        Bundle arguments = getArguments();

        if( arguments != null){
            init(view);
            isActiveTab = arguments.getBoolean("activePub",false);
            loadPublishments(false);
        }

        return view;
    }

    private void init(View view){
        swipeRefreshLayout = view.findViewById(R.id.fg_conv_swipe);
        listView = view.findViewById(R.id.fg_conv_good);
        errorView = view.findViewById(R.id.fg_conv_bad);
        loadingView = view.findViewById(R.id.fg_conv_loading);
        rvConversation = view.findViewById(R.id.fg_conv_list);
        fab = view.findViewById(R.id.fg_conv_fab);
        rvConversation = view.findViewById(R.id.fg_conv_list);
        conversations = new ArrayList<>();
        myListAdapter = new MyListAdapter(getContext());
        handlerException = new HandlerException();
    }

    private void loadPublishments(boolean onRefresh){

        if( onRefresh ){
            loadingView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        }

        RequestHandler requestHandler = new RequestHandler(getContext());

        String url = getResources().getString(R.string.host)
                +"api/Conversaciones/estado/?estado=" + (isActiveTab ? "1" : "2");

        SharedPreferences sha = getContext().getSharedPreferences(getContext().getResources().getString(R.string.SharedPrefKey), Context.MODE_PRIVATE);

        Map<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer "+sha.getString("token",null));
        try {
            requestHandler.getString(url, header, null, new RequestHandler.callBack() {
                @Override
                public void respuesta(Object response) {

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Conversation>>(){}.getType();

                    try{
                        conversations = gson.fromJson((String) response,listType);
                    }catch (Exception e){
                        e.printStackTrace();
                        conversations = new ArrayList<>();
                    }

                    if( !conversations.isEmpty() ){
                        Collections.reverse(conversations);


                        commentsAdapter = myListAdapter.new CommentsAdapter(conversations,isActiveTab);

                        LinearLayoutManager llm = new LinearLayoutManager(getContext());

                        rvConversation.setLayoutManager(llm);
                        rvConversation.setAdapter(commentsAdapter);
                        listView.setVisibility(View.VISIBLE);
                    }else{
                        TextView txvMessage = view.findViewById(R.id.mdl_error_message);
                        ImageView imgIcon  = view.findViewById(R.id.mdl_error_icon);
                        view.findViewById(R.id.mdl_error_btn).setVisibility(View.GONE);

                        imgIcon.setImageResource(R.drawable.ic_accessibility_24);
                        String message = isActiveTab ? "NO hay conversaciones disponibles, empieza con unas" :
                                "No hay conversaciones cerradas";
                        txvMessage.setText(message);
                        errorView.setVisibility(View.VISIBLE);
                    }


                    fab.setVisibility( isActiveTab ? View.VISIBLE : View.GONE );
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(),CreateConversationActivity.class);
                            startActivityForResult(intent, 1);
                        }
                    });

                    loadingView.setVisibility(View.GONE);
                    swipeRefreshLayout.setOnRefreshListener(ConversationFragment.this);
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void error(VolleyError msg) {

                    try {
                        MyException exception = handlerException.getException(msg);
                        Log.d("ERROR",exception.toString());
                        TextView txvMessage = view.findViewById(R.id.mdl_error_message);
                        ImageView imgIcon  = view.findViewById(R.id.mdl_error_icon);
                        view.findViewById(R.id.mdl_error_btn).setVisibility(View.GONE);

                        imgIcon.setImageResource(exception.getIcon());
                        txvMessage.setText(exception.getMessage());

                        loadingView.setVisibility(View.GONE);
                        errorView.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setOnRefreshListener(ConversationFragment.this);
                        swipeRefreshLayout.setRefreshing(false);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == CreateConversationActivity.RESULT_OK) {

                if( conversations.isEmpty() ){

                    Conversation conv = (Conversation) data.getSerializableExtra("newConv");
                    conversations.add(0,conv);
                    Log.d("CONVERSATION_SIZE",String.valueOf(conversations.size()));
                    commentsAdapter = myListAdapter.new CommentsAdapter(conversations,isActiveTab);
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    rvConversation.setLayoutManager(llm);
                    rvConversation.setAdapter(commentsAdapter);

                    errorView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                }else{
                    assert data != null;
                    Conversation conv = (Conversation) data.getSerializableExtra("newConv");
                    conversations.add(0,conv);
                    commentsAdapter.notifyDataSetChanged();

                }

            }
        }
    }

    @Override
    public void onRefresh() {
        loadPublishments(true);
    }
}
