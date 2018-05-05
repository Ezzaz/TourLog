package com.tour_log.tourlog.introSlider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tour_log.tourlog.MainActivity;
import com.tour_log.tourlog.R;
import com.tour_log.tourlog.login.LoginActivity;

public class IntroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout layoutDot;
    private TextView[] dotsTV;
    private int[] layouts;
    private Button btnNext, btnSkip;
    private IntroAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isFirstTimeStart()){
            startLoginActivity();
            finish();
        }
        setStatusBarTransparent();

        setContentView(R.layout.activity_intro);

        viewPager = (ViewPager) findViewById(R.id.welcome_view_pager);
        layoutDot = findViewById(R.id.dot_layout);
        btnNext=(Button) findViewById(R.id.btn_next_slider);
        btnSkip=(Button) findViewById(R.id.btn_skip_slider);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem()+1;

                if(currentPage < layouts.length){
                    viewPager.setCurrentItem(currentPage);
                }
                else {
                    startLoginActivity();
                }

            }
        });

        layouts = new int[]{R.layout.slider_1,R.layout.slider_2,R.layout.slider_3,R.layout.slider_4};
        pagerAdapter =  new IntroAdapter(layouts,getApplicationContext());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == layouts.length-1){
                    btnNext.setText("START");
                    btnSkip.setVisibility(View.GONE);
                }
                else{
                    btnNext.setText("NEXT");
                    btnSkip.setVisibility(View.VISIBLE);
                }
                setDotStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDotStatus(0);



    }
    private void startLoginActivity() {
        setFirstTimeStartStatus(false);
        startActivity(new Intent(IntroActivity.this,LoginActivity.class));
        finish();
    }
    private boolean isFirstTimeStart(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("IntroSlider", Context.MODE_PRIVATE);
        return (preferences.getBoolean("FirstTimeStart",true));
    }

    private void setFirstTimeStartStatus( boolean status){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("IntroSlider", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("FirstTimeStart",status);
        editor.commit();
    }

    private void setStatusBarTransparent(){
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window =  getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    private void setDotStatus(int page){
        layoutDot.removeAllViews();
        dotsTV = new TextView[layouts.length];

        for(int i = 0 ; i< dotsTV.length;i++){
            dotsTV[i] = new TextView(this);
            dotsTV[i].setText(Html.fromHtml("&#8226;"));
            dotsTV[i].setTextSize(30);
            dotsTV[i].setTextColor(Color.parseColor("#a9b4bb"));
            layoutDot.addView(dotsTV[i]);
        }

        if(dotsTV.length >0){
            dotsTV[page].setTextColor(Color.parseColor("#ffffff"));

        }
    }
}
