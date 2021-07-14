package com.penautsoft.zapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.penautsoft.zapapp.util.Util;

public class Profile extends AppCompatActivity {


    private TextView txvUserName,txvPhone,txvEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        initComponents();
        setSupportActionBar((Toolbar) findViewById(R.id.act_prof_tool));
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final SharedPreferences shared = getSharedPreferences(getResources().getString(R.string.SharedPrefKey), Context.MODE_PRIVATE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                txvEmail.post(new Runnable() {
                    @Override
                    public void run() {
                        String email = shared.getString("cliEmail","Sin Correo");
                        txvEmail.setText( !email.equals("") ? email : "Sin Correo");
                        String phone = shared.getString("cliPhone",null);
                        phone = Util.formatPhone(phone);
                        txvPhone.setText(phone);
                        String fullName = shared.getString("cliName","NaN") + " " + shared.getString("cliLastName","NaN");

                        txvUserName.setText(fullName);
                    }
                });
            }
        }).start();
    }


    private void initComponents(){
        txvEmail = findViewById(R.id.prof_email);
        txvUserName = findViewById(R.id.prof_name);
        txvPhone = findViewById(R.id.prof_phone);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if( id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}