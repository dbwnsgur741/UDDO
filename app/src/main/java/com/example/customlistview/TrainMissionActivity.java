package com.example.customlistview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TrainMissionActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private AppCompatButton btn30;
    private AppCompatButton btn50;
    private AppCompatButton btn100;
    private AppCompatButton btn150;
    private String admin_status;
    private LinearLayout point_layout;
    private static boolean event_check = true;
    private TextView textView;
    private Context context;
    private TextView warningtextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.trainmission_default );

        /***** Set Layout *****/

        context = this;

        //// Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.msq_default_toolbar);
        TextView toolbar_text = (TextView) findViewById( R.id.msq_default_toolbar_text );
        toolbar_text.setText( "대결 경쟁을 통해\n참다운 의병이 되어라!" );
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        //// End Of Set Toolbar
        textView = (TextView) findViewById( R.id.train_mission_complete_mission_TextView );
        imageButton = (ImageButton)findViewById( R.id.msq_default_btn );
        warningtextView = (TextView)findViewById( R.id.trainmission_quiz_warning );

        /// if Admin_Login..
        point_layout = findViewById( R.id.point_layout );
        sharedPreferences = getSharedPreferences( "NamSan",MODE_PRIVATE );
        editor = sharedPreferences.edit();
        admin_status = sharedPreferences.getString( "Admin","" );
        btn30 = (AppCompatButton) findViewById( R.id.point_30 );
        btn50 = (AppCompatButton) findViewById( R.id.point_50 );
        btn100 = (AppCompatButton) findViewById( R.id.point_100 );
        btn150 = (AppCompatButton) findViewById( R.id.point_150 );

        setPointBtn( checkAdaminLogin() );
        /***** End Of Set Layout *****/

        /***** Set Event *****/

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), PopupActivity.class );
                startActivity(intent);
            }
        });

        /***** End Of Set Event *****/
    }

    private boolean checkAdaminLogin() {
        if(admin_status == ""){
            if(event_check){
                point_layout.setVisibility( View.GONE );
            }else{
                point_layout.setVisibility( View.GONE );
                imageButton.setVisibility( View.GONE );
                warningtextView.setVisibility( View.GONE );
                textView.setVisibility( View.VISIBLE );
            }
            return false;
        }else {
            if(event_check){
                point_layout.setVisibility( View.VISIBLE );
            }else{
                point_layout.setVisibility( View.GONE );
                imageButton.setVisibility( View.GONE );
                warningtextView.setVisibility( View.GONE );
                textView.setVisibility( View.VISIBLE );
            }
            return true;
        }
    }

    private void setPointBtn(boolean check){
        if(check){
            AppCompatButton.OnClickListener onClickListener = new AppCompatButton.OnClickListener(){
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.point_30:
                            editor.putInt( "myPoint",sharedPreferences.getInt( "myPoint",0 )+30 );
                            editor.commit();
                            event_check = false;
                            finish();
                            break;
                        case R.id.point_50:
                            editor.putInt( "myPoint",sharedPreferences.getInt( "myPoint",0 )+50 );
                            editor.commit();
                            event_check = false;
                            finish();
                            break;
                        case R.id.point_100:
                            editor.putInt( "myPoint",sharedPreferences.getInt( "myPoint",0 )+100 );
                            editor.commit();
                            event_check = false;
                            finish();
                            break;
                        case R.id.point_150:
                            editor.putInt( "myPoint",sharedPreferences.getInt( "myPoint",0 )+150 );
                            editor.commit();
                            event_check = false;
                            finish();
                            break;
                    }
                }
            };
            btn30.setOnClickListener( onClickListener );
            btn50.setOnClickListener( onClickListener );
            btn100.setOnClickListener( onClickListener );
            btn150.setOnClickListener( onClickListener );
        }else{

        }
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
        setPointBtn( checkAdaminLogin() );
    }
}
