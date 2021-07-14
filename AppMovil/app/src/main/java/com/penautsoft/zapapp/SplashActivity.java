package com.penautsoft.zapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {

    private static final int DELAY =  2000;
    private Animation topAnim;
    private static final String TAG = "SPLAHS";
    SharedPreferences sharedPreferences;
    private int GPS_REQUEST_CODE = 1;
    private GoogleApiAvailability api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences(getString(R.string.SharedPrefKey), Context.MODE_PRIVATE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        Animation bottomAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);

        ImageView logo = findViewById(R.id.act_splash_logo);
        TextView txvAppName = findViewById(R.id.act_splash_app_name);

        logo.setAnimation(topAnim);
        txvAppName.setAnimation(bottomAnim);
        checkGooglePlayServices();
    }

    private boolean checkNotifi(){
        int data = api.isGooglePlayServicesAvailable(this);

        return data == ConnectionResult.SUCCESS;
    }

    void checkGooglePlayServices() {
        api = GoogleApiAvailability.getInstance();
        int status = api.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (api.isUserResolvableError(status)) {
                Dialog dialog = api.getErrorDialog(this, status, GPS_REQUEST_CODE,
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                finish();
                            }
                        });
                dialog.show();
            } else {
                Toast.makeText(this,"PITO",Toast.LENGTH_LONG).show();
            }
        }else{
               if( checkNotifi() ){
                   FirebaseMessaging.getInstance().getToken()
                           .addOnCompleteListener(new OnCompleteListener<String>() {
                               @Override
                               public void onComplete(@NonNull Task<String> task) {
                                   if (!task.isSuccessful()) {
                                       Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                       return;
                                   }

                                   String token = task.getResult();
                                   Log.d(TAG, token);

                                   new Handler().postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           boolean execute = sharedPreferences.getBoolean("boarded",false);

                                           if( !execute ){
                                               startActivity(new Intent(SplashActivity.this,OnBoardActivity.class));
                                           }else {
                                               Intent intent = null;
                                               if (sharedPreferences.getBoolean("Logged", false)) {
                                                   intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                                               } else {
                                                   intent = new Intent(SplashActivity.this, Login.class);
                                                   intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                               }
                                               startActivity(intent);
                                           }
                                       }
                                   }, DELAY);
                               }
                           });
               }else
                   Log.d("BBBbBBBB","Sin servicios de google");
        }
    }
}