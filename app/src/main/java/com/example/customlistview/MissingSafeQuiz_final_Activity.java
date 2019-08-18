package com.example.customlistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class MissingSafeQuiz_final_Activity extends AppCompatActivity {

    private ImageButton msq_final_review;
    private ImageButton msq_final_go_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.missingsafequiz_final );

        msq_final_review = (ImageButton)findViewById( R.id.msq_final_review );
        msq_final_go_main = (ImageButton)findViewById( R.id.msq_final_go_main );

        Toolbar toolbar = (Toolbar) findViewById(R.id.msq_detail_toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        msq_final_review.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),MissingSafeQuiz_Detail_Activity.class );
                startActivity( intent );
                finish();
            }
        } );

        msq_final_go_main.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),MainActivity.class );
                startActivity( intent );
                finish();
            }
        } );
    }
}
