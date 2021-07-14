package com.penautsoft.zapapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.VolleyError;
import com.penautsoft.zapapp.Entity.MyException;
import com.penautsoft.zapapp.Networking.HandlerException;
import com.penautsoft.zapapp.Networking.RequestHandler;
import com.google.android.material.textfield.TextInputLayout;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private TextInputLayout tilNumero, tilPass;
    private TextView txvForgot,txvErrorMsg;
    private Button btnLogin,btnRegister;
    private Intent intent;
    private ImageView showPass;
    private boolean isPassVisible = false;
    private RequestHandler requestHandler;
    private SharedPreferences sharedPreferences;
    private HandlerException handlerException;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestHandler = new RequestHandler(this);
        sharedPreferences = getSharedPreferences(getString(R.string.SharedPrefKey), Context.MODE_PRIVATE);

        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        txvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this,"En desarrollo",Toast.LENGTH_SHORT).show();
            }
        });

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passVisibility();
            }
        });
    }

    private void init(){
        tilNumero = findViewById(R.id.login_layout_nocliente);
        tilPass = findViewById(R.id.login_layout_contra);
        txvForgot = findViewById(R.id.login_forgotten_pass);
        btnLogin = findViewById(R.id.login_btn_ingresar);
        btnRegister = findViewById(R.id.login_btn_registrar);
        showPass = findViewById(R.id.login_icon_show_pass);
        txvErrorMsg = findViewById(R.id.act_login_errorMsg);
        handlerException = new HandlerException();
    }

    public void registrar(){
        if( validaFormulario())
            login();
        else
            Toast.makeText(Login.this,"Por favor, revise sus datos",Toast.LENGTH_SHORT).show();

    }

    private void login(){
        try{
            final Dialogs.LoadingResponseDialog dialog = Dialogs.LoadingResponseDialog.display(getSupportFragmentManager());
            txvErrorMsg.setVisibility(View.GONE);
            txvErrorMsg.setText("");

            RequestHandler.POST postHandler = requestHandler.post();
            String url = getResources().getString(R.string.host) +
                        "api/Login";

            Map<String,Object> body = new HashMap<>();
            body.put("usuario",tilNumero.getEditText().getText().toString());
            body.put("contraseña",tilPass.getEditText().getText().toString());


            postHandler.loginManagement(url, body, null, new RequestHandler.callBack() {
                @Override
                public void respuesta(Object response) {

                    Map<String,Object> object = (Map<String, Object>) response;

                    boolean successLogin = (boolean) object.get("successful");

                    if( !successLogin){
                        dialog.dismissDialog();
                        txvErrorMsg.setText("Contraseña y/o usuario invalidos");
                        txvErrorMsg.setVisibility(View.VISIBLE);
                    }else{
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("Logged",true);
                        editor.apply();
                        intent = new Intent(Login.this,DashBoardActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void error(VolleyError msg) {
                    try {
                        MyException exception = handlerException.getException(msg);
                        txvErrorMsg.setText(exception.getMessage());
                        txvErrorMsg.setVisibility(View.VISIBLE);
                        dialog.dismissDialog();
                    }catch (Exception e){
                        e.printStackTrace();
                        dialog.dismissDialog();
                    }

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void passVisibility(){
        String pass = tilPass.getEditText().getText().toString();
        if (isPassVisible) {
            tilPass.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
            showPass.setImageResource(R.drawable.ic_baseline_visibility);
        } else {
            tilPass.getEditText().setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showPass.setImageResource(R.drawable.ic_baseline_visibility_off);
        }
        tilPass.getEditText().setSelection(pass.length());
        isPassVisible= !isPassVisible;
    }

    private boolean validaFormulario() {
        boolean output = true;

        if(  tilNumero.getEditText().getText().toString().equals("")){
            tilNumero.setError("No deje en blanco");
            tilNumero.setErrorEnabled(true);
            output = false;
        }else{
            tilNumero.setError("");
            tilNumero.setErrorEnabled(false);
        }

        if( tilPass.getEditText().getText().toString().equals("")){
            tilPass.setError("No deje en blanco");
            tilPass.setErrorEnabled(true);
            output = false;
        }else{
            tilPass.setError("");
            tilPass.setErrorEnabled(false);
        }
        if( output ){
            if( tilNumero.getEditText().getText().toString().contains(".")){
                tilNumero.setError("Ingrese un numero valido");
                tilNumero.setErrorEnabled(true);
                output = false;
            }else{
                tilNumero.setError("");
                tilNumero.setErrorEnabled(false);
            }
        }
        return output;
    }
}
