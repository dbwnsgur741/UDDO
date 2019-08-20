package com.example.customlistview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class A_Quiz_False_Acitivity extends AppCompatActivity {

    private ImageView go_to_main;
    private ImageView wait_quiz;
    private TextView timer_text;
    private SharedPreferences sharedPreferences;
    private Long timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.always_quiz_false );

        Toolbar toolbar = (Toolbar) findViewById(R.id.always_quiz_false_toolbar_top);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        sharedPreferences = getSharedPreferences( "NamSan",MODE_PRIVATE );
        timer = sharedPreferences.getLong( "Timer",0 );

        go_to_main = (ImageView)findViewById( R.id.false_GoMain );
        wait_quiz = (ImageView)findViewById( R.id.false_WaitQuiz );
        timer_text = (TextView)findViewById( R.id.false_timer_view );
        timer_text.setText( "" );
        setTimer_check( timer );

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
        } );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void setTimer_check(final Long time) {
        final Handler handler = new Handler();
        if (time > 0) {
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
                        timer_text.setText( "퀴즈풀기!" );
                        handler.removeCallbacks( this );
                    }
                    else {
                        if (String.valueOf( sec ).length() == 1) {
                            timer_text.setText( String.format( "0%d:0%d", min, sec ) );
                        } else {
                            timer_text.setText( String.format( "0%d:%d", min, sec ) );
                        }
                    }
                    handler.postDelayed( this, 1000 );
                }
            };
            handler.post( runnableCode );
        }
    }
}
