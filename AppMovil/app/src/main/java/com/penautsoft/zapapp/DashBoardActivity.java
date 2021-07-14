package com.penautsoft.zapapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Objects;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        shared = getSharedPreferences(getString(R.string.SharedPrefKey), Context.MODE_PRIVATE);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_sell, R.id.nav_orders,R.id.nav_sell,R.id.nav_fire_sale,R.id.nav_about_us)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.nav_log_out) {
            flushSharedPref(false);
            finish();
            Intent intent = new Intent(this,Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }else if( id == R.id.nav_reset_all){
            flushSharedPref(true);
            Intent intent = new Intent(this,OnBoardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }else if( id == R.id.nav_profile ){
            Intent intent = new Intent(this,Profile.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
        }else if( id == R.id.nav_conv){
            Intent intent = new Intent(this,CreateConversationActivity.class);
            startActivity(intent);
        }else
            NavigationUI.onNavDestinationSelected(item,navController);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void flushSharedPref(boolean fullReset){
        SharedPreferences.Editor editor = shared.edit();

        if( fullReset )
            editor.putBoolean("boarded",false);

        editor.putBoolean("Logged", false);
        editor.remove("token");
        editor.remove("idUser");
        editor.remove("name");
        editor.remove("role");
        editor.remove("cliName");
        editor.remove("cliLastName");
        editor.remove("cliPhone");
        editor.remove("cliEmail");
        editor.remove("cliAvaible");
        editor.apply();
    }
}