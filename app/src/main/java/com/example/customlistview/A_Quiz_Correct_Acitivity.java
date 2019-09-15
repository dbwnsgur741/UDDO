package com.example.customlistview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class A_Quiz_Correct_Acitivity extends AppCompatActivity {

    private ImageView go_to_main;
    private ImageView wait_quiz;
    private TextView timer_text;
    private SharedPreferences sharedPreferences;
    private Long savedTime;
    private Handler handler;
    private final long currentTime = System.currentTimeMillis();
    // long currentTime : should be set when class started or it can be changed at every call
    private long maxTime;
    public MySingleton mySingleton;
    private TextView answer_desc;
    private Runnable runnableCode;
    private boolean runnableKill = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.always_quiz_correct );

        /******* Layout Setting*******/
        //// Tool bar Setting
        Toolbar toolbar = (Toolbar) findViewById(R.id.always_quiz_correct_toolbar_top);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        //// End Of Tool bar Setting
        answer_desc = (TextView)findViewById( R.id.always_quiz_correct_answer_desc );
        go_to_main = (ImageView) findViewById( R.id.correct_GoMission );
        wait_quiz = (ImageView) findViewById( R.id.correct_WaitQuiz );
        timer_text = (TextView)findViewById( R.id.correct_timer_view );
        timer_text.setText( "" );
        mySingleton = MySingleton.getInstance(this);
        /******* End of Layout Setting*******/

        /******* Event Setting *******/
        go_to_main.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                setResult( 1000 );
                finish();
            }
        });
        wait_quiz.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), A_Quiz_Default_Activity.class );
                startActivity( intent );
                finish();
            }
        });
        /******* End of event Setting *******/

        sharedPreferences = getSharedPreferences( "NamSan",MODE_PRIVATE );
        sharedPreferences.edit().remove("Timer").commit();
        sharedPreferences.edit().putLong("Timer", currentTime).apply();
        mySingleton.setQuiz_answer_desc( sharedPreferences.getInt( "Quiz1",0 ) -1);
        this.answer_desc.setText( mySingleton.getQuiz_answer_desc() );
        Log.v("Timer",sharedPreferences.getLong("Timer", currentTime)+" ");

        String timeValue = getResources().getString(R.string.timerValue);
        if(timeValue!=null) {
            maxTime = Long.parseLong(timeValue);
        }
        handler = new Handler();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTimer_check();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPause() {
        runnableKill=true;
        super.onPause();
    }

    /*******
     *        void setTiemr_check()
     *        summary : set and start the timer if it's not started (right after quiz)
     *         if it's started resume the timer and refresh the textview
     *********/
    private void setTimer_check() {

        savedTime = sharedPreferences.getLong("Timer", currentTime);

        if (currentTime - savedTime  > maxTime){ // over 5 mins
            timer_text.setText( "퀴즈풀기!" );
            sharedPreferences.edit().remove("Timer").commit();
            runnableKill=true;
            return;
        }

        if (currentTime == savedTime){
            sharedPreferences.edit().putLong("Timer", currentTime).apply();
        }
        runnableKill=false;
        runnableCode = new Runnable() {
            @Override
            public void run() {
                long TIME_NOW = System.currentTimeMillis();
                int timeGab = ((int) (TIME_NOW - savedTime)) / 1000;
                int remainTimeSec = (int)( maxTime/1000) - timeGab;
                int min = remainTimeSec / 60;
                int sec = remainTimeSec % 60;

                if (TIME_NOW - savedTime  > maxTime){ // over 5 mins
                    timer_text.setText( "퀴즈풀기!" );
                    sharedPreferences.edit().remove("Timer").apply();
                    runnableKill=true;
                } else {
                    if (String.valueOf( sec ).length() == 1) {
                        timer_text.setText( String.format( "0%d:0%d", min, sec ) );
                    } else {
                        timer_text.setText( String.format( "0%d:%d", min, sec ) );
                    }
                }
                if(!runnableKill)
                handler.postDelayed( this, 1000 );
            }
        };
        handler.post( runnableCode );
    }
}