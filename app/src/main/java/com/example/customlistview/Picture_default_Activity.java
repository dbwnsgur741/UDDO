package com.example.customlistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class Picture_default_Activity extends AppCompatActivity  {

    private GridView gridView ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.picture_mission_default );

        Toolbar toolbar = (Toolbar)findViewById( R.id.picture_mission_default_toolbar );
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        gridView = (GridView) findViewById( R.id.grid_view );
        gridView.setAdapter( new ImageAdapter(this ) );
        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Picture_detail_Activity.class);
                intent.putExtra( "position",position );
                startActivityForResult(intent,2222);
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(resultCode == 2222){
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
