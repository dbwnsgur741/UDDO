package com.example.customlistview;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    private TimerTask TimerTask ;
    private ImageButton ib;
    private static Boolean flag = true;
    private static int sp_quiz_num ;
    private TextView timer_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.always_quiz_default);

        Toolbar toolbar = (Toolbar) findViewById(R.id.always_quiz_toolbar_top);
        TextView toolbar_text = (TextView)findViewById( R.id.always_quiz_toolbar_text );
        toolbar_text.setText( "지피지기면 백전백승" );
        setSupportActionBar(toolbar);

        ib = (ImageButton) findViewById(R.id.next_step);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_detail = new Intent(getApplicationContext(),A_Quiz_Detail_Activity.class);
                startActivityForResult(intent_detail,1000);
            }
        });



        // TODO : 툴바 텍스트 적용 및 레이아웃 정리 ( A_QUIZ)
        sharedPreferences = getSharedPreferences("NamSan",MODE_PRIVATE);
        sp_quiz_num = sharedPreferences.getInt( "Quiz1",0 );

        timer_check = sharedPreferences.getLong( "Timer",0 );
        setTimer_check( timer_check );

        timer_textview = (TextView)findViewById( R.id.timer_textview );
        tmr = (TextView) findViewById(R.id.timer_layout);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        if(sp_quiz_num > 10){
            timer_textview.setText( "모든 문제를 풀었습니다!" );
            ib.setVisibility( View.INVISIBLE );
        }
    }
    /*
    private void setTimer_check(final Long time){
        if(time > 0 ){
            ib.setVisibility( View.GONE );
            ntimer = new Timer();
            TimerTask = new TimerTask() {
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
                        TimerTask.cancel();
                        tmr.setText("퀴즈풀기!");
                    }
                }
            };
            ntimer.schedule(TimerTask,0,1000);
        }
        else{
            ib.setVisibility( View.VISIBLE );
        }
    }
    */

    private void setTimer_check(final Long time) {
        final Handler handler = new Handler();
        if (time > 0) {
            ib.setVisibility( View.GONE );
            final Runnable runnableCode = new Runnable() {
                @Override
                public void run() {
                    long TIME_NOW = System.currentTimeMillis();
                    int TIMER = 1;
                    int temp = ((int) (TIME_NOW - time)) / 1000;
                    int temp2 = TIMER - temp;
                    int min = temp2 / 60;
                    int sec = temp2 % 60;

                    if (temp2 < 0) {
                        tmr.setText( "퀴즈풀기!" );
                        ib.setVisibility( View.VISIBLE );
                        handler.removeCallbacks( this );
                    }
                    else {
                        if (String.valueOf( sec ).length() == 1) {
                            tmr.setText( String.format( "0%d:0%d", min, sec ) );
                        } else {
                            tmr.setText( String.format( "0%d:%d", min, sec ) );
                        }
                    }
                    handler.postDelayed( this, 1000 );
                }
            };
            handler.post( runnableCode );
        } else {
            ib.setVisibility( View.VISIBLE );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sp_quiz_num < 10){
            setTimer_check( timer_check );
        }else{
            timer_textview.setText( "모든 문제를 풀었습니다!" );
            ib.setVisibility( View.INVISIBLE );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("resultCode1111111", String.valueOf( resultCode ) );
        if(requestCode==1000){
                setResult(2000);
                finish();
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