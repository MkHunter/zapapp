package com.penautsoft.zapapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lopei.collageview.CollageView;
import com.penautsoft.zapapp.Entity.Comment;
import com.penautsoft.zapapp.Entity.Conversation;
import com.penautsoft.zapapp.Entity.MyException;
import com.penautsoft.zapapp.Networking.HandlerException;
import com.penautsoft.zapapp.Networking.RequestHandler;
import com.penautsoft.zapapp.util.Util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PublishmentDetailActivity extends AppCompatActivity implements Dialogs.LoadingResponseDialog.ResponseCallBack {

    private TextView txvMessage,txvDate,txvUser;
    private View loadginView,errorView,writeView;
    private ImageButton ingBtnSendComment;
    private EditText edtWriteComment;
    private Conversation conv;
    private LinearLayout linearLayout;
    private ScrollView scv;
    private RequestHandler.PUT putHandler;
    private RequestHandler.GET getHanfler;
    private boolean isActive;
    private Map<String,String> header;
    private Dialogs.LoadingResponseDialog dialog;
    private SharedPreferences sha;
    private Comment comme;
    //
    private CollageView collageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_detail);

        init();

        setSupportActionBar((Toolbar) findViewById(R.id.publish_det_tool));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent inte = getIntent();

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.SharedPrefKey),Context.MODE_PRIVATE);

        if( inte != null){
            conv = (Conversation) getIntent().getSerializableExtra("obj");
            isActive = inte.getBooleanExtra("active",false);
            sha = getSharedPreferences(getResources().getString(R.string.SharedPrefKey), Context.MODE_PRIVATE);
            header = new HashMap<>();
            header.put("Authorization","Bearer "+sha.getString("token",null));
        }


        if( !isActive )
            writeView.setVisibility(View.GONE);

        try{
            txvMessage.setText(conv.getPostPrincipal());
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
            txvUser.setText(sharedPreferences.getString("cliName", null) + " " + sharedPreferences.getString("cliLastName", null));
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            txvDate.setText(Util.DateManagement.formattedDate(new SimpleDateFormat().parse(conv.getFechaPost())));
        }catch (Exception e){
            e.printStackTrace();
        }

        getComments();

        linearLayout = findViewById(R.id.mdl_publish_comments);

        ingBtnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
            }
        });
    }

    private void init(){
        txvMessage = findViewById(R.id.mdl_publish_description);
        txvDate = findViewById(R.id.mdl_publish_date);
        txvUser = findViewById(R.id.mdl_publish_user);
        loadginView = findViewById(R.id.mdl_publis_loading);
        errorView = findViewById(R.id.mdl_publis_error);
        loadginView.setVisibility(View.VISIBLE);
        edtWriteComment = findViewById(R.id.publis_det_message);
        ingBtnSendComment = findViewById(R.id.publis_det_send);
        writeView = findViewById(R.id.publish_det_write);
        scv = findViewById(R.id.publish_scroll);

        RequestHandler handler = new RequestHandler(this);
        putHandler = handler.put();
        getHanfler = handler.get();
        collageView = findViewById(R.id.collageView);
    }

    private void addComment(){
        if( !edtWriteComment.getText().toString().equals("") ){

            dialog = Dialogs.LoadingResponseDialog.display(getSupportFragmentManager(),PublishmentDetailActivity.this);
            final Comment comm = new Comment(edtWriteComment.getText().toString(), Calendar.getInstance().getTime().toString(),0,0);

            String  host = getResources().getString(R.string.host),
                    fullUrl = host + "api/Conversaciones";

            Map<String,Object> body = new HashMap<>(),jsonProv = new HashMap<>();
            body.put("idHilo",conv.getIdHilo());

            jsonProv.put("mensaje",comm.getMensaje());
            jsonProv.put("usuario",1);

            body.put("conversacion",jsonProv);

            putHandler.putCommentsById(fullUrl, header, body, new RequestHandler.callBack() {
                @Override
                public void respuesta(Object response) {
                    Log.d(PublishmentDetailActivity.class.getSimpleName(),response.toString());
                    Gson gson = new Gson();

                    try{
                        comme = gson.fromJson((String) response, Comment.class);
                        comme.setMensaje(comm.getMensaje());
                        Log.w(PublishmentDetailActivity.class.getCanonicalName(),comme.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    final View view = LayoutInflater.from(PublishmentDetailActivity.this).inflate(R.layout.model_comment, null);
                    TextView txvDate = view.findViewById(R.id.mdl_comme_date);
                    TextView txvMess = view.findViewById(R.id.mdl_comme_message),
                            txvUser = view.findViewById(R.id.mdl_comme_username);

                    txvUser.setText(
                            sha.getString("cliName", null) + " " + sha.getString("cliLastName", null)
                    );
                    txvMess.setText(comme.getMensaje());

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    try {
                        txvDate.setText(Util.DateManagement.dateView(sdf.parse(comme.getFechaConversacion()),sdf.parse(comme.getFechaConversacion()),5));
                    } catch (Exception e) {
                        e.printStackTrace();
                        txvDate.setText(conv.getFechaPost().split(" ")[0]);
                    }


                    if( !conv.isCommentsEmpty() ) {
                        errorView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }else{
                        errorView.setVisibility(View.GONE);
                    }

                    dialog.dismissDialog();

                    linearLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            linearLayout.addView(view);
                            conv.addComment(comme);
                            scv.fullScroll(View.FOCUS_DOWN);
                            edtWriteComment.setText("");
                        }
                    });
                }

                @Override
                public void error(VolleyError msg) {
                    try{
                        HandlerException handlerException = new HandlerException();
                        MyException exception = handlerException.getException(msg);
                        String exMess = "[ " + exception.getStatusCode() + " ] "
                                + msg.toString();

                        dialog.showResponse(false,exMess);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }else{
            Toast.makeText(this,"NO es posible, trate de escribir algo",Toast.LENGTH_SHORT).show();
        }
    }

    private void getComments(){
        String url = getResources().getString(R.string.host)
                + "api/Conversaciones/" + conv.getIdHilo();

        getHanfler.getCommentsById(url, header, null, new RequestHandler.callBack() {
            @Override
            public void respuesta(Object response) {
                Gson gson = new Gson();
                Conversation convaux = gson.fromJson((String)response,Conversation.class);
                int user = 0;

                if( !convaux.isCommentsEmpty() ){
                    for(final Comment x: convaux.getConversaciones()){
                        final View view = LayoutInflater.from(PublishmentDetailActivity.this).inflate(R.layout.model_comment, null);
                        TextView txvDate = view.findViewById(R.id.mdl_comme_date),
                                txvMessage = view.findViewById(R.id.mdl_comme_message),
                                txvUser = view.findViewById(R.id.mdl_comme_username);
                        txvMessage.setText(x.getMensaje());
                        user = x.getUsuario();
                        if( user == sha.getInt("idUser",-1)){
                            txvUser.setText(
                                    sha.getString("cliName", null) + " " + sha.getString("cliLastName", null)
                            );
                        }else{
                            txvUser.setText("Sucursal");
                        }

                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            txvDate.setText(Util.DateManagement.dateView(sdf.parse(x.getFechaConversacion()),sdf.parse(convaux.getHoraRequest()),5));
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        linearLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                linearLayout.addView(view);
                            }
                        });
                    }
                    loadginView.post(new Runnable() {
                        @Override
                        public void run() {
                            loadginView.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        }
                    });
                    conv.setConversaciones(convaux.getConversaciones());
                }else{
                    final View view = LayoutInflater.from(PublishmentDetailActivity.this).inflate(R.layout.model_error, null);
                    ImageView imgIcon = findViewById(R.id.mdl_error_icon);
                    TextView txvMessage = findViewById(R.id.mdl_error_message);
                    findViewById(R.id.mdl_error_btn).setVisibility(View.GONE);

                    txvMessage.setText("No hay comentarios");
                    txvMessage.setTextColor(Color.BLACK);
                    imgIcon.setImageResource(R.drawable.ic_accessibility_24);
                    imgIcon.setColorFilter(Color.BLACK);
                    loadginView.post(new Runnable() {
                        @Override
                        public void run() {
                            loadginView.setVisibility(View.GONE);
                            errorView.setVisibility(View.VISIBLE);

                        }
                    });
                }

                conv.setImagenPostPrincipal(convaux.getImagenPostPrincipal());

                if( !conv.isPhotosEmpty() ){

                    collageView
                            .photoMargin(1)
                            .photoPadding(3)
                            .defaultPhotosForLine(4) //columnas por defecto en cada linea
                            .maxWidth(800)
                            .placeHolder(R.drawable.ic_en_desarrollo)
                            .photosForm(CollageView.ImageForm.IMAGE_FORM_SQUARE)
                            .loadPhotos(conv.getImagenPostPrincipal());
                    collageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void error(VolleyError msg) {

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == android.R.id.home) {
            onBackPressed();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTouch(boolean sucess) {

    }
}
