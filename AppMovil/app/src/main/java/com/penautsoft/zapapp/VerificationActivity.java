package com.penautsoft.zapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.penautsoft.zapapp.Networking.RequestHandler;
import java.util.HashMap;
import java.util.Map;

public class VerificationActivity extends AppCompatActivity implements Dialogs.LoadingResponseDialog.ResponseCallBack{

    private LinearLayout numbersView;
    private TextView[] txvNumbers;
    private TextView txvPhonePlaceHolder;
    private char[] numberList;
    private String phone = "6673474353";
    private int size = 0, idUser;
    private Dialogs.LoadingResponseDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        setSupportActionBar((Toolbar) findViewById(R.id.act_veri_tool));

        new Thread(new Runnable() {
            @Override
            public void run() {
                initComponents();

                phone = getIntent().getStringExtra("typedPhone");
                idUser = getIntent().getIntExtra("idUser",-1);

                final String text = getString(R.string.number_holder,phone != null ? phone : "");
                final SpannableString spannable = new SpannableString(text);
                if( phone != null)
                    spannable.setSpan(new StyleSpan(Typeface.BOLD),text.length()-10,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                txvPhonePlaceHolder.post(new Runnable() {
                    @Override
                    public void run() {
                        txvPhonePlaceHolder.setText(spannable,TextView.BufferType.SPANNABLE);
                    }
                });
            }
        }).start();
    }

    private void initComponents(){
        numbersView = findViewById(R.id.act_veri_number_list);
        txvPhonePlaceHolder = findViewById(R.id.act_veri_phone_place);
        txvNumbers = new TextView[5];
        numberList = new char[5];

        LinearLayout view;
        for (int i = 0; i < numbersView.getChildCount(); i++) {
            view = (LinearLayout) numbersView.getChildAt(i);
            txvNumbers[i] = (TextView) view.getChildAt(0);
        }
    }

    private void popActivity(){
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if( id == android.R.id.home)
            popActivity();
        return super.onOptionsItemSelected(item);
    }

    public void typeNumber(View view){
        if( size < 5){
            TextView txv = (TextView) view;
            String txt = txv.getText().toString();
            numberList[size] = txt.charAt(0);
            txvNumbers[size].setText(txt);
            size++;
        }
    }

    public void deleteNumber(View view){
        if( size > 0){
            size--;
            numberList[size] = ' ';
            txvNumbers[size].setText("");
        }
    }

    public void verificate(View view){
        String msg = "Codigo Ingresado: "+String.valueOf(numberList);
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        if( size == 5) {
            dialog = Dialogs.LoadingResponseDialog.display(getSupportFragmentManager(), this);
            RequestHandler handler = new RequestHandler(this);
            RequestHandler.PUT postHandler = handler.put();

            try{
                String url = getResources().getString(R.string.host)
                        +"api/Register"; //Rutas Nueva/Oficial

                Map<String,Object> body = new HashMap<>();
                body.put("idUsuario",idUser);
                body.put("codigo",String.valueOf(numberList));

                postHandler.verifyAccount(url, body, new RequestHandler.callBack() {
                    @Override
                    public void respuesta(Object response) {

                        Map<String,Object> data = (Map<String, Object>) response;

                        boolean successRegister = (boolean) data.get("status");

                        Object[] bundle = new Object[6];
                        if( !successRegister ){
                            bundle[0] = "Cerrar";
                            bundle[1] = "No se pudo verificar el numero";
                            bundle[2] = R.drawable.dialog_top_icon_error;
                        }else{

                            bundle[0] = "Ir al login";
                            bundle[1] = "Cuenta verificada exitasamente";
                            bundle[2] = R.drawable.dialog_top_icon_success;
                        }

                        dialog.showResponse(successRegister,bundle);
                    }

                    @Override
                    public void error(VolleyError msg) {
                        Toast.makeText(VerificationActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        popActivity();
    }

    @Override
    public void onTouch(boolean sucess) {
        if( sucess ) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }else{
            dialog.dismissDialog();
        }
    }
}