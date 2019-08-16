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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class QR_Code_Quiz_Activity extends AppCompatActivity {

    private String quiz_num;
    private Intent intent;
    private TextView textView;
    private EditText editText;
    private Button btn_apply;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.qr_code_detail );

        Toolbar toolbar = (Toolbar) findViewById(R.id.qr_code_detail_toolbar_top);
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
        quiz_num = intent.getStringExtra( "Quiz_Num" );
        textView = (TextView)findViewById( R.id.qr_quiz_desc );
        editText = (EditText)findViewById( R.id.edt_QuizText );

        btn_apply = (Button)findViewById( R.id.qr_code_detail_apply_btn );

        if(qr_quiz_array == null){
            qr_quiz_array = getResources().getStringArray( R.array.qr_code_quiz );
            qr_quiz_array_answer = getResources().getStringArray( R.array.qr_code_quiz_answer );
        }

        setQr_quiz( quiz_num );
        getQr_quiz();
        setQr_quiz_answer( quiz_num );
        getQr_quiz_answer();

        textView.setText( qr_quiz_desc );

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
                        intent = new Intent( getApplicationContext(), QR_PointManager.class );
                        intent.putExtra( "user_answer",user_answer );
                        intent.putExtra("flag",true);
                        startActivityForResult( intent, 20 );
                    }
                    else{
                        intent = new Intent( getApplicationContext(), QR_PointManager.class );
                        intent.putExtra( "user_answer", user_answer );
                        intent.putExtra( "flag",false );
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
        Log.d("!!!!!!!", String.valueOf( code_index ) );
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
            Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 20 ){
            int qr_code_manager = (int) Math.pow( 2,code_index );
            editor.putInt( "qr_code_manager",qr_code_manager+sharedPreferences.getInt( "qr_code_manager" ,0) );
            editor.commit();
            finish();
        }
        else if(resultCode == 30){
            finish();
        }
    }

}
