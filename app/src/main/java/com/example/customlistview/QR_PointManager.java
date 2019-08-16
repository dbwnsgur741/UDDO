package com.example.customlistview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QR_PointManager extends AppCompatActivity {

    private TextView qr_txt;
    private Button btn_point;
    private AppCompatButton btn1;
    private AppCompatButton btn2;
    private boolean flag;
    private String user_answer;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.qr_pointmanager );

        Intent intent = getIntent();
        flag = (Boolean) intent.getBooleanExtra( "flag",false );
        user_answer = intent.getStringExtra( "user_answer" );

        sharedPreferences = getSharedPreferences( "NamSan",MODE_PRIVATE );
        editor = sharedPreferences.edit();

        qr_txt = (TextView)findViewById( R.id.txt_pointmanager );
        btn_point = (Button)findViewById( R.id.btn_circle_pointmanager );
        btn1 = (AppCompatButton)findViewById( R.id.qr_pointmanager_re );
        btn2 = (AppCompatButton)findViewById( R.id.qr_pointmanager_main );
        set_btn();

    }

    private void set_btn(){
        if(flag){
            editor.putInt( "myPoint",sharedPreferences.getInt( "myPoint",0 )+500 );
            editor.commit();
            qr_txt.setText( "정답입니다." );
            btn_point.setText( "+ 500 POINT" );
            btn1.setText( "확인" );
            btn2.setVisibility( View.GONE );
            btn1.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult( 20 );
                    finish();
                }
            } );
        }
        else{
            editor.putInt( "myPoint",sharedPreferences.getInt( "myPoint",0 )-30 );
            editor.commit();
            qr_txt.setText( "틀렸어요 ㅠㅠ" );
            btn_point.setText("- 30 POINT");
            btn1.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            } );
            btn2.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult( 30 );
                    finish();
                }
            } );
        }
    }
}
