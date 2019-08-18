package com.example.customlistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class QR_Code_Default_Activity extends AppCompatActivity {

    ImageButton imageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.qr_code_default_main );

        Toolbar toolbar = (Toolbar) findViewById(R.id.qr_code_default_toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        imageButton = (ImageButton)findViewById( R.id.qr_code_default_btn );
        imageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),QR_Code_Default_Sub_Activity.class );
                startActivity( intent );
                finish();
            }
        } );
    }

}
