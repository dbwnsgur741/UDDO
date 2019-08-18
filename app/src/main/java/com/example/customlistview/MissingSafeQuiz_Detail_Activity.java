package com.example.customlistview;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MissingSafeQuiz_Detail_Activity extends AppCompatActivity {

    private static int index = 1 ;
    private ImageButton imageButton;
    private TextView desc ;
    private TextView toolbar_text;
    private TextView desc_text;
    private ImageView msq_detail_imageview;
    private TextView msq_description_textview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        /*
        if(index ==1){
            setContentView( R.layout.missingsafequiz_detail );
        }
        if(index ==2){
            setContentView( R.layout.missingsafequiz_detail_2 );
        }
        if(index ==3){
            setContentView( R.layout.missingsafequiz_detail_3 );
        }
        if(index ==4){
            setContentView( R.layout.missingsafequiz_detail_4 );
        }
        if(index ==5){
            this.index = 1;
            Intent intent = new Intent( getApplicationContext(), MainActivity.class );
            startActivity(intent);
            finish();
        }
        */
        switch (index){
            case 1:
                setContentView( R.layout.missingsafequiz_detail );
                break;
            case 2:
                setContentView( R.layout.missingsafequiz_detail_2 );
                break;
            case 3:
                setContentView( R.layout.missingsafequiz_detail_3 );
                break;
            case 4:
                setContentView( R.layout.missingsafequiz_detail_4 );
                break;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.msq_detail_toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar_text = (TextView) findViewById( R.id.msq_detail_toolbar_text );
        desc_text = (TextView)findViewById( R.id.msq_detail_textview2 );
        imageButton = (ImageButton)findViewById( R.id.msq_detail_btn );
        msq_detail_imageview = (ImageView)findViewById( R.id.msq_detail_imageview );
        msq_description_textview = (TextView)findViewById( R.id.msq_detail_textview );


        imageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index < 4){
                    index += 1;
                    Intent intent = new Intent( getApplicationContext(), MissingSafeQuiz_Detail_Activity.class );
                    startActivity( intent );
                    finish();
                }else{
                    index = 1;
                    Intent intent = new Intent( getApplicationContext(), MissingSafeQuiz_final_Activity.class );
                    startActivity(intent);
                    finish();
                }
            }
        } );
    }


    /*
    private void setLayout(int index) {
        toolbar_text.setText( "사라진 고종황제의 비밀금고"+title[index] );
        desc_text.setText( "황제의 비밀금고"+description2[index]+"번째 비밀번호입니다.\n꼭 기억하세요." );
        switch (index){
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    msq_detail_imageview.setBackground( ContextCompat.getDrawable( this,R.drawable.msq_1 ) );
                }
                msq_description_textview.setVisibility( View.GONE );
                break;
            case 2:
                msq_description_textview.setVisibility( View.VISIBLE );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    msq_detail_imageview.setBackground( ContextCompat.getDrawable( this,R.drawable.msq_2 ) );
                }
                msq_description_textview.setText("위 그림에 숫자가 있습니다.\n" +
                        "찾은 숫자를 모두 더하시오.\n" +
                        "한자리 숫자가 될 때까지 각자리수를 더하시오.");
                break;
            case 3:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    msq_detail_imageview.setBackground( ContextCompat.getDrawable( this,R.drawable.msq_3 ) );
                }
                msq_description_textview.setText("물음표에 들어갈 숫자를 구하시오.");
                break;
            case 4:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    msq_detail_imageview.setBackground( ContextCompat.getDrawable( this,R.drawable.msq_4 ) );
                }
                msq_description_textview.setText("를 낱말로 만든다면 무엇이 될까?\n" +
                        "\n" +
                        "힌트 : 자음과 모음을 조합해 낱말을 만드시오.\n" +
                        "정답은 12지신 중 몇 번째 동물일까요?\n" +
                        "낱말은 12지신과 같은 과에 속하는 동물입니다.");
                break;
            case 5:
                this.index = 1;
                Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                finish();
                break;
        }
    }
    */

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
