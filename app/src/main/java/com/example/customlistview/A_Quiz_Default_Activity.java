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
    private Long savedTime;
    private Handler handler;
    private final long currentTime = System.currentTimeMillis();
    // long currentTime : should be set when class started or it can be changed at every call
    private long maxTime;
    private TextView warningText;
    Runnable runnableCode;
    private boolean runnableKill = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.always_quiz_default);


        /******* Layout Setting*******/
        //// Tool bar Setting

        Toolbar toolbar = (Toolbar) findViewById(R.id.always_quiz_toolbar_top);
        TextView toolbar_text = (TextView)findViewById( R.id.always_quiz_toolbar_text );
        toolbar_text.setText( "지피지기면 백전백승" );
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        //// End Of Tool bar Setting

        timer_textview = (TextView)findViewById( R.id.timer_textview );
        tmr = (TextView) findViewById(R.id.timer_layout);
        ib = (ImageButton) findViewById(R.id.next_step);
        warningText = (TextView)findViewById( R.id.always_quiz_warning );
        /******* End of Layout Setting*******/

        /******* Event Setting *******/
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_detail = new Intent(getApplicationContext(),A_Quiz_Detail_Activity.class);
                startActivityForResult(intent_detail,1000);
            }
        });
        /******* End of event Setting *******/

        // TODO : 툴바 텍스트 적용 및 레이아웃 정리 ( A_QUIZ)
        sharedPreferences = getSharedPreferences("NamSan",MODE_PRIVATE);
        sp_quiz_num = sharedPreferences.getInt( "Quiz1",0 );

        String timeValue = getResources().getString(R.string.timerValue);
        if(timeValue!=null) {
            maxTime = Long.parseLong(timeValue);
        }
        handler = new Handler();
    }
    /*******
     *        void setTiemr_check()
     *        summary : set and start the timer if it's not started (right after quiz)
     *         if it's started resume the timer and refresh the textview
     *********/
    private void setTimer_check() {

        savedTime = sharedPreferences.getLong("Timer", currentTime);

        if (currentTime - savedTime  > maxTime){ // over 5 mins
            Log.v("outovertime","outovertime");
            overTimeSetting();
            return;
        }

        if (currentTime == savedTime){ // first access
            Log.v("overtimefirst","overtimefirst");
            overTimeSetting();
            return;
        }
        runnableKill=false;

        runnableCode = new Runnable() {
            @Override
            public void run() { //remain some time
                remainTimeSetting();
                long TIME_NOW = System.currentTimeMillis();
                int timeGab = ((int) (TIME_NOW - savedTime)) / 1000;
                int remainTimeSec = (int)( maxTime/1000) - timeGab;
                int min = remainTimeSec / 60;
                int sec = remainTimeSec % 60;

                if (TIME_NOW - savedTime  > maxTime){ // over 5 mins
                    overTimeSetting();
                    Log.v("overtime","overtime");

                } else {
                    if (String.valueOf( sec ).length() == 1) {
                        tmr.setText( String.format( "0%d:0%d", min, sec ) );
                    } else {
                        tmr.setText( String.format( "0%d:%d", min, sec ) );
                    }
                }
                if(!runnableKill)
                handler.postDelayed( this, 1000 );
            }
        };
        handler.post( runnableCode );
    }
    private void overTimeSetting(){
        tmr.setText( "퀴즈풀기!" );
        ib.setVisibility( View.VISIBLE );
        sharedPreferences.edit().remove("Timer").apply();
runnableKill=true;    }
    private void remainTimeSetting(){
        ib.setVisibility( View.GONE );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("Timer",sharedPreferences.getLong("Timer", currentTime)+" ");

        if(sp_quiz_num < 10){
            setTimer_check();
        }else{
            timer_textview.setText( "모든 문제를 풀었습니다!" );
            warningText.setVisibility( View.INVISIBLE );
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