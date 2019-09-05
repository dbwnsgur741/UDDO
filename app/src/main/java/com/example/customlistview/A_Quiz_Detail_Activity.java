package com.example.customlistview;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class A_Quiz_Detail_Activity extends AppCompatActivity implements View.OnClickListener {

    public int quiz_number ;
    public String quiz_description ;
    public String what_quiz ;
    public boolean flag;
    public MySingleton mySingleton;
    private ImageButton corbx;
    private ImageButton falbx;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.always_quiz_detail);

        IntiailzeView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.always_quiz_detail_toolbar_top);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mySingleton = MySingleton.getInstance(this);
        sharedPreferences = getSharedPreferences("NamSan",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getInt("Quiz1",0) == 0 ){
            editor.putInt("Quiz1",1);
            editor.commit();
        }

        mySingleton.setQuiz_num(sharedPreferences.getInt("Quiz1", 0));
        mySingleton.setQuiz_desc(sharedPreferences.getInt("Quiz1",0));


        this.quiz_description = mySingleton.getQuiz_desc();
        this.what_quiz = mySingleton.getWhat_quiz();

        TextView quiz_num = (TextView)findViewById(R.id.quiz_num);
        TextView quiz_desc = (TextView)findViewById(R.id.quiz_desc);

        quiz_num.setText(what_quiz);
        quiz_desc.setText(quiz_description);

    }

    public void IntiailzeView(){
        corbx = (ImageButton)findViewById(R.id.correct_box);
        falbx = (ImageButton)findViewById(R.id.false_box);
        corbx.setOnClickListener(this);
        falbx.setOnClickListener(this);
    }


    public void correctevent(int num) {
        Intent intent = null;
        SharedPreferences sharedPreferences = getSharedPreferences("NamSan",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        mySingleton.setQuiz_answer(sharedPreferences.getInt("Quiz1",0));
        int answer = mySingleton.getQuiz_answer();

        if(num == answer){
            editor.putInt("Quiz1", sharedPreferences.getInt("Quiz1",0)+1);
            editor.putInt("myPoint",sharedPreferences.getInt("myPoint",0)+50);
            editor.commit();
            intent = new Intent( getApplicationContext(), A_Quiz_Correct_Acitivity.class );
            startActivity(intent);
            finish();
        }
        else {
            editor.putInt("Quiz1", sharedPreferences.getInt("Quiz1",0)+1);
            editor.commit();
            intent = new Intent( getApplicationContext(), A_Quiz_False_Acitivity.class );
            startActivity( intent );
            finish();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.correct_box:
                correctevent(0);
                break;
            case R.id.false_box:
                correctevent(1);
                break;
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
