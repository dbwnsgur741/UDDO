package com.example.customlistview;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class TrainMissionActivity extends AppCompatActivity {

    private ImageButton imageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.trainmission_default );

        Toolbar toolbar = (Toolbar) findViewById(R.id.msq_default_toolbar);
        TextView toolbar_text = (TextView) findViewById( R.id.msq_default_toolbar_text );
        toolbar_text.setText( "대결 경쟁을 통해 참다운 의병이 되어라!" );
        setSupportActionBar(toolbar);
        imageButton = (ImageButton)findViewById( R.id.msq_default_btn );

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        imageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), PopupActivity.class );
                startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();
    }
}
