package com.example.customlistview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class QR_Code_False_Activity extends AppCompatActivity {

    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.qr_code_false );

        Toolbar toolbar = (Toolbar) findViewById(R.id.qr_code_false_toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences( "NamSan",MODE_PRIVATE );
        editor = sharedPreferences.edit();

        imageButton1 = (ImageButton)findViewById( R.id.qr_code_false_retry_quiz);
        imageButton2 = (ImageButton)findViewById( R.id.qr_code_false_go_main);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        setDelete_Point();

        imageButton1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );

        imageButton2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult( 30 );
                finish();
            }
        } );
    }

    public void setDelete_Point(){
        editor.putInt("myPoint",sharedPreferences.getInt( "myPoint" ,0)-30);
        editor.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
