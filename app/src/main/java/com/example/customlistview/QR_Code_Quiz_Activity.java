package com.example.customlistview;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class QR_Code_Quiz_Activity extends AppCompatActivity {

    private String quiz_num;
    private Intent intent;
    private TextView textView;
    private EditText editText;
    private ImageButton btn_apply;
    private static String[] qr_quiz_array;
    private static String[] qr_quiz_array_answer;
    private String qr_quiz_desc;
    private String qr_quiz_answer;
    private String user_answer;
    private final String[] QR_CODE= {"924512","936802","966509","999910"};
    private boolean flag;
    private int code_index;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor ;
    private int code_check;
    private ImageButton pass_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.qr_code_detail );

        Toolbar toolbar = (Toolbar) findViewById(R.id.qr_code_detail_toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences( "NamSan",MODE_PRIVATE );
        editor = sharedPreferences.edit();
        code_check = sharedPreferences.getInt( "qr_code_manager",0 );

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        intent = getIntent();

        pass_btn = (ImageButton)findViewById( R.id.qr_code_detail_pass_btn );
        quiz_num = intent.getStringExtra( "Quiz_Num" );
        textView = (TextView)findViewById( R.id.qr_quiz_desc );
        editText = (EditText)findViewById( R.id.qr_code_detail_editText );

        btn_apply = (ImageButton)findViewById( R.id.qr_code_detail_apply_btn );

        if(qr_quiz_array == null){
            qr_quiz_array = getResources().getStringArray( R.array.qr_code_quiz );
            qr_quiz_array_answer = getResources().getStringArray( R.array.qr_code_quiz_answer );
        }

        setQr_quiz( quiz_num );
        getQr_quiz();
        setQr_quiz_answer( quiz_num );
        getQr_quiz_answer();

        textView.setText( qr_quiz_desc );

        pass_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent( getApplicationContext(), QR_Code_Default_Sub_Activity.class );
                startActivity( intent );
                finish();
            }
        } );

        btn_apply.setOnClickListener( new View.OnClickListener() {
            Intent intent = null;
            @Override
            public void onClick(View v) {
                user_answer = String.valueOf( editText.getText() );

                if(user_answer.length() == 0){
                    Toast.makeText( getApplicationContext(),"정답을 입력해주세요.",Toast.LENGTH_LONG ).show();
                }
                else {
                    if(qr_quiz_answer.equals( user_answer )){
                        intent = new Intent( getApplicationContext(), QR_Code_Correct_Activity.class );
                        intent.putExtra( "code_index",code_index );
                        startActivity( intent );
                        finish();
                    }
                    else{
                        intent = new Intent( getApplicationContext(), QR_Code_False_Activity.class );
                        startActivityForResult( intent, 30 );
                    }

                }
            }
        } );
    }

    public String getQuiz_num() {
        return quiz_num;
    }

    public void setQuiz_num(String quiz_num) {
        this.quiz_num = quiz_num;
    }

    public String getQr_quiz() {
        return qr_quiz_desc;
    }

    public void setQr_quiz(String num) {
        code_index = Arrays.binarySearch(QR_CODE,num);
        this.qr_quiz_desc = qr_quiz_array[code_index];
    }

    public String getQr_quiz_answer() {
        return qr_quiz_answer;
    }

    public void setQr_quiz_answer(String num) {
        int index = Arrays.binarySearch( QR_CODE,num );
        this.qr_quiz_answer = qr_quiz_array_answer[index];
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 30){
            finish();
        }
    }

}
