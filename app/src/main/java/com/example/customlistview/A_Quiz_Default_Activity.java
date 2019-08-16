package com.example.customlistview;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class A_Quiz_Default_Activity extends AppCompatActivity{

    private SharedPreferences sharedPreferences;
    private TextView tmr;
    private Long timer_check;
    private Timer ntimer ;
    private TimerTask TT ;
    private ImageButton ib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.always_quiz_default);

        Toolbar toolbar = (Toolbar) findViewById(R.id.always_quiz_toolbar_top);
        setSupportActionBar(toolbar);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.default_layout);

        ib = (ImageButton) findViewById(R.id.next_step);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_detail = new Intent(getApplicationContext(),A_Quiz_Detail_Activity.class);
                startActivityForResult(intent_detail,1000);
                finish();
            }
        });

        sharedPreferences = getSharedPreferences("NamSan",MODE_PRIVATE);
        timer_check = sharedPreferences.getLong( "Timer",0 );
        setTimer_check( timer_check );

        tmr = (TextView) findViewById(R.id.timer_layout);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

    }

    private void setTimer_check(final Long time){
        if(time > 0 ){
            ib.setVisibility( View.GONE );
            ntimer = new Timer();
            TT = new TimerTask() {
                @Override
                public void run() {
                    long TIME_NOW = System.currentTimeMillis();
                    int TIMER = 300;
                    int temp = ((int)(TIME_NOW - time))/1000;
                    int temp2 = TIMER - temp;

                    int min = temp2 / 60;
                    int sec = temp2 % 60;

                    if(String.valueOf(sec).length() == 1){
                        tmr.setText(String.format("0%d:0%d",min,sec));
                    }
                    else{
                        tmr.setText(String.format("0%d:%d",min,sec));
                    }
                    if(temp2 == 0){
                        TT.cancel();
                        tmr.setText("퀴즈풀기!");
                    }
                }
            };
            ntimer.schedule(TT,0,1000);
        }
        else{
            ib.setVisibility( View.VISIBLE );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TT != null){
            TT.cancel();
            setTimer_check( timer_check );
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode==1000){
                setResult(2000);
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}