package com.penautsoft.zapapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.penautsoft.zapapp.Adapters.SlideAdapter;

public class OnBoardActivity extends AppCompatActivity {

    private ViewPager pager;
    private LinearLayout dotsLayout;
    private Button btnLogin,btnSingUp,btnSkip,btnNext;
    private SlideAdapter.ViewPager slideAdapter;
    private int currentPage = 0;
    private TextView[] dots;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboard_activity);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SharedPrefKey), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        initComp();
        pager.setAdapter(slideAdapter);
        pager.addOnPageChangeListener(pageChangeListener);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(dots.length-1);
                btnLogin.setText("Comenzar");
                btnLogin.setVisibility(View.VISIBLE);
                btnSingUp.setVisibility(View.GONE);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(currentPage + 1);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("boarded",true);
                editor.apply();
                intent = new Intent(OnBoardActivity.this,Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                startActivity(intent);
            }
        });
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("boarded",true);
                editor.apply();
                intent = new Intent(OnBoardActivity.this,RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

    }

    private void initComp(){
        pager = findViewById(R.id.onboard_pager);
        dotsLayout = findViewById(R.id.onboard_dots_layout);
        btnSkip = findViewById(R.id.onboard_btn_skip);
        btnNext = findViewById(R.id.onboard_btn_next);
        slideAdapter = new SlideAdapter.ViewPager(this);
        btnLogin = findViewById(R.id.onboard_btn_login);
        btnSingUp = findViewById(R.id.onboard_btn_singup);

        addDots(0);
        addButtons(0);
    }

    private void addDots(int current){
        dots = new TextView[3];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(45);
            dots[i].setTextColor(getResources().getColor(R.color.Creme_Brulee));
            dotsLayout.addView(dots[i]);
        }

        if( dots.length > 0){
            dots[current].setTextColor(getResources().getColor(R.color.Whiskey));
        }
    }
    private void addButtons(int pos){
        if( pos == 0){
            btnLogin.setText("Iniciar Sesion");
            btnLogin.setVisibility(View.VISIBLE);
            btnSingUp.setVisibility(View.VISIBLE);
        }else if( pos == 1){
            btnLogin.setVisibility(View.INVISIBLE);
            btnSingUp.setVisibility(View.INVISIBLE);
        }else{
            btnLogin.setText("Comenzar");
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    private void disableBottomButtons(boolean status){
        String str1 = "", str2 = "";

        if( status ){
            str1 = "Saltar";
            str2 = "Siguiente";
        }

        btnSkip.setText(str1);
        btnSkip.setEnabled(status);
        btnNext.setText(str2);
        btnNext.setEnabled(status);
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            addButtons(position);
            currentPage = position;

            if( position == dots.length - 1){
                disableBottomButtons(false);
            }else{
                disableBottomButtons(true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };
}
