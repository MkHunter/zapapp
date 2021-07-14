package com.penautsoft.zapapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputLayout;
import com.penautsoft.zapapp.Networking.RequestHandler;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements Dialogs.LoadingResponseDialog.ResponseCallBack{
    
    private TextInputLayout tilName,tilLastName,tilEmail,tilPass,tilConffPass,tilPhone;
    private Button btnRegister;
    private String name,lastName,email,password,conffPassword,phone;
    private Dialogs.LoadingResponseDialog dialog;
    private Map<String,Object> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form_alt_2);
        
        initComp();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( validateForm() ) {
                    dialog = Dialogs.LoadingResponseDialog.display(getSupportFragmentManager(),RegisterActivity.this);
                    RequestHandler handler = new RequestHandler(RegisterActivity.this);
                    RequestHandler.POST postHandler= handler.post();
                    try{
                        String url = getResources().getString(R.string.host)
                                +"api/Register"; //Rutas Nueva/Oficial

                        Map<String,Object> body = new HashMap<>(), entity = new HashMap<>();
                        entity.put("nombre",name);
                        entity.put("apellido",lastName);
                        entity.put("telefono",phone);
                        entity.put("correo",email);
                        body.put("contraseña",password);
                        body.put("entidad",entity);
                        body.put("rol",1);

                        postHandler.createClient(url, body, new RequestHandler.callBack() {
                            @Override
                            public void respuesta(Object response) {

                                data = (Map<String, Object>) response;

                                boolean successRegister = (boolean) data.get("status");

                                if( !successRegister ){
                                    dialog.showResponse(false);
                                }else{
                                    dialog.showResponse(true);
                                }
                            }

                            @Override
                            public void error(VolleyError msg) {
                                Toast.makeText(RegisterActivity.this,"Error",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    
    private void initComp(){
        tilName = findViewById(R.id.register_til_name);
        tilLastName = findViewById(R.id.register_til_last_name);
        tilEmail = findViewById(R.id.register_til_email);
        tilPass = findViewById(R.id.register_til_pass);
        tilConffPass = findViewById(R.id.register_til_pass_conff);
        tilPhone = findViewById(R.id.register_til_phone);

        btnRegister = findViewById(R.id.register_btn_register);
    }

    private boolean validateForm(){
        boolean isBlank = false,hasError = false;

        tilName.setErrorEnabled(false);
        tilLastName.setErrorEnabled(false);
        tilEmail.setErrorEnabled(false);
        tilPass.setErrorEnabled(false);
        tilConffPass.setErrorEnabled(false);
        tilPhone.setErrorEnabled(false);

        name = tilName.getEditText().getText().toString();
        lastName = tilLastName.getEditText().getText().toString();
        email = tilEmail.getEditText().getText().toString();
        password = tilPass.getEditText().getText().toString();
        conffPassword = tilConffPass.getEditText().getText().toString();
        phone = tilPhone.getEditText().getText().toString();

        if( name.equals("") ){
            tilName.setError("No puede ser blanco");
            tilName.setErrorEnabled(true);
            isBlank = true;
        }

        if( lastName.equals("") ){
            tilLastName.setError("No puede ser blanco");
            tilLastName.setErrorEnabled(true);
            isBlank = true;
        }

        if( phone.equals("") ){
            tilPhone.setError("No puede ser blanco");
            tilPhone.setErrorEnabled(true);
            isBlank = true;
        }
        if( !email.equals("") )
            if( !email.contains("@") ){
                tilEmail.setError("Correo Invalido");
                tilEmail.setErrorEnabled(true);
            }

        if( password.equals("") ){
            tilPass.setError("No puede ser blanco");
            tilPass.setErrorEnabled(true);
            isBlank = true;
        }

        if( conffPassword.equals("") ){
            tilConffPass.setError("No puede ser blanco");
            tilConffPass.setErrorEnabled(true);
            isBlank = true;
        }


        if( !isBlank ){
            if( !password.equals(conffPassword)){
                tilConffPass.setError("Las contraseñas no coinciden");
                tilConffPass.setErrorEnabled(true);
                hasError = true;
            }

            if( phone.length() < 10){
                tilPhone.setError("Numero invalido");
                tilPhone.setErrorEnabled(true);
                hasError = true;
            }
        }
        return isBlank == hasError;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    @Override
    public void onTouch(boolean sucess) {
        if( sucess ) {
            Intent intent = new Intent(RegisterActivity.this, VerificationActivity.class);
            intent.putExtra("idUser",(int) data.get("userId"));
            intent.putExtra("typedPhone",phone);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }else{
            dialog.dismissDialog();
        }
    }
}
